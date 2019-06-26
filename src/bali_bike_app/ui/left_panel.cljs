(ns bali-bike-app.ui.left-panel
  (:require [bali-bike-app.ant :as ant]
            [clojure.string :as string]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn render-area-filter
  [{:keys [:area-id]}]
  (r/with-let [constants (rf/subscribe [:constants])]
    [ant/select {:placeholder "Select rental area"
                 :style {:width "100%"}
                 :value area-id
                 :onChange #(rf/dispatch [:update-left-panel-data {:area-id %}])
                 :allowClear true
                 :showSearch true
                 :optionFilterProp "children"
                 :filterOption (fn [input option]
                                 (>= (.indexOf
                                      (.props.children.toLowerCase option)
                                      (.toLowerCase input))
                                     0))}
     (for [[id title] (:areas @constants)]
       ^{:key id} [ant/select-option {:value id} title])]))

(defn render-price-filters
  [{:keys [title key data]}]
  (let [min-key (keyword (str "min-" (name key) "-price"))
        max-key (keyword (str "max-" (name key) "-price"))
        min-price (get data min-key)
        max-price (get data max-key)
        set-price (fn [key price] (rf/dispatch [:update-left-panel-data {key price}]))
        clear-filter #(rf/dispatch [:update-left-panel-data {min-key nil max-key nil}])
        parser-regexp #"Rp\s?|(,*)"
        parser #(string/replace (str %) parser-regexp "")
        formatter-regexp #"\B(?=(\d{3})+(?!\d))"
        formatter #(str "Rp " (string/replace (str %) formatter-regexp ","))]
    [:div.price-filters
     [:div.title
      [:span title]
      (when (or min-price max-price) [:a {:on-click clear-filter} "Clear"])]
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
    [ant/drawer {:visible @visible
                 :placement "left"
                 :on-close #(rf/dispatch [:close-left-panel])
                 :width 400
                 :closable false
                 :class-name "left-panel"}
     [ant/collapse {:bordered false :default-active-key ["area"]}
      [ant/collapse-panel {:header "Area" :key "area"}
       [render-area-filter @data]]]
     [ant/collapse {:bordered false :default-active-key ["price"]}
      [ant/collapse-panel {:header "Price" :key "price"}
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
                              :min-max [500 5000]}]]]

     [:div.bottom
      [ant/button {:type "primary"
                   :size "large"
                   :on-click #(rf/dispatch [:close-left-panel])} "Show bikes"]]]))
