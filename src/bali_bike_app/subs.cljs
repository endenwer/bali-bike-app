(ns bali-bike-app.subs
  (:require [re-frame.core :as rf]
            [bali-bike-app.edb :as edb]))

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
