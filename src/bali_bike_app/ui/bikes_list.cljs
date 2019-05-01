(ns bali-bike-app.ui.bikes-list
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn main []
  (r/with-let [bikes (rf/subscribe [:bikes])]
    [:div
     (for [bike-data @bikes]
       ^{:key (:id bike-data)} [:div (:model-id bike-data)])]))
