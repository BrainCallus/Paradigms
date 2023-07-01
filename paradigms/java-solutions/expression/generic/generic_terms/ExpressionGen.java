package expression.generic.generic_terms;

@FunctionalInterface
public interface ExpressionGen<E> {
    E evaluate(E x, E y, E z) throws Exception;
}
