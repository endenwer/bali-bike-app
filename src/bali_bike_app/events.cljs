(ns bali-bike-app.events
  (:require [bali-bike-app.events.bike :as bike-events]
            [bali-bike-app.api :as api]
            [bali-bike-app.interceptors :as interceptors]
            [re-frame.core :as rf]))

; bikes

(rf/reg-event-fx :load-bikes bike-events/load-bikes-event)
(rf/reg-event-db :on-bikes-loaded
                 [interceptors/transform-event-to-kebab]
                 bike-events/on-bikes-loaded-event)

; api
(rf/reg-fx :api/send-graphql api/send-graphql)
