(ns bali-bike-app.api
  (:require [bali-bike-app.http :as http]
            [re-frame.core :as rf]
            [promesa.core :as p :refer-macros [alet]]
            [clojure.string]))

(goog-define api-url "http://localhost:4000")

(defn- vector-to-string
  [v]
  (str "["
       (clojure.string/join "," (mapv #(if (string? %) (str "\"" % "\"") %) v))
       "]"))

(defn parse-query [q]
  (cond
    (keyword? q)
    (let [[query alias] (clojure.string/split (name q) #"->")]
      (if alias
        (str alias ":" query)
        query))
    (map? q)
    (str \(
         (clojure.string/join
          \,
          (map (fn [[k v]]
                 (let [v (if (keyword? v) (name v) v)
                       v-meta (meta v)]
                   (cond
                     (:enum v-meta)
                     (str (name k) ":" (:key v))

                     (string? v)
                     (str (name k) ":\"" (str v) \")

                     (vector? v)
                     (str (name k) ":" (vector-to-string v))

                     :else
                     (str (name k) ":" (str v)))
                   ))
               q))
         \))
    (vector? q)
    (str \{
         (clojure.string/join
          \space
          (map parse-query q))
         \})

    :else
    (throw (js/Error. "Cannot parse query"))))

(defn- post
  [params]
  (http/POST api-url {:with-credentials? false :json-params params}))

(defn send-graphql
  [{:keys [query mutation callback-event]}]
  (let [parsed-query (if query (parse-query query) (str "mutation " (parse-query mutation)))]
    (->
     (alet [response (p/await (post {:query parsed-query}))]
           (when callback-event
             (rf/dispatch [callback-event (:body response)])))
     (p/catch (fn [error] (.log js/console (clj->js error)))))))
