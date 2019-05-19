(ns bali-bike-app.ui.bike-card
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ant :as ant]
            [bali-bike-app.ui.bike-preview :as bike-preview]))

(defn main
  [{:keys [id model-id photos daily-price weekly-price monthly-price]}]
  (r/with-let [constants (rf/subscribe [:constants])]
    [:div.bike-card
     [:a {:href (str "/b/" id) :target "_blank"}
      [bike-preview/main {:src (first photos)
                          :daily-price daily-price
                          :weekly-price weekly-price
                          :monthly-price monthly-price}]
      [:div.title (get-in @constants [:models model-id])]]]))

