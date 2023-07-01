package expression.generic.parsers;

import expression.generic.generic_terms.ExpressionGen;

@FunctionalInterface
public interface ExpressionParser<E> {
    ExpressionGen<E> parse(String s) throws Exception;
}
