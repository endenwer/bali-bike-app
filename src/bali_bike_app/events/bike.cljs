(ns bali-bike-app.events.bike
  (:require [bali-bike-app.edb :as edb]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [camel-snake-kebab.core :refer [->camelCaseKeyword]]))

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
  [{:keys [db]} [_ _]]
  (let [page-size 20
        bikes-meta (edb/get-collection-meta db :bikes :list)
        skip (or (:skip bikes-meta) 0)
        filters (transform-keys ->camelCaseKeyword (:filters db))
        pagination-vars {:first page-size :skip skip}
        query-vars (merge pagination-vars filters)]
    (when-not (or (:all-loaded? bikes-meta) (:loading? bikes-meta))
      {:db (edb/insert-meta db :bikes :list {:loading? true :skip (+ skip page-size)})
       :api/send-graphql {:query
                          [:bikes query-vars bike-query]
                          :callback-event :on-bikes-loaded}})))

(defn add-filters-event
  [{:keys [db]} [_ filters]]
  (let [current-filters (:filters db)
        merged-filters (merge current-filters filters)
        new-filters (into {} (remove (comp nil? second) merged-filters))]
    (if (= current-filters new-filters)
      {:db (assoc db :filters merged-filters)}
      {:db (-> db
               (assoc :filters new-filters)
               (edb/remove-collection :bikes :list)
               (edb/insert-meta :bikes :list {:all-loaded? false}))
       :dispatch [:load-bikes]})))
