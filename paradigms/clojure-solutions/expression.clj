; sory for this code-fir but this simplifies the brackets sequences control
(load-file "./proto.clj")
(load-file "./common.clj")
; :NOTE: почему не использовать код с лекции?

;-------------------------------------------------
; functional expression

(defn abstract_operation [oper]
      (fn [& terms]
          (fn [xyz]
              (apply oper (mapv (fn [func] (func xyz)) terms))
              )
          )
      )
(def add (abstract_operation +))
(def subtract (abstract_operation -))
(def negate subtract)
(def multiply (abstract_operation *))
(def divide (abstract_operation safe_div))
(def exp (abstract_operation exponent))
(def ln (abstract_operation logarithm))
(def arcTan (abstract_operation arctg))
(def arcTan2 (abstract_operation arctg2))

(defn constant [x] (fn [xyz] x))

(defn variable [name] (fn [xyz] (xyz name)))

(defn parseFunction [expression] (parseExpr expression false))



;-------------------------------------------------------------------------
; object expression
; :NOTE: где proto с лекции?

(defn evalTerms [xyz & terms] (map (fn [term] (evaluate term xyz)) terms))
(defn termsToStr [& terms] (mapv
                             (fn [term] (str (toString term)))
                             terms))

(def protoAbstract
  {
   :evaluate (fn [this xyz]
                 (apply (get (extendedOperMap (this :oper)) 0) (apply evalTerms xyz (this :terms)))
                 )

   :toString (fn [this]
                 (str
                   (apply str "(" (this :oper) " "
                          (clojure.string/join " " (apply termsToStr (this :terms)))
                          )
                   ")")
                 )

   :diff     (fn [this var] ((get (extendedOperMap (this :oper)) 1) this var))
   }
  )

(defn AbstractOperation [oper]
      (let [this {:prototype protoAbstract :oper oper}]
           (fn [& terms] (assoc this :terms terms)))
      )


(defn Constant [val]
      {
       :evaluate (fn [this xyz] (double val))
       :toString (fn [& xyz] (str (double val)))
       :diff     (fn [this var] (Constant 0.0))
       }
      )

(defn Variable [name]
      {
       :evaluate (fn [this xyz] (xyz name))
       :toString (fn [& xyz] (str name))
       :diff     (fn [this var] (if (identical? name var) (Constant 1.0) (Constant 0.0)))
       }
      )


(def Add (AbstractOperation "+"))
(def Subtract (AbstractOperation "-"))
(def Multiply (AbstractOperation "*"))
(def Divide (AbstractOperation "/"))
(def Negate (AbstractOperation "negate"))
(def Sin (AbstractOperation "sin"))
(def Cos (AbstractOperation "cos"))
(def Sinh (AbstractOperation "sinh"))
(def Cosh (AbstractOperation "cosh"))
; :NOTE: где общая часть 10 и 11 дз?

(defn parseObject [expression] (parseExpr expression true))