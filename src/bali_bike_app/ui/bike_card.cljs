(ns bali-bike-app.ui.bike-card
  (:require [bali-bike-app.constants :as constants]
            [reagent.core :as r]))

(defn main
  [{:keys [model-id photos]}]
  [:div.bike-card
   [:div.photo {:style {:background-image (str "url(\"" (first photos) "\")")}}]
   [:div.title (get constants/models model-id)]])

