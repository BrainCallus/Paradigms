package expression.generic.generic_terms;

import expression.generic.computable.Executor;

public class OperationGen<E> implements ExpressionGen<E> {
    private final ExpressionGen<E> term1;
    private final ExpressionGen<E> term2;
    private final TermTypes operator;
    private final Executor<E> executor;

    public OperationGen(ExpressionGen<E> term1, ExpressionGen<E> term2, TermTypes operator,
                        Executor<E> executor) {
        this.term1 = term1;
        this.term2 = term2;
        this.operator = operator;
        this.executor = executor;
    }

    public OperationGen(ExpressionGen<E> term1, TermTypes operator, Executor<E> executor) {
        this.term1 = term1;
        this.term2 = null;
        this.operator = operator;
        this.executor = executor;
    }

    @Override
    public E evaluate(E x, E y, E z) throws Exception {
        return isUnary() ? executor.exec(operator, term1.evaluate(x, y, z)) :
                executor.exec(operator, term1.evaluate(x, y, z), term2.evaluate(x, y, z));
    }

    private boolean isUnary() {
        return term2 == null;
    }
}
