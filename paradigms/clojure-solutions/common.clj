(load-file "./math.clj")

;-----------------------------------------------------------

(def extendedOperMap {
                      "+"      [+, sum_dif],
                      "-"      [-, substr_dif],
                      "*"      [*, mul_dif],
                      "/"      [safe_div, div_dif],
                      "negate" [-, substr_dif]
                      "sin"    [sinus, sin_dif]
                      "cos"    [cosin, cos_dif]
                      "sinh"   [sinh, sinh_dif]
                      "cosh"   [cosh, cosh_dif]
                      "ln"     [logarithm, log_diff]
                      "exp"    [exponent, exp_diff]
                      "atan"   [arctg, nil]
                      "atan2"  [arctg2, nil]
                      })
(def varSet #{"x" "y" "z"})


(defn parseExpr [expression isObj]
      (letfn
        [(parse [term]
                (cond
                  (number? term) ((if isObj Constant constant) (double term))

                  (and
                    (list? term) (contains? extendedOperMap (str (first term)))
                    )
                  (apply
                    (if isObj (AbstractOperation (str (first term)))
                              (abstract_operation (get (extendedOperMap (str (first term))) 0)))
                    (mapv parse (rest term))
                    )
                  ; error processing for future, just for fun
                  (and (symbol? term) (contains? varSet (str term)))
                  ((if isObj Variable variable) (str term))

                  :else (throw (Exception. "Unknown term"))
                  )
                )]
        (parse (read-string expression))
        )
      )