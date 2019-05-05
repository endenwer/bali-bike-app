(ns bali-bike-app.routes
  (:require [re-frame.core :as rf]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]))

(def routes ["/" {"" :index
                  ["b/" :id] :bike}])

(defn- parse-url [url]
  (bidi/match-route routes url))

(defn- dispatch-route [matched-route]
  (rf/dispatch [:set-active-page matched-route]))

(defn app-routes []
  (pushy/start! (pushy/pushy dispatch-route parse-url)))

(def url-for (partial bidi/path-for routes))
