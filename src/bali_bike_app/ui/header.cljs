(ns bali-bike-app.ui.header
  (:require [bali-bike-app.ant :as ant]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn render-search []
  (r/with-let [constants (rf/subscribe [:constants])
               filters (rf/subscribe [:filters])
               on-model-search #(rf/dispatch [:update-filters {:model-id %}])]
    [:div.search-container
     [ant/select {:show-arrow false
                  :show-search true
                  :class-name "model-search"
                  :placeholder "Search"
                  :allow-clear true
                  :on-change on-model-search
                  :option-filter-prop "children"
                  :filter-option (fn [input option]
                                   (>= (.indexOf
                                        (.props.children.toLowerCase option)
                                        (.toLowerCase input))
                                       0))}
      (for [[id title] (sort-by val (:models @constants))]
        ^{:key id} [ant/select-option {:value id} title])]
     [ant/icon {:type "search"}]]))

(defn main []
  [:div.header
   [:div.left
    [:a {:href "/"}
     [:img.logo {:src "/img/logo.svg"}]]
    [render-search]]
   [:div.links
    [:a {:href "https://dashboard.balibike.app" :target "_blank"} "Add bike"]
    [:a {:href "https://itunes.apple.com/us/app/balibike/id1456497891?ls=1&mt=8"
         :target "_blank"}
     [:img.store-img {:src "/img/badgeappstore.png"}]]
    [:a {:href "https://play.google.com/store/apps/details?id=app.balibike"
         :target "_blank"}
     [:img.store-img {:src "/img/badgegoogleplay.png"}]]]])
