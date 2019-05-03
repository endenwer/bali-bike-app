(ns bali-bike-app.ant
  (:require ["antd/lib/spin" :default Spin]
            ["antd/lib/select" :default Select]
            [reagent.core :as r]))

(def spin (r/adapt-react-class Spin))
(def select (r/adapt-react-class Select))
(def select-option (r/adapt-react-class (.-Option Select)))
