(ns bali-bike-app.ant
  (:require ["antd/lib/spin" :default Spin]
            ["antd/lib/select" :default Select]
            ["antd/lib/tag" :default Tag]
            ["antd/lib/icon" :default Icon]
            ["antd/lib/empty" :default Empty]
            ["antd/lib/button" :default Button]
            ["antd/lib/collapse" :default Collapse]
            ["antd/lib/slider" :default Slider]
            ["antd/lib/input" :default Input]
            ["antd/lib/drawer" :default Drawer]
            ["antd/lib/input-number" :default InputNumber]
            ["antd/lib/menu" :default Menu]
            ["antd/lib/dropdown" :default Dropdown]
            [reagent.core :as r]))

(def dropdown (r/adapt-react-class Dropdown))
(def menu (r/adapt-react-class Menu))
(def menu-item (r/adapt-react-class (.-Item Menu)))
(def drawer (r/adapt-react-class Drawer))
(def input-number (r/adapt-react-class InputNumber))
(def input (r/adapt-react-class Input))
(def slider (r/adapt-react-class Slider))
(def collapse (r/adapt-react-class Collapse))
(def collapse-panel (r/adapt-react-class (.-Panel Collapse)))
(def button (r/adapt-react-class Button))
(def button-group (r/adapt-react-class (.-Group Button)))
(def spin (r/adapt-react-class Spin))
(def empty-data (r/adapt-react-class Empty))
(def icon (r/adapt-react-class Icon))
(def tag (r/adapt-react-class Tag))
(def select (r/adapt-react-class Select))
(def select-option (r/adapt-react-class (.-Option Select)))
