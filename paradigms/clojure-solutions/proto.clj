(defn proto-get
      "Returns object property respecting the prototype chain"
      ([obj key] (proto-get obj key nil))
      ([obj key default]
       (cond
         (contains? obj key) (obj key)
         (contains? obj :prototype) (proto-get (obj :prototype) key default)
         :else default)))

(defn proto-call
      "Calls object method respecting the prototype chain"
      [this key & args]
      (apply (proto-get this key) this args))

(defn toString [expr] (proto-call expr :toString))
(defn evaluate [expr xyz] (proto-call expr :evaluate xyz))
(defn diff [expr var] (proto-call expr :diff var))
(defn getTerms [expr] (proto-get expr :terms))
(defn headTerm [expr] (first (getTerms expr)))
(defn tailTerms [expr] (rest (getTerms expr)))