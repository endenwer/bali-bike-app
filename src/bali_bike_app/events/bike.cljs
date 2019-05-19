(ns bali-bike-app.events.bike
  (:require [bali-bike-app.edb :as edb]
            [bali-bike-app.utils :as utils]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [camel-snake-kebab.core :refer [->camelCaseKeyword]]))

(def page-size 20)

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
      (edb/append-collection
       db :bikes :list (:bikes data)
       {:loading? false :all-loaded? (< (count (:bikes data)) page-size)}))))

(defn filters-for-page
  [{:keys [handler route-params]}]
  (case handler
    :whatsapp-bikes {:whatsapp (:whatsapp route-params)}
    {}))

(defn load-bikes-event
  [{:keys [db]} [_ _]]
  (let [bikes-meta (edb/get-collection-meta db :bikes :list)
        skip (or (:skip bikes-meta) 0)
        bikes-order (:bikes-order db)
        order-by (str (:type bikes-order) "_" (:direction bikes-order))
        active-page (:active-page db)
        page-filters (filters-for-page active-page)
        filters (transform-keys ->camelCaseKeyword (merge page-filters (:filters db)))
        pagination-vars {:first page-size :skip skip}
        query-vars (merge pagination-vars filters
                          {:orderBy (with-meta {:key order-by} {:enum true})})]
    (when-not (or (:all-loaded? bikes-meta) (:loading? bikes-meta))
      {:db (edb/insert-meta db :bikes :list {:loading? true :skip (+ skip page-size)})
       :api/send-graphql {:query
                          [:bikes query-vars bike-query]
                          :callback-event :on-bikes-loaded}})))

(defn update-filters-event
  [{:keys [db]} [_ new-filters]]
  (let [filters (:filters db)
        updated-filters (utils/clean-merge filters new-filters)]
    (when-not (= filters updated-filters)
      {:db (-> db
               (assoc :filters updated-filters)
               (edb/remove-collection :bikes :list)
               (edb/insert-meta :bikes :list {:all-loaded? false}))
       :dispatch [:load-bikes]
       :set-url "/"})))

(defn on-bike-loaded-event
  [db [_ {:keys [data]}]]
  (edb/insert-named-item db :bikes :current (:bike data) {:loading? false}))

(defn load-current-bike-event
  [{:keys [db]} [_ _]]
  (let [bike-id (get-in db [:active-page :route-params :id])]
    {:db (edb/insert-named-item db :bikes :current {} {:loading? true})
     :api/send-graphql {:query [:bike {:id bike-id} bike-query]
                        :callback-event :on-bike-loaded}}))

(defn change-bikes-order-type-event
  [{:keys [db]} [_ order-type]]
  {:db (-> db
           (assoc-in [:bikes-order :type] order-type)
           (edb/remove-collection :bikes :list))
   :dispatch [:load-bikes]})

(defn toggle-bikes-order-direction-event
  [{:keys [db]} [_ _]]
  (let [current-direction (get-in db [:bikes-order :direction])
        new-direction (if (= current-direction "DESC") "ASC" "DESC")]
    {:db (-> db
             (assoc-in [:bikes-order :direction] new-direction)
             (edb/remove-collection :bikes :list))
     :dispatch [:load-bikes]}))
