(ns bali-bike-app.ant
  (:require ["antd/lib/spin" :default Spin]
            ["antd/lib/select" :default Select]
            ["antd/lib/tag" :default Tag]
            ["antd/lib/icon" :default Icon]
            ["antd/lib/empty" :default Empty]
            ["antd/lib/button" :default Button]
            [reagent.core :as r]))

(def button (r/adapt-react-class Button))
(def spin (r/adapt-react-class Spin))
(def empty-data (r/adapt-react-class Empty))
(def icon (r/adapt-react-class Icon))
(def tag (r/adapt-react-class Tag))
(def select (r/adapt-react-class Select))
(def select-option (r/adapt-react-class (.-Option Select)))
