(ns bali-bike-app.ui.main-page
  (:require [bali-bike-app.ui.bikes-list :as bikes-list]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn main []
  (r/create-class
   {:component-did-mount #(rf/dispatch [:load-bikes])
    :render (fn []
              [bikes-list/main])}))
