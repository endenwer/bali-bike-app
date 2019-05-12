(ns bali-bike-app.routes
  (:require [re-frame.core :as rf]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]))

(def routes ["/" {"" :index
                  ["b/" :id] :bike
                  ["w/" :whatsapp] :whatsapp-bikes}])

(defn- parse-url [url]
  (bidi/match-route routes url))

(defn- dispatch-route [matched-route]
  (rf/dispatch [:set-active-page matched-route]))

(def history (pushy/pushy dispatch-route parse-url))

(defn app-routes []
  (pushy/start! history))

(def url-for (partial bidi/path-for routes))

(defn set-token!
  [token]
  (pushy/set-token! history token))
