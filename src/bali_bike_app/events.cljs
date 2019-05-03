(ns bali-bike-app.events
  (:require [bali-bike-app.events.bike :as bike-events]
            [bali-bike-app.api :as api]
            [bali-bike-app.interceptors :as interceptors]
            [re-frame.core :as rf]))

; bikes

(rf/reg-event-fx :load-bikes bike-events/load-bikes-event)
(rf/reg-event-fx :add-filters bike-events/add-filters-event)
(rf/reg-event-db :on-bikes-loaded
                 [interceptors/transform-event-to-kebab]
                 bike-events/on-bikes-loaded-event)

; api
(rf/reg-fx :api/send-graphql api/send-graphql)

(rf/reg-event-db
 :on-constants-loaded
 [interceptors/transform-event-to-kebab]
 (fn [db [_ {:keys [data]}]]
   (let [constants (:constants data)
         models (reduce #(assoc %1 (:id %2) (:value %2)) {} (:models constants))
         areas (reduce #(assoc %1 (:id %2) (:value %2)) {} (:areas constants))]
     (assoc db :constants {:models models :areas areas}))))

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db {}
    :api/send-graphql {:query [:constants [:models [:id :value] :areas [:id :value]]]
                       :callback-event :on-constants-loaded}}))
