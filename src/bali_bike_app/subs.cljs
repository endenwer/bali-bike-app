(ns bali-bike-app.subs
  (:require [re-frame.core :as rf]
            [bali-bike-app.edb :as edb]))

(rf/reg-sub
 :current-bike
 (fn [app-db _]
   (edb/get-named-item app-db :bikes :current)))

(rf/reg-sub
 :current-bike-meta
 (fn [app-db _]
   (edb/get-item-meta app-db :bikes :current)))

(rf/reg-sub
 :bikes
 (fn [app-db _]
   (edb/get-collection app-db :bikes :list)))

(rf/reg-sub
 :bikes-meta
 (fn [app-db _]
   (edb/get-collection-meta app-db :bikes :list)))

(rf/reg-sub
 :constants
 (fn [app-db _]
   (:constants app-db)))

(rf/reg-sub
 :filters
 (fn [app-db _]
   (:filters app-db)))

(rf/reg-sub
 :active-page
 (fn [app-db _]
   (:active-page app-db)))

(rf/reg-sub
 :left-panel-visible?
 (fn [app-db _]
   (:left-panel-visible? app-db)))

(rf/reg-sub
 :left-panel-data
 (fn [app-db _]
   (:left-panel-data app-db)))
