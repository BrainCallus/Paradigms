(def safe_div
  (fn [& terms]
      (cond
        (= 1 (count terms)) (/ 1.0 (double (first terms)))
        :else (* (first terms)
                 (apply * (mapv #(/ 1.0 (double %)) (rest terms)))
                 )
        )
      )
  )
(def logarithm (fn [x] (Math/log (abs (double x)))))
(def exponent (fn [x] (Math/exp (double x))))
(def arctg (fn [x] (Math/atan (double x))))
(def arctg2 (fn [x y] (Math/atan2 (double x) (double y))))
(def sinus (fn [x] (Math/sin (double x))))
(def cosin (fn [x] (Math/cos (double x))))
(def cosh (fn [x] (Math/cosh (double x))))
(def sinh (fn [x] (Math/sinh (double x))))

(def AbstractOperation)
(def Add)
(def Subtract)
(def Multiply)
(def Divide)
(def Negate)
(def Constant)
(def Variable)
(def Sin)
(def Cos)
(def Sinh)
(def Cosh)
(def abstract_operation)
(def constant)
(def variable)

; Differentials
; :NOTE: copypaste

(def Power
  (fn [expr pow]
      (cond
        (zero? pow) (Constant 1)
        (even? pow) (Power (Multiply expr expr) (quot pow 2))
        (odd? pow) (Multiply expr (Power expr (dec pow)))
        )
      )
  )

(defn add_sub_mul_diff [operation func expr var]
      (apply operation (mapv
                         (fn [term] (func term var)) (getTerms expr))
             ))
(defn sum_dif [expr var] (add_sub_mul_diff Add diff expr var))

(defn substr_dif [expr var] (add_sub_mul_diff Subtract diff expr var))

(defn mul_dif [expr var]
      (add_sub_mul_diff Add
                        (fn [term var]
                            (Multiply
                              (diff term var)
                              (apply Multiply (filter #(-> % (not= term)) (getTerms expr)))
                              ))
                        expr var))

(defn div_dif [expr var]
      (if
        (= (count (getTerms expr)) 1)
        (Negate
          (Divide (diff (headTerm expr) var) (Power (headTerm expr) 2))
          )
        (Divide
          (Subtract
            (Multiply (diff (headTerm expr) var) (apply Multiply (tailTerms expr)))
            (Multiply (headTerm expr) (mul_dif (apply Multiply (tailTerms expr)) var))
            )
          (Power (apply Multiply (tailTerms expr)) 2)
          )
        )
      )

(defn geom_diff [operation expr var]
      (Multiply (operation (headTerm expr)) (diff (headTerm expr) var))
      )

(defn sin_dif [expr var] (geom_diff Cos expr var))
(defn cos_dif [expr var] (Negate (geom_diff Sin expr var)))


(defn sinh_dif [expr var] (geom_diff Cosh expr var))
(defn cosh_dif [expr var] (geom_diff Sinh expr var))

(defn exp_diff [expr var] (Multiply (diff (headTerm expr) var) expr))
(defn log_diff [expr var] (Divide (diff headTerm expr) var) (headTerm expr))