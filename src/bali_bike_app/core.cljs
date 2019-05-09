(ns bali-bike-app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.routes :as routes]
            [bali-bike-app.ui.page-content :as page-content]
            [bali-bike-app.ui.header :as header]
            [bali-bike-app.ui.meta-tags :as meta-tags]
            [bali-bike-app.events]
            [bali-bike-app.subs]))

(defn app-root []
  [:<>
   [meta-tags/main]
   [header/main]
   [page-content/main]])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (r/render-component [app-root] (.getElementById js/document "app")))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(defn ^:export main []
  (routes/app-routes)
  (rf/dispatch-sync [:initialize-db])
  (start))
