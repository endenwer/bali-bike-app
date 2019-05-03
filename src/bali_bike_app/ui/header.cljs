(ns bali-bike-app.ui.header
  (:require [bali-bike-app.ant :as ant]
            [bali-bike-app.constants :as constants]))

(defn main []
  [:div.header
   [:a {:href "/"}
    [:img.logo {:src "img/logo.svg"}]]
   [ant/select {:show-arrow false
                :show-search true
                :class-name "model-search"
                :placeholder "Search"}
    (for [[id title] constants/models]
      ^{:key id} [ant/select-option {:value id} title])]
   [:div.links
    [:a {:href "https://dashboard.balibike.app" :target "_blank"} "Add bike"]
    [:a {:href "https://itunes.apple.com/us/app/balibike/id1456497891?ls=1&mt=8"
         :target "_blank"}
     [:img.store-img {:src "img/badgeappstore.png"}]]
    [:a {:href "https://play.google.com/store/apps/details?id=app.balibike"
         :target "_blank"}
     [:img.store-img {:src "img/badgegoogleplay.png"}]]]])
