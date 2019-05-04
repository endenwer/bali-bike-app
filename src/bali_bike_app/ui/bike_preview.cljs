(ns bali-bike-app.ui.bike-preview
  (:require [reagent.core :as r]))

(defn main
  [{:keys [src]}]
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
          [:div.photo {:style {:background-image (str "url(\"" src "\")")}}]
          [:<>
           [:div.photo-preload {:style {:background-image (str "url(\"" src "\")")}}]
           [:div.photo-loading]]))})))
