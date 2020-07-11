(ns app.client
  (:require
    [app.application :as app]
    [app.ui :as ui]))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function.
  See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (app/init))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot-reload of source.
  See shadow-cljs.edn"
  []
  (app/hot-reload))