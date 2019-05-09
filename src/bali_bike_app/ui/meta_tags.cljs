(ns bali-bike-app.ui.meta-tags
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["react-helmet" :default Helmet]))

(def helmet (r/adapt-react-class Helmet))

(defmulti render-tags (fn [page] (:handler page)))

(defmethod render-tags :index []
  [helmet
   [:title "BaliBike"]])

(defmethod render-tags :bike [{:keys [route-params]}]
  (r/with-let [bike (rf/subscribe [:current-bike])
               bike-meta (rf/subscribe [:current-bike-meta])
               constants (rf/subscribe [:constants])]
    (when-not (:loading? @bike-meta)
      [helmet
       [:title (str (get-in @constants [:models (:model-id @bike)]) " - BaliBike")]
       [:meta {:name "og:title"
               :property "og:title"
               :content (str (get-in @constants [:models (:model-id @bike)]) " - BaliBike")}]
       [:meta {:name "og:image"
               :property "og:image"
               :content (first (:photos @bike))}]])))

(defmethod render-tags :default [])

(defn main []
  (r/with-let [page (rf/subscribe [:active-page])]
    [render-tags @page]))
