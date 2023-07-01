package expression.generic.generic_terms;

public class ConstGen<E> implements ExpressionGen<E> {
    private final E value;

    public ConstGen(E value) {
        this.value = value;
    }


    @Override
    public E evaluate(E x, E y, E z) {
        return value;
    }

}
