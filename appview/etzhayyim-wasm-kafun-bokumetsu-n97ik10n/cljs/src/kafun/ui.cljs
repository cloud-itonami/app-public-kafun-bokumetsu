(ns kafun.ui
  (:require [kafun.state :as state]
            [reagent.core :as reagent]))

;; appkit.core is still src/appkit/core.cljk on main - invisible to
;; shadow-cljs 2.28.20, so this surface hand-rolls its structural chrome
;; (same class contract as saiban.ui / kotoba-ui/core: top/facts/panel)
;; rather than require a namespace shadow cannot resolve.

(defn chrome-section [label body]
  [:section {:class "panel"}
   [:h2 label]
   body])

(defn chips-list [items]
  [:ul {:class "chips"}
   (for [i items] [:li i])])

(defn facts-grid []
  [:section {:class "facts"}
   [:div [:span "Project"] [:strong (:project @state/app-meta)]]
   [:div [:span "Nanoid"] [:strong (:nanoid @state/app-meta)]]
   [:div [:span "Domains"] [:strong (count (:domains @state/app-meta))]]
   [:div [:span "XRPC"]
    [:strong (if (:xrpc @state/app-meta) "enabled" "not configured")]]])

(defn domains-panel []
  (chrome-section "Domains"
    (if (seq (:domains @state/app-meta))
      [:ul (for [d (:domains @state/app-meta)] [:li d])]
      [:p {:class "muted"} "No domain is declared for this app surface."])))

(defn xrpc-panel []
  (chrome-section "XRPC Surface"
    (if (seq (:xrpc-namespaces @state/app-meta))
      (chips-list (:xrpc-namespaces @state/app-meta))
      [:p {:class "muted"} "No XRPC namespace is declared next to this app surface."])))

(defn source-panel []
  (chrome-section "Source"
    [:p (:relative-path @state/app-meta)]))

(defn top-section []
  [:section {:class "top"}
   [:p "Cloudflare appview"]
   [:h1 (:title @state/app-meta)]
   [:span (:name @state/app-meta)]])

(defn root-view []
  [:main
   [top-section]
   [facts-grid]
   [domains-panel]
   [xrpc-panel]
   [source-panel]])
