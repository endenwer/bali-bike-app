(ns bali-bike-app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn app-root []
  [:div "HELLO"])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (r/render-component [app-root] (.getElementById js/document "app")))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(defn ^:export main []
  (start))
