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
      [bike-preview/main {:src (first photos)}]
      [:div.title (get-in @constants [:models model-id])]
      [:div.price
       (when daily-price [ant/tag (str "Daily " (/ daily-price 1000) "K")])
       (when weekly-price [ant/tag (str "Weekly " (/ weekly-price 1000) "K")])
       (when monthly-price [ant/tag (str "Monthly " (/ monthly-price 1000) "K")])
       (when-not (or daily-price weekly-price monthly-price)
         [ant/tag "Ask for price"])]]]))

