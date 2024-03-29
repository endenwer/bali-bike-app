(ns bali-bike-app.events
  (:require [bali-bike-app.events.bike :as bike-events]
            [bali-bike-app.events.left-panel :as left-panel-events]
            [bali-bike-app.api :as api]
            [bali-bike-app.interceptors :as interceptors]
            [bali-bike-app.routes :as routes]
            [bali-bike-app.edb :as edb]
            [re-frame.core :as rf]))

(rf/reg-fx
 :set-url
 (fn [url]
   (routes/set-token! url)))

; bikes

(rf/reg-event-fx :load-bikes bike-events/load-bikes-event)
(rf/reg-event-fx :load-current-bike bike-events/load-current-bike-event)
(rf/reg-event-fx :update-filters bike-events/update-filters-event)
(rf/reg-event-fx :change-bikes-order-type bike-events/change-bikes-order-type-event)
(rf/reg-event-fx :toggle-bikes-order-direction bike-events/toggle-bikes-order-direction-event)
(rf/reg-event-db :on-bike-loaded
                 [interceptors/transform-event-to-kebab]
                 bike-events/on-bike-loaded-event)
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

(defn page-initial-event
  [{:keys [handler]}]
  (case handler
    :index [:load-bikes]
    :whatsapp-bikes [:load-bikes]
    :bike [:load-current-bike]))

(rf/reg-event-fx
 :set-active-page
 (fn [{:keys [db]} [_ page]]
   {:db (-> db
            (assoc :active-page page)
            (edb/remove-collection :bikes :list))
    :dispatch (page-initial-event page)}))

(rf/reg-event-db :open-left-panel left-panel-events/open-event)
(rf/reg-event-fx :close-left-panel left-panel-events/close-event)
(rf/reg-event-db :update-left-panel-data left-panel-events/update-data-event)

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db {:bikes-order {:type "DATE" :direction "DESC"}}
    :api/send-graphql {:query [:constants [:models [:id :value] :areas [:id :value]]]
                       :callback-event :on-constants-loaded}}))
