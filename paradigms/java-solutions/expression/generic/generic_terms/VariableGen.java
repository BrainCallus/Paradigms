package expression.generic.generic_terms;

public class VariableGen<E> implements ExpressionGen<E> {
    private final String name;

    public VariableGen(String name) {
        this.name = name;
    }

    @Override
    public E evaluate(E x, E y, E z) throws Exception {
        return switch (name) {
            case ("x") -> x;
            case ("y") -> y;
            case ("z") -> z;
            default -> null;
        };
    }

}
