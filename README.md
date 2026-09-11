# app-public-kafun-bokumetsu

**花粉撲滅Fund（スギ・ヒノキ花粉）の抽出物 —— この repo が持つのは
「研究 → アクション → capability」を記録する kotoba 実装 1 本と、
appview の骨組み 1 枚である。**

名前が主題（花粉撲滅）しか示さず、`app-` が役割面であることしか言わないので、
先に何が在るかを名乗る（この workspace の規約）。

`etzhayyim/root` の `60-apps/etzhayyim-project-public-kafun-bokumetsu` からの
抽出物で、**tree は移行元と 1 バイトも違わない**（下記「持ち出しは完全である」）。
数字はすべて `scripts/verify-docs-claims.cljk` が tree から再計算して検査する。

## tree に在るもの（24 ファイル）

```
kotoba/                      ← 唯一テストの在る面。ここが実装である
  src/types.ts                 record 型・DID 導出・検証集合
  src/registry.ts              research / capability / action / coverage
  src/index.ts                 barrel
  test/kafun.test.ts           vitest 3 件（mock SDK に対して回る）

appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/
  src/app.ts                 ← CF Worker の薄い dispatcher（384 行）
  kotodama.jsonld              actor 宣言
  svelte/                      Vite の足場（App.svelte は 455 B）

CLAUDE.md                    ← 移行元から持ってきた文書。下記のとおり現状と合わない
README.edn / migration.edn   ← 移行が足すことを許した 2 件だけ

README.md / docs/ / scripts/ ← この 3 件だけが移行後の追加（本文書と検証器）
```

内訳は **移行由来 21（= 継承 19 + 許可された追加 2）+ 本 README 群 3**。
検証器はこの 2 つを別々に数える —— 継承分が動いていないことと、
文書を足したことは、別の主張だからである。

`kotoba/` が主張するのは 3 つの collection と、その間の 1 本の FK だけである:

| collection | 書く関数 | 不変条件（テストが押さえているもの） |
|---|---|---|
| `…kafunBokumetsu.research` | `recordResearch` | `category` は 6 種の閉じた集合。外れると `rejected` |
| `…kafunBokumetsu.capability` | `defineCapability` | 同 id の再定義は `alreadyExists` |
| `…kafunBokumetsu.action` | `proposeAction` | `researchId` を渡したら**実在しなければ `researchNotFound`** |

`setActionStatus` は `done` / `cancelled` を終端として扱い、そこから戻す遷移を
`rejected` にする。`coverage` は 3 collection を走査して数える（`maxScan` 上限つき）。

## ⚠ CLAUDE.md はこの repo を説明していない

`CLAUDE.md` は移行元 monorepo の文書がそのまま来たもので、**tree に無いものを
在るものとして書いている**。読む前に次を知っておくこと（すべて実測）:

| CLAUDE.md の記述 | この tree の実際 |
|---|---|
| XRPC メソッド表 **23 行**（`agent.*` 7 / `fund.*` 9 / `cap.*` 5 / `evolution.*` 2） | `app.ts` が登録するのは **3 つ**（`agent.research` / `agent.think` / `agent.tick`）。`fund.*` と `evolution.*` は**コードに 1 文字も無い** |
| build 手順が `wasm/etzhayyim-wasm-…` に `cd` する | そのディレクトリは無い。**移行元にも無かった** —— 実際は `appview/…`（移行の改名ではない） |
| `k8s/cronjob.yaml` を `kubectl apply` する | `k8s/` ディレクトリが無い |
| `component.wasm` を配る（`kotodama.jsonld` も指す） | tree に無い |
| Svelte の画面 7 枚（Dashboard / Evolution / Rooms / Research / Actions / Capabilities / Logs） | `App.svelte` は 455 B の足場で、自分を "Vite entry scaffold after SvelteKit cleanup" と名乗る |
| `pnpm install && pnpm build` で UI を作る | `svelte/package.json` が `@etzhayyim/design-system: workspace:*` に依存する。**workspace 外では解決しない** |

**この差は移行が壊したものではない。** 移行は 19 ファイルを 1 バイトも変えずに
運んでおり（次節）、食い違いは移行元の時点で既に在った。ここに書いたのは
「どちらが正しいか」ではなく「どちらが tree に在るか」であって、`fund.*` を
実装するのか表から消すのかはオーナーの決定である。

## 持ち出しは完全である（実測）

`migration.edn` が申告する値を、移行元の git object と突き合わせた結果:

| 申告 | 値 | 検証 |
|---|---|---|
| source revision | `etzhayyim/root@168497bd` | commit が実在する |
| source tree | `bd42c6d6a555e9e2f06fc98db4d2d367833cb5b8` | `git rev-parse <rev>:<path>` と**完全一致** |
| tracked-files | 19 | 一致 |
| bytes | 49,056 | 一致 |
| 中身 | — | **19/19 の blob hash が同一**（書き換えゼロ） |
| 追加 | `README.edn` `migration.edn` | `:allowed-additions` とちょうど一致。他の追加は無い |

## 何が動くか —— npm 11.16.0 だけが例外である

`kotoba/` の宣言どおりの手順（`npm install` → `npm test` → `npm run typecheck`）は
**動く**。ただしこのワークステーションの npm 11.16.0 では `npm install` が届かない。

| 環境 | `npm install` | `npm test` | `npm run typecheck` |
|---|---|---|---|
| judah（npm **11.17.0**） | OK | **3 passed** exit 0 | exit 0 |
| このワークステーション（npm **11.16.0**） | **EALLOWSCRIPTS で失敗** | 迂回すれば 3 passed | 通らない（6 errors） |

**これは repo の欠陥ではなく npm 11.16.0 の回帰である。** `prepare` script を
持つだけの最小の git 依存を 1 つ作れば再現し、同じ再現物が **11.17.0 と 10.9.8 では
通る**。原因は git 依存の準備段で npm が内側の install に `--allow-scripts` 相当を
渡し、それを自分で拒否すること（`@etzhayyim/sdk` は `prepare: tsc` を持つ）。

迂回と、なぜ迂回できるのか（`@etzhayyim/sdk` は **型としてしか import されない**、
`@etzhayyim/sdk-mock` は import ゼロの自己完結 309 行）は
`docs/operator-quickstart.md` に測った手順として書いてある。

## 検証

```bash
kbb --backend sci scripts/verify-docs-claims.cljk .      # <dir> は先頭に置く
```

この README と quickstart が言う数字を tree から**測り直す**。
exit 0 = 全部合う / 1 = 合わない主張が在る / **2 = 読めなくて答えられなかった**
（読めなかったことを合格と区別するために別の code にしてある）。

## 境界

`README.edn` は `:role :public-pollen-eradication-application`、
`:subject-authority "etzhayyim/com-etzhayyim-kafun"` を宣言する。
扱うのは**公開の環境・公衆衛生データだけ**である —— 個人データを持たず
（対象は花粉であって人ではない）、`Fund` は事業名であって決済は無く、
実行責任も負わない（助言としての研究・提案）。`kotoba/src/types.ts` の
AXIS NOTE がこの姿勢の正本。
