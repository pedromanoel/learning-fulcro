(ns app.mutations
  (:require
   [com.fulcrologic.fulcro.algorithms.merge :as merge]
   [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(defmutation delete-person
  "Mutation: Delete the person :person/id from the list :list/id"
  [{list-id   :list/id
    person-id :person/id}]
  (action [{:keys [state]}]
          (swap! state merge/remove-ident* [:person/id person-id] [:list/id list-id :list/people])))
