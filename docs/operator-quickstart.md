# operator quickstart

**この文書の手順は全部実際に踏んである。** 踏めなかったものは「踏めない」と
書いてあり、そのときの正確な失敗も一緒に置いてある（測れなかったことを
成功と区別するため）。数字は `scripts/verify-docs-claims.cljs` が測り直す。

所要: 手順 1 が数秒、手順 2 が npm ≥ 11.17 の機械で 1〜2 分。

---

## 0. 何を相手にしているかを 30 秒で見る

```bash
git ls-files | wc -l                    # 24 = 移行由来 21 + 本 README 群 3
git ls-files | grep -c '^kotoba/'       # 7 ← 実装とテストはここだけ
git ls-files | grep -cE '^(README\.md|docs/|scripts/)'   # 3 ← 移行後に足したのはこれだけ
```

`kotoba/` 以外はまだ足場である（`README.md` の表を先に読むこと ——
`CLAUDE.md` は移行元の文書で、tree に無いものを在るものとして書いている）。

## 1. 持ち出しが完全であることを自分で確かめる

`migration.edn` の申告を、移行元の git object に対して検算する。
**移行元の checkout が要る**（`orgs/etzhayyim/root`）。

```bash
SRC=~/github/com-junkawasaki/orgs/etzhayyim/root
REV=168497bd668c8d081fbeb32a0df24d9514075686        # migration.edn の :source :revision
P=60-apps/etzhayyim-project-public-kafun-bokumetsu

git -C "$SRC" rev-parse "$REV:$P"                     # → bd42c6d6a555e9e2f06fc98db4d2d367833cb5b8
git -C "$SRC" ls-tree -r --name-only "$REV" -- "$P" | wc -l   # → 19
git -C "$SRC" ls-tree -r -l "$REV" -- "$P" | awk '{s+=$4} END {print s}'   # → 49056
```

実測（2026-08-19）: 3 つとも `migration.edn` の申告と一致し、**19 個の blob hash も
全部同一**、追加は `README.edn` と `migration.edn` のちょうど 2 件だった。
`scripts/verify-docs-claims.cljs` はこの検算を移行元 checkout が無くても
できる範囲（ファイル数・追加集合・自リポ側の byte 合計）で毎回やり直す。

## 2. テストを回す

### 2a. npm ≥ 11.17 の機械（宣言どおりで通る）

```bash
cd kotoba
npm install          # git 依存 8 本 + @atproto/* + viem。1〜2 分
npm test             # → Test Files 1 passed / Tests 3 passed
npm run typecheck    # → exit 0（出力なし）
```

実測（2026-08-19、judah / npm 11.17.0 / node v26.4.0）: 3 つとも exit 0。
`node_modules/@etzhayyim/` に 8 パッケージが入り、`sdk/dist` が生成される
（`@etzhayyim/sdk` の `prepare: tsc` が走るため）。

### 2b. npm 11.16.0 の機械（`npm install` が届かない）

このワークステーションの npm 11.16.0 では、上の `npm install` がこう落ちる:

```
npm error code EALLOWSCRIPTS
npm error --allow-scripts is not allowed in project-scoped installs.
npm error git dep preparation failed
```

**repo のせいではない。** `prepare` を持つだけの空の git 依存を 1 つ作れば
同じ形で落ち、その最小再現は npm 11.17.0 と 10.9.8 では通る（実測）。
`--ignore-scripts` でも `--dangerously-allow-all-scripts` でも変わらない ——
落ちているのは npm が git 依存の準備のために起動する**内側の** install で、
外から渡すフラグはそこに届かない。

迂回できるのは、テストの実行時閉包が宣言よりずっと小さいからである:

- `kotoba/src/registry.ts:7` は `import type { Etzhayyim } from "@etzhayyim/sdk"`
  —— **型としてしか使わないので実行時には消える**。
- `@etzhayyim/sdk-mock` は import ゼロの自己完結 309 行（9,197 B）。

```bash
# (1) mock だけを置く。package.json の URL は旧名だが GitHub が転送する
cd kotoba
mkdir -p node_modules/@etzhayyim
git clone -q --depth 1 https://github.com/kotoba-lang/sdk-mock.git \
  node_modules/@etzhayyim/sdk-mock
rm -rf node_modules/@etzhayyim/sdk-mock/.git

# (2) vitest は git 依存の無い所で入れて、node_modules ごと運ぶ
#     （この repo で npm install を打つと 2b 冒頭の失敗に戻る）
mkdir -p /tmp/vt && (cd /tmp/vt && npm init -y >/dev/null \
  && npm install --save-dev vitest@^4.1.0 typescript@^5.6.0 >/dev/null)
cp -R /tmp/vt/node_modules/. node_modules/

# (3) 回す
./node_modules/.bin/vitest run              # → Test Files 1 passed / Tests 3 passed
```

実測（2026-08-19、npm 11.16.0 / node v26.3.0）: **3 passed。`@etzhayyim/sdk` を
一切入れずに通る**（型 import が消える証拠でもある）。

**`typecheck` はこの経路では通らない。** 実測で 6 件出る ——
`TS2307`（`@etzhayyim/sdk` の型が無い）1 件と、その帰結の `TS7006`（`r` が
暗黙の `any`）5 件。型を得るには `@etzhayyim/sdk` を build する必要があり、
それには結局 2a の install が要る。**この経路で `typecheck` が緑になったら、
それは何かを測り損ねている。**

## 3. 検証器を回す

```bash
nbb scripts/verify-docs-claims.cljs .
```

`<dir>` は**引数の先頭**に置く（多くの gate が「`--` で始まらない最初の引数」を
tree として読むので、後ろに置くとフラグの値がパスとして解釈される）。

- exit 0 — README.md と本書の主張が全部 tree と合う
- exit 1 — 合わない主張が在る（どれかを名前で言う）
- exit 2 — **読めなくて答えられなかった**。合格ではない

## 4. まだ踏めないもの（正直に）

| やりたいこと | 今日できない理由 |
|---|---|
| appview を build / deploy | `svelte/package.json` が `@etzhayyim/design-system: workspace:*` に依存する。monorepo 外では解決しない |
| `component.wasm` を配る | `kotodama.jsonld` が指すが tree に無い |
| `agent.tick` を cron で回す | `CLAUDE.md` が言う `k8s/cronjob.yaml` が tree に無い |
| `fund.*` / `evolution.*` を呼ぶ | 表には 11 行在るが**実装が 1 文字も無い**。表を消すか実装するかはオーナーの決定 |

これらは移行が壊したものではない（移行は 19 ファイルを 1 バイトも変えていない）。
移行元の時点で既にそうだった。
