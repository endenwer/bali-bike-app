(ns bali-bike-app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ui.main-page :as main-page]
            [bali-bike-app.events]
            [bali-bike-app.subs]))

(defn app-root []
  [main-page/main])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (r/render-component [app-root] (.getElementById js/document "app")))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(defn ^:export main []
  (start))
