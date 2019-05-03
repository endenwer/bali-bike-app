(ns bali-bike-app.ui.main-page
  (:require [bali-bike-app.ui.bikes-list :as bikes-list]
            [bali-bike-app.ui.left-panel :as left-panel]))

(defn main []
  [:div.main-page
   ;[left-panel/main]
   [bikes-list/main]])
