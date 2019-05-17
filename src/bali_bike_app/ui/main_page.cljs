(ns bali-bike-app.ui.main-page
  (:require [bali-bike-app.ui.bikes-list :as bikes-list]
            [bali-bike-app.ui.left-panel :as left-panel]
            [re-frame.core :as rf]
            [bali-bike-app.ant :as ant]))

(defn main []
  [:div.main-page
   [left-panel/main]
   [:div.top-buttons
    [ant/button {:on-click #(rf/dispatch [:open-left-panel])
                 :size "large"} "Filters"]]
   [bikes-list/main]])
