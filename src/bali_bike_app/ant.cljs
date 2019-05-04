(ns bali-bike-app.ant
  (:require ["antd/lib/spin" :default Spin]
            ["antd/lib/select" :default Select]
            ["antd/lib/tag" :default Tag]
            [reagent.core :as r]))

(def spin (r/adapt-react-class Spin))
(def tag (r/adapt-react-class Tag))
(def select (r/adapt-react-class Select))
(def select-option (r/adapt-react-class (.-Option Select)))
