(ns bali-bike-app.ui.bikes-list
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ui.bike-card :as bike-card]
            [bali-bike-app.ant :as ant]
            ["react-infinite-scroller" :as InfiniteScroll]))

(def infinite-scroll (r/adapt-react-class InfiniteScroll))

(defn main []
  (r/with-let [bikes (rf/subscribe [:bikes])
               bikes-meta (rf/subscribe [:bikes-meta])]
    (if (and (empty? @bikes) (:all-loaded? @bikes-meta))
      [ant/empty-data {:description "Bikes not found"}]
      [:div.bikes-list
       [infinite-scroll {:page-start -1
                         :load-more #(rf/dispatch [:load-bikes])
                         :has-more (not (:all-loaded? @bikes-meta))
                         :initial-load false
                         :loader (r/as-element [ant/spin {:key 0
                                                          :style {:width "100%"}}])}
        (for [bike-data @bikes]
          ^{:key (:id bike-data)} [bike-card/main bike-data])]])))
