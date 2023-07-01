package expression.exceptions;

import expression.Term;

public class CheckedNegate extends CheckedOperationUno {
    public CheckedNegate(Term term) {
        super(term, "-");
    }

    @Override
    protected int getValue(int x) {
        if (x < 0 && -1 * x < 0) {
            throw new OverflowException("overflow");
        }
        return x * -1;
    }
}
