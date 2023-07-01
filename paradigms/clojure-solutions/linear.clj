(defn size-check [v] (apply == (mapv count v)))

(defn shape-check [& terms] (and (every? vector? terms) (size-check terms)
                                 (every? (partial mapv shape-check) terms)))

(defn vector-correctness-check [v] (and (vector? v) (every? number? v)))

(defn matrix-correctness-check [matrix] (and (vector? matrix)
                                             (every? vector-correctness-check matrix) (size-check matrix)))

(defn abstract_vector_operation
      ;; single not null oper argument
      [oper]
      (fn [& terms]
          (apply mapv oper terms)))

(def v+ (abstract_vector_operation +))
(def v- (abstract_vector_operation -))
(def v* (abstract_vector_operation *))
(def vd (abstract_vector_operation /))

(defn v*s
      [vect & numbers] {:pre [(vector-correctness-check vect) (every? number? numbers)]
                        }
      (mapv (fn [x] (apply * x numbers)) vect)
      )
(defn scalar
      [& vectors] {:pre  [(every? vector-correctness-check vectors)]
                   :post [(number? %)]}
      (apply + (apply v* vectors)))

(defn vect
      [& vectors] {:pre  [(every? vector-correctness-check vectors) (every? (partial = 3) (mapv count vectors))]
                   :post [(vector-correctness-check %)]}
      (reduce (fn [v1 v2]
                  (vector
                    (- (* (nth v1 1) (nth v2 2)) (* (nth v1 2) (nth v2 1)))
                    (- (* (nth v1 2) (nth v2 0)) (* (nth v1 0) (nth v2 2)))
                    (- (* (nth v1 0) (nth v2 1)) (* (nth v1 1) (nth v2 0)))
                    )
                  ) vectors)
      )

(defn transpose
      [matrix] {:pre [(matrix-correctness-check matrix)]}
      (apply mapv vector matrix))

(defn m*s
      [matrix & s] {:pre [(matrix-correctness-check matrix) (every? number? s)]}
      (mapv (fn [x] (apply v*s x s)) matrix))

(defn m*v
      [matrix & vectors] {:pre [(matrix-correctness-check matrix) (every? vector-correctness-check vectors)]}
      (mapv (fn [x] (apply scalar x vectors)) matrix))

(defn m*m
      ;; 2 matrix arguments: m1[M x N] m2[N x T]
      [& matrices] {:pre [(every? matrix-correctness-check matrices)]}
      (reduce
        (fn [mat1 mat2]
            (transpose (mapv (partial m*v mat1) (transpose mat2)))) matrices)

      )

(def m+ (abstract_vector_operation v+))
(def m- (abstract_vector_operation v-))
(def m* (abstract_vector_operation v*))
(def md (abstract_vector_operation vd))

(defn abstract_shapeless [oper]
      (fn [& terms] (cond
                      (every? number? terms) (apply oper terms)
                      :else (apply mapv (abstract_shapeless oper) terms)
                      )))

(def s+ (abstract_shapeless +))
(def s- (abstract_shapeless -))
(def s* (abstract_shapeless *))
(def sd (abstract_shapeless /))


