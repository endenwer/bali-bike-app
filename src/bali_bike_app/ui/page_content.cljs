(ns bali-bike-app.ui.page-content
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [bali-bike-app.ui.not-found-page :as not-found-page]
            [bali-bike-app.ui.bike-page :as bike-page]
            [bali-bike-app.ui.main-page :as main-page]))

(defn main []
  (r/with-let [active-page (rf/subscribe [:active-page])]
    (case (:handler @active-page)
      :index [main-page/main]
      :bike [bike-page/main]
      [not-found-page/main])))
