package expression.exceptions;

import expression.Term;

public class CheckedSubtract extends CheckedOperationBin {
    public CheckedSubtract(Term term1, Term term2) {
        super(term1, term2, "-", 3, 5, 4);
    }

    @Override
    protected int getValue(int x, int y) {
        if ((x >= 0 && y < 0 && x > Integer.MAX_VALUE + y) ||
                (x < 0 && y > 0 && x < Integer.MIN_VALUE + y)) {
            throw new OverflowException("overflow");
        }
        return x - y;
    }

}

