(ns bali-bike-app.events.bike
  (:require [bali-bike-app.edb :as edb]))

(def bike-query
  [:id :modelId :photos
   :dailyPrice :monthlyPrice :weeklyPrice
   :mileage :manufactureYear
   :areaIds :whatsapp])

;; events

(defn on-bikes-loaded-event
  [db [_ {:keys [data]}]]
  (edb/append-collection db :bikes :list (:bikes data) {:loading? false}))

(defn load-bikes-event
  [{:keys [db]} [_ _]]
  {:db (edb/insert-meta db :bikes :list {:loading? true})
   :api/send-graphql {:query
                      [:bikes {:first 20 :skip 0} bike-query]
                      :callback-event :on-bikes-loaded}})
