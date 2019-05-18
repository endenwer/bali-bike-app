(ns bali-bike-app.ui.main-page
  (:require [bali-bike-app.ui.bikes-list :as bikes-list]
            [bali-bike-app.ui.left-panel :as left-panel]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [bali-bike-app.ant :as ant]))

(def order-types
  {"DATE" "Date added"
   "DAILY_PRICE" "Daily price"
   "WEEKLY_PRICE" "Weekly price"
   "MONTHLY_PRICE" "Monthly price"})

(defn render-sort-options
  [order-type]
  [ant/menu {:on-click #(rf/dispatch [:change-bikes-order-type (.-key %)])
             :selected-keys [order-type]}
   (for [[type title] order-types]
     [ant/menu-item {:key type} title])])

(defn main []
  (r/with-let [bikes-order (rf/subscribe [:bikes-order])]
    [:div.main-page
     [left-panel/main]
     [:div.top-buttons
      [ant/button {:on-click #(rf/dispatch [:open-left-panel])
                   :size "large"} "Filters"]
      [ant/button-group
       [ant/dropdown {:overlay (r/as-element [render-sort-options (:type @bikes-order)])}
        [ant/button {:size "large"} (get order-types (:type @bikes-order))]]
       [ant/button {:icon (if (= (:direction @bikes-order) "DESC") "arrow-down" "arrow-up")
                    :size "large"
                    :on-click #(rf/dispatch [:toggle-bikes-order-direction])}]]]
     [bikes-list/main]]))
