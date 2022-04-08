(ns app.ui.root
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom]
            [app.ui.person-list :as person-list]))

(defsc Root [_this person-lists]
  {:query [{:friends (comp/get-query person-list/PersonList)}
           {:enemies (comp/get-query person-list/PersonList)}]
   :initial-state
   (fn [_] {:friends (comp/get-initial-state person-list/PersonList {:id :friends :label "Friends"})
            :enemies (comp/get-initial-state person-list/PersonList {:id :enemies :label "Enemies"})})}
  (dom/div
   (map (comp person-list/ui-person-list second) person-lists)))
