(ns bali-bike-app.ui.left-panel
  (:require [bali-bike-app.ant :as ant]
            [clojure.string :as string]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn render-price-filters
  [{:keys [title key data]}]
  (let [min-key (keyword (str "min-" (name key) "-price"))
        max-key (keyword (str "max-" (name key) "-price"))
        min-price (get data min-key)
        max-price (get data max-key)
        set-price (fn [key price] (rf/dispatch [:update-left-panel-data {key price}]))
        parser-regexp #"Rp\s?|(,*)"
        parser #(string/replace (str %) parser-regexp "")
        formatter-regexp #"\B(?=(\d{3})+(?!\d))"
        formatter #(str "Rp " (string/replace (str %) formatter-regexp ","))]
    [:div.price-filters
     [:div title]
     [:div.price-inputs
      [ant/input-number {:value min-price
                         :step 1000
                         :on-change #(set-price min-key %)
                         :parser parser
                         :formatter formatter}]
      [:span.separetor "-"]
      [ant/input-number {:value max-price
                         :step 1000
                         :on-change #(set-price max-key %)
                         :parser parser
                         :formatter formatter}]]]))

(defn main []
  (r/with-let [visible (rf/subscribe [:left-panel-visible?])
               data (rf/subscribe [:left-panel-data])]
    [:div
     [ant/button {:on-click #(rf/dispatch [:open-left-panel])} "Filters"]
     [ant/drawer {:visible @visible
                  :placement "left"
                  :on-close #(rf/dispatch [:close-left-panel])
                  :width 400
                  :closable false
                  :class-name "left-panel"}
      [ant/collapse {:bordered false}
       [ant/collapse-panel {:header "Location"}]
       [ant/collapse-panel {:header "Price"}
        [render-price-filters {:title "Daily price"
                               :key :daily
                               :data @data
                               :min-max [10 500]}]
        [render-price-filters {:title "Weekly price"
                               :key :weekly
                               :data @data
                               :min-max [100 1000]}]
        [render-price-filters {:title "Monthly price"
                               :key :monthly
                               :data @data
                               :min-max [500 5000]}]]]]]))
