package expression.generic.computable;

import java.util.function.BiFunction;
import java.util.function.Function;

// стоит ли наследовать от Computable<E> в текущей реализации?
// В общем случае класс, имплементирующий Checked<E> может не реализовывать Computable,
// если, например, он работает со строками или другими нечисленными объектами;
// но в данной программе таких примеров нет, поэтому возникает еще вопрос, насколько полезно
// в принципе в данном случае выделять эти методы в отдельный интерфейс
public interface Checked<E> {
    E checkedBinaryOperation(E x, E y, boolean predicate, BiFunction<E, E, E> operation);

    E checkedUnaryOperation(E x, boolean predicate, Function<E, E> operation);
}
