(ns bali-bike-app.ui.bike-card
  (:require [bali-bike-app.constants :as constants]))

(defn main
  [{:keys [model-id photos]}]
  [:div.bike-card
   [:div.photos]
   [:div.title (get constants/models model-id)]])

