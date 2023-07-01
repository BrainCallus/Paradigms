package expression.exceptions;

import expression.Term;

public class CheckedMultiply extends CheckedOperationBin {
    public CheckedMultiply(Term term1, Term term2) {
        super(term1, term2, "*", 5, 6, 6);
    }

    @Override
    protected int getValue(int x, int y) {
        if ((x != 0 && x * y / x != y) || (y != 0 && x * y / y != x)) {
            throw new OverflowException("overflow");
        }
        return x * y;
    }

}

