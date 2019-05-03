(ns bali-bike-app.ui.bike-card
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn main
  [{:keys [model-id photos]}]
  (r/with-let [constants (rf/subscribe [:constants])]
    [:div.bike-card
     [:div.photo {:style {:background-image (str "url(\"" (first photos) "\")")}}]
     [:div.title (get-in @constants [:models model-id])]]))

