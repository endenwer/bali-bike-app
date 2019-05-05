(ns bali-bike-app.ui.bike-page
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ant :as ant]))

(defn render-page []
  (r/with-let [bike (rf/subscribe [:current-bike])
               bike-meta (rf/subscribe [:current-bike-meta])]
    [:div.bike-page
     (if (:loading? @bike-meta)
       [ant/spin]
       [:div (:id @bike)])]))

(defn main []
  (r/create-class
   {:component-did-mount #(rf/dispatch [:load-current-bike])
    :render render-page}))
