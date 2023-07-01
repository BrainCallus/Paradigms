package expression.generic.computable;

import expression.generic.generic_terms.TermTypes;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Executor<E> implements Computable<E>, Checked<E> {
    public E exec(TermTypes operation, E x) {
        // parameter 'operation' is left in case other unary operations occurs
        if (x == null) return null;
        return neg(x);
    }

    public E exec(TermTypes operation, E x, E y) {
        if (x == null || y == null) {
            return null;
        }
        return switch (operation) {
            case ADD -> add(x, y);
            case SUB -> sub(x, y);
            case MUL -> mul(x, y);
            case DIV -> div(x, y);
            default -> null;
        };
    }

    @Override
    public E checkedBinaryOperation(E x, E y, boolean predicate,
                                    BiFunction<E, E, E> operation) {
        return predicate ? null : operation.apply(x, y);

    }

    @Override
    public E checkedUnaryOperation(E x, boolean predicate,
                                   Function<E, E> operation) {
        return predicate ? null : operation.apply(x);
    }

}
