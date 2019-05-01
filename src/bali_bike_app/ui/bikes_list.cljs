(ns bali-bike-app.ui.bikes-list
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ui.bike-card :as bike-card]))

(defn main []
  (r/with-let [bikes (rf/subscribe [:bikes])]
    [:div.bikes-list
     (for [bike-data @bikes]
       ^{:key (:id bike-data)} [bike-card/main bike-data])]))
