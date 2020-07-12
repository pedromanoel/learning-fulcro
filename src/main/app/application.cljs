(ns app.application
  (:require [app.ui :as ui]
            [com.fulcrologic.fulcro.application :as app]))

(defonce app (app/fulcro-app))

(defn init []
  (app/mount! app ui/Root "app")
  (js/console.log "Loaded"))

(defn hot-reload []
  (app/mount! app ui/Root "app")
  (js/console.log "Hot reload"))