#!/usr/bin/env nbb
;; verify-docs-claims — re-derive every number README.md and docs/operator-quickstart.md
;; state, from the tree itself, and fail when the tree and the prose disagree.
;;
;; The load-bearing claim here is CUSTODY: migration.edn says 19 files / 49,056 bytes
;; came from etzhayyim/root@168497bd unchanged, and that exactly two files were
;; allowed to be added. That was verified blob-by-blob against the source repo once
;; (2026-08-19, 19/19 identical). This script keeps it verified WITHOUT the source
;; checkout, by pinning each inherited file's sha256 -- so a later silent edit to an
;; "unchanged" file fails here even on a machine that has never seen etzhayyim/root.
;;
;; The second claim is that CLAUDE.md describes a system this tree does not contain.
;; That is asserted as a COUNT ON BOTH SIDES (23 documented methods vs 3 registered),
;; not as "CLAUDE.md is stale" -- so implementing the missing methods, or deleting the
;; table, both fail here and force the prose to move with the tree.
;;
;; Usage:  nbb scripts/verify-docs-claims.cljs [<dir>]     (<dir> FIRST, default ".")
;; Exit:   0 every claim holds · 1 a claim is false · 2 could not answer

(require '["node:fs" :as fs]
         '["node:path" :as path]
         '["node:crypto" :as crypto]
         '[clojure.edn :as edn]
         '[clojure.string :as str])

(def root (or (first (remove #(str/starts-with? % "--") *command-line-args*)) "."))

(def claims
  {:all-files              24      ; everything tracked today
   :pre-docs-files         21      ; = 19 inherited + 2 allowed additions
   :inherited-files        19      ; migration.edn :source :tracked-files
   :inherited-bytes        49056   ; migration.edn :source :bytes
   :allowed-additions      #{"README.edn" "migration.edn"}
   :migration-tree         "bd42c6d6a555e9e2f06fc98db4d2d367833cb5b8"
   :migration-revision     "168497bd668c8d081fbeb32a0df24d9514075686"
   ;; CLAUDE.md's XRPC table vs what app.ts actually registers
   :documented-methods     23
   :documented-fund-rows   9
   :documented-evolution-rows 2
   :registered-nsids       3
   ;; kotoba/ -- the only tested face
   :test-blocks            3
   :research-categories    6
   :action-statuses        4
   ;; things README.md asserts are ABSENT, by name
   :absent                 ["k8s" "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/component.wasm"]
   :workspace-dep          "@etzhayyim/design-system"
   :app-svelte-bytes       455})

;; The 19 files migration.edn says arrived unchanged. Pinned by content so that
;; "unchanged" stays a checkable statement offline.
(def inherited
  {"CLAUDE.md" "8c42fc08b4e5b970dfb588dad771ef5ebff1929cdc6458a49ce5f169e17a3465"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/kotodama.jsonld" "2dffd6df53d81998234c5ad4f30152efc240133c10a0504a2e3c7f6e4b9eb1ec"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/src/app.ts" "cc641566e2040cc3cb3ad7993dd68d2c824dd0428cd5ef6838dae42f51667ddf"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/index.html" "8d3614a8fb6d235b207049901435572abd451b3f2893acf027ce0f017449f9c3"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/package.json" "5661258e6bf7ce3b86fec09d264ef6555226cb0f2868eea3ea53bff23b994fe6"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/postcss.config.js" "082f9a07aae22ae329bfc652d405ba5a704cba1ee86f4b78c7c70f98d628a610"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/src/App.svelte" "c0dda0885ce42c31dcbc5776d9204c6e3d615587625a3c51a57ff1aa88f3a724"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/src/main.ts" "63388a3e3933a72c9906542b5ccdef051b492286da1eda3426b8822f8bfd3f7c"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/src/svelte.d.ts" "5fa9abc37983b9e4f4c89265ff15bdd7a4fb68019315588f723b599288d91bb1"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/tailwind.config.js" "71987ce4435148edbf92e81ca80770c4ef19766a48a54a3127d561497656cdf6"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/tsconfig.json" "810ef62e4f2e84afa143dadede084875a90bf0817af2946cfa02a7c351308110"
   "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/vite.config.ts" "c3611c0a75bef95ba5f5a71d7ec39c92f8be2324bdeee39a82c159c1cd47b6d0"
   "kotoba/package.json" "f08e2758c211fa4eb207670c2aa2ea8bd6fd21c5d3afe0b30dc3465096d7ebb3"
   "kotoba/src/index.ts" "cc58bbafb14e6e030afb8e8c6c2ba329caf99a78627f84080dfd378555b5ba17"
   "kotoba/src/registry.ts" "f7be5c1db388785b6d2ad3a5f775bf14c93f534bde80b1e295647b0f61bc872f"
   "kotoba/src/types.ts" "97a4845ed8f705a7faf33d968d15155b7ef3ff1b8b0c9bf5c1b60cefee454925"
   "kotoba/test/kafun.test.ts" "47fede31cb84366cbda6b50816af5884209a1f992988e85a66307a38406d2681"
   "kotoba/tsconfig.json" "95a429e51d6162cb7205b603f745e7604d93ffbb1ea6c346e5c6215a79ae541e"
   "kotoba/vitest.config.ts" "f82a551ef4da1c9cbf17985a3bee96eee450a3e4a46bff0d96c6150263121eff"})

(def failures (atom []))
(def undetermined (atom []))
(def scanned (atom 0))

(defn label [k] (if (keyword? k) (name k) (str k)))
(defn fail! [k expected actual]
  (swap! failures conj k)
  (println (str "FAIL\t" (label k) "\texpected=" (pr-str expected) "\tactual=" (pr-str actual))))
(defn check! [k expected actual]
  (if (= expected actual) true (do (fail! k expected actual) false)))
(defn undet! [m] (swap! undetermined conj m))

(defn p [f] (path/join root f))
(defn slurp* [f]
  (try (str (fs/readFileSync (p f) "utf8")) (catch :default _ nil)))
(defn exists? [f] (try (do (fs/statSync (p f)) true) (catch :default _ false)))
(defn sha256 [f]
  (try (-> (crypto/createHash "sha256") (.update (fs/readFileSync (p f))) (.digest "hex"))
       (catch :default _ nil)))
(defn bytes-of [f]
  (try (.-size (fs/statSync (p f))) (catch :default _ nil)))

(defn walk-files [dir]
  (try
    (->> (fs/readdirSync (p dir) #js {:withFileTypes true})
         (mapcat (fn [d]
                   (let [n (.-name d) rel (if (= dir "") n (str dir "/" n))]
                     (cond
                       (#{".git" "node_modules" ".vite-temp"} n) []
                       (.isDirectory d) (walk-files rel)
                       :else [rel]))))
         vec)
    (catch :default _ nil)))

;; ── custody ─────────────────────────────────────────────────────────
(let [m (some-> (slurp* "migration.edn") edn/read-string)]
  (if (nil? m)
    (undet! "migration.edn unreadable or not EDN")
    (let [src (:source m)]
      (check! :migration-revision (:migration-revision claims) (:revision src))
      (check! :migration-tree (:migration-tree claims) (:tree src))
      (check! :inherited-files (:inherited-files claims) (:tracked-files src))
      (check! :inherited-bytes (:inherited-bytes claims) (:bytes src))
      (check! :allowed-additions (:allowed-additions claims)
              (set (get-in m [:identity :allowed-additions]))))))

;; every inherited file still byte-identical, and their total still the declared one
(let [hashes (into {} (map (fn [[f _]] [f (sha256 f)]) inherited))
      missing (keep (fn [[f h]] (when (nil? h) f)) hashes)]
  (if (seq missing)
    (undet! (str (count missing) " inherited file(s) unreadable: " (str/join ", " missing)))
    (do
      (reset! scanned (count hashes))
      (doseq [[f want] inherited]
        (when-not (= want (get hashes f))
          (fail! (str "inherited-changed:" f) want (get hashes f))))
      ;; the byte total re-derived from THIS tree, not copied from migration.edn
      (check! :inherited-bytes-recomputed (:inherited-bytes claims)
              (reduce + 0 (map bytes-of (keys inherited)))))))

;; the two allowed additions are present; nothing claims to be inherited that isn't listed
(doseq [a (:allowed-additions claims)]
  (check! (str "addition-present:" a) true (exists? a)))

;; ── CLAUDE.md documents a system this tree does not contain ─────────
(let [c (slurp* "CLAUDE.md")
      a (slurp* "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/src/app.ts")]
  (if (or (nil? c) (nil? a))
    (undet! "CLAUDE.md or app.ts unreadable")
    (let [rows (re-seq #"(?m)^\| `([a-z_]+)\.[a-z_]+` *\|" c)
          by-prefix (frequencies (map second rows))
          nsids (set (map second (re-seq #"nsid\(\"(com\.etzhayyim\.apps\.kafun\.[a-z.]+)\"\)" a)))]
      (check! :documented-methods (:documented-methods claims) (count rows))
      (check! :documented-fund-rows (:documented-fund-rows claims) (get by-prefix "fund" 0))
      (check! :documented-evolution-rows (:documented-evolution-rows claims) (get by-prefix "evolution" 0))
      (check! :registered-nsids (:registered-nsids claims) (count nsids))
      ;; asserted BY NAME, not by a count that a rename would keep green
      (check! :registered-nsid-names
              #{"com.etzhayyim.apps.kafun.agent.research"
                "com.etzhayyim.apps.kafun.agent.think"
                "com.etzhayyim.apps.kafun.agent.tick"}
              nsids)
      ;; the whole point of the README table: fund/evolution exist only in prose
      (check! :no-fund-or-evolution-in-code true
              (empty? (filter #(or (str/includes? % ".fund.") (str/includes? % ".evolution."))
                              nsids))))))

;; ── kotoba/ invariants the tests pin ────────────────────────────────
(let [t (slurp* "kotoba/test/kafun.test.ts")
      ty (slurp* "kotoba/src/types.ts")
      r (slurp* "kotoba/src/registry.ts")]
  (if (or (nil? t) (nil? ty) (nil? r))
    (undet! "kotoba source or test unreadable")
    (do
      (check! :test-blocks (:test-blocks claims) (count (re-seq #"(?m)^\s+it\(" t)))
      (check! :research-categories (:research-categories claims)
              (count (re-seq #"\"[a-z-]+\""
                             (or (second (re-find #"CATEGORIES[^=]*= new Set\(\[([^\]]*)\]" ty)) ""))))
      (check! :action-statuses (:action-statuses claims)
              (count (re-seq #"\"[a-zA-Z]+\""
                             (or (second (re-find #"ACTION_STATUSES[^=]*= new Set\(\[([^\]]*)\]" ty)) ""))))
      ;; why the suite runs without @etzhayyim/sdk installed (quickstart 2b)
      (check! :sdk-imported-type-only true
              (and (str/includes? r "import type { Etzhayyim } from \"@etzhayyim/sdk\";")
                   (nil? (re-find #"(?m)^import \{[^}]*\} from \"@etzhayyim/sdk\";" r)))))))

;; ── things the README asserts are ABSENT ────────────────────────────
(doseq [a (:absent claims)]
  (check! (str "absent:" a) false (exists? a)))

(let [s (slurp* "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/package.json")]
  (if (nil? s)
    (undet! "svelte/package.json unreadable")
    (check! :workspace-dep-unresolvable true
            (str/includes? s (str "\"" (:workspace-dep claims) "\": \"workspace:*\"")))))

(check! :app-svelte-bytes (:app-svelte-bytes claims)
        (bytes-of "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/svelte/src/App.svelte"))

;; ── the docs themselves ─────────────────────────────────────────────
(let [rm (slurp* "README.md") qs (slurp* "docs/operator-quickstart.md")]
  (if (or (nil? rm) (nil? qs))
    (undet! "README.md or docs/operator-quickstart.md unreadable")
    (do
      (check! :readme-names-itself true (str/starts-with? rm "# app-public-kafun-bokumetsu"))
      ;; No dangling in-repo reference. Matched by SUFFIX against the real tree, so
      ;; shorthand ("app.ts") still resolves while an invented name does not -- an
      ;; exact-path test would flag every abbreviation and teach the reader to
      ;; ignore this check, which is how a check stops being one.
      (let [tree (or (walk-files "") [])
            known? (fn [c] (some #(or (= % c) (str/ends-with? % (str "/" c))) tree))
            cands (->> (re-seq #"`([A-Za-z0-9_./-]+\.(?:ts|md|edn|json|jsonld|cljs|svelte|html))`"
                               (str rm "\n" qs))
                       (map second)
                       (remove #(str/starts-with? % "/"))
                       (remove #(str/includes? % "node_modules"))
                       distinct)
            dangling (remove known? cands)]
        (check! :no-dangling-doc-refs [] (vec dangling))))))

;; ── evidence floor + verdict ────────────────────────────────────────
;; Counted as TWO populations. Collapsing them into one total would let a lost
;; inherited file be masked by an added doc -- the sum stays right while the
;; custody claim quietly becomes false.
(let [all (walk-files "")
      added? (fn [f] (or (str/starts-with? f "docs/")
                         (str/starts-with? f "scripts/")
                         (= f "README.md")))]
  (if (nil? all)
    (undet! "could not walk the tree")
    (do
      (check! :all-files (:all-files claims) (count all))
      (check! :pre-docs-files (:pre-docs-files claims) (count (remove added? all))))))

(when (< @scanned (:inherited-files claims))
  (undet! (str "evidence floor: hashed " @scanned " of " (:inherited-files claims)
               " inherited files")))

(println (str "SCANNED\t" @scanned " inherited files hashed"))
(let [u @undetermined f @failures]
  (when (seq u)
    (doseq [m u] (println (str "UNDETERMINED\t" m)))
    (println "Refusing to report a pass: the tree could not be read completely.")
    (js/process.exit 2))
  (if (seq f)
    (do (println (str "FAILED\t" (count f) " claim(s): " (str/join ", " (map label f))))
        (js/process.exit 1))
    (do (println "OK\tevery claim in README.md and docs/operator-quickstart.md holds")
        (js/process.exit 0))))
