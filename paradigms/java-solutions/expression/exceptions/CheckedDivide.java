package expression.exceptions;

import expression.Term;

public class CheckedDivide extends CheckedOperationBin {

    public CheckedDivide(Term term1, Term term2) {
        super(term1, term2, "/", 5, 7, 5);
    }

    @Override
    protected int getValue(int x, int y) {
        if (y == 0) {
            throw new OverflowException("division by zero");
        } else if (x < 0 && y < 0 && x / y < 0) {
            throw new OverflowException("overflow");
        }
        return x / y;
    }

}

