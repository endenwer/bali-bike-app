(ns bali-bike-app.events.left-panel
  (:require [bali-bike-app.utils :as utils]))

(def filter-names
  [:min-daily-price
   :max-daily-price
   :min-weekly-price
   :max-weekly-price
   :min-monthly-price
   :max-monthly-price])

(defn open-event
  [db _]
  (assoc db
         :left-panel-visible? true
         :left-panel-data (:filters db)))

(defn close-event
  [{:keys [db]} _]
  (let [data (:left-panel-data db)
        filters (reduce #(assoc %1 %2 (get data %2)) {} filter-names)]
    {:db (-> db
             (assoc :left-panel-visible? false)
             (dissoc :left-panel-data))
     :dispatch [:update-filters filters]}))

(defn update-data-event
  [db [_ new-data]]
  (assoc db :left-panel-data (utils/clean-merge (:data db) new-data)))
