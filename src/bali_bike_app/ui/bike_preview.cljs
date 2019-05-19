(ns bali-bike-app.ui.bike-preview
  (:require [reagent.core :as r]
            [bali-bike-app.ant :as ant]))

(defn render-price
  [price title]
  [:div {:style {:font-size "12px" :text-align "right"}}
   (if price
     [:span {:style {:font-weight "600" :color "#25D366"}} (str "Rp " (.toLocaleString price))]
     [:span {:style {:font-weight "600"}} "Ask for price"])
   [:span {:style {:color "#a5a5a5"}} (str " / " title)]])

(defn render-prices
  [{:keys [daily-price weekly-price monthly-price]}]
  (if (or daily-price weekly-price monthly-price)
    [:div.price
     [render-price daily-price "day"]
     [render-price weekly-price "week"]
     [render-price monthly-price "month"]]
    [:div.price {:style {:font-weight "600"
                         :margin-top "-65px"
                         :padding "10px"
                         :font-size "12px"}}
     "Ask for price"]))

(defn main
  [{:keys [src] :as params}]
  (let [loaded? (r/atom false)]
    (r/create-class
     {:component-did-mount
      (fn []
        (let [img (js/Image.)]
          (set! (.-src img) src)
          (set! (.-onload img) #(reset! loaded? true))))
      :render
      (fn []
        (if @loaded?
          [:div.photo {:style {:background-image (str "url(\"" src "\")")}}
           [render-prices params]]
          [:<>
           [:div.photo-preload {:style {:background-image (str "url(\"" src "\")")}}]
           [:div.photo-loading]]))})))
