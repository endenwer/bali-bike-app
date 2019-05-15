(ns bali-bike-app.ui.left-panel
  (:require [bali-bike-app.ant :as ant]
            [reagent.core :as r]))

(defn render-price-filters
  [{:keys [title]}]
  [:div.price-filters
   [:div title]
   [ant/slider {:range true :default-value [10 500] :min 10 :max 500}]
   [:div.price-inputs
    [ant/input {:addon-before "Rp" :addon-after "K" :allow-clear true}]
    [:span.separetor "-"]
    [ant/input {:addon-before "Rp" :addon-after "K"}]]])

(defn main []
  (r/with-let [visible (r/atom false)]
    [:div
     [ant/button {:on-click #(reset! visible true)} "Filters"]
     [ant/drawer {:visible @visible
                  :placement "left"
                  :on-close #(reset! visible false)
                  :width 350
                  :closable false
                  :class-name "left-panel"}
      [ant/collapse {:bordered false}
       [ant/collapse-panel {:header "Location"}]
       [ant/collapse-panel {:header "Price"}
        [render-price-filters {:title "Daily price"}]
        [render-price-filters {:title "Weekly price"}]
        [render-price-filters {:title "Monthly price"}]]]]]))
