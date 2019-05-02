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
  (let [bikes (edb/get-collection db :bikes :list)]
    (if (or
         (= 0 (count (:bikes data)))
         (and (not= 0 (count bikes)) (= (:id (last bikes)) (:id (last (:bikes data))))))
      (edb/insert-meta db :bikes :list {:loading? false :all-loaded? true})
      (edb/append-collection db :bikes :list (:bikes data) {:loading? false}))))

(defn load-bikes-event
  [{:keys [db]} [_ page]]
  (let [page-size 20
        bikes-meta (edb/get-collection-meta db :bikes :list)]
    (when-not (or (:all-loaded? bikes-meta) (:loading? bikes-meta))
      {:db (edb/insert-meta db :bikes :list {:loading? true})
       :api/send-graphql {:query
                          [:bikes {:first page-size :skip (* page page-size)} bike-query]
                          :callback-event :on-bikes-loaded}})))
