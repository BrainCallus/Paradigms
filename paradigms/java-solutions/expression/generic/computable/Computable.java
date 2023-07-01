package expression.generic.computable;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Computable<E> {
    E add(E x, E y);

    E sub(E x, E y);

    E mul(E x, E y);

    E div(E x, E y);

    E neg(E x);

    E parse(String x) throws Exception;

    E castArg(int x);
}
