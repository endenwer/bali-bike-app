(ns bali-bike-app.utils
  (:require [clojure.string :as string]))

(defn clean-merge
  [filters new-filters]
  (into {} (remove (comp string/blank? second) (merge filters new-filters))))
