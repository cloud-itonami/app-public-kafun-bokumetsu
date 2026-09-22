(ns kafun.state
  (:require [reagent.core :as reagent]))

;; App surface metadata mirroring the declared appview identity (untrusted
;; source data, read-only): title/project/kind/domains/xrpc namespaces.
;; Same surface contract as saiban.state - the appview declares its
;; nanoid, project, runtime and XRPC surface in kotodama.jsonld / app.ts.
(defonce app-meta
  (reagent/atom {:title "Kafun Bokumetsu N97ik10n"
                 :project "etzhayyim-project-public-kafun-bokumetsu"
                 :name "etzhayyim-wasm-kafun-bokumetsu-n97ik10n"
                 :kind "appview"
                 :nanoid "n97ik10n"
                 :domains ["kafun-bokumetsu.etzhayyim.com"
                           "n97ik10n.etzhayyim.com"]
                 :xrpc-namespaces ["com.etzhayyim.apps.kafun.agent"
                                   "com.etzhayyim.apps.kafun.fund"
                                   "com.etzhayyim.apps.kafun.cap"
                                   "com.etzhayyim.apps.kafun.evolution"]
                 :xrpc true
                 :relative-path "appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/cljs/src/kafun/desktop.cljs"}))

(defn app-meta-value [k]
  (get @app-meta k))
