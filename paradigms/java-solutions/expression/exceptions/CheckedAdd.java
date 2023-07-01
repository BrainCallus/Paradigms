package expression.exceptions;

import expression.OperationBin;
import expression.Term;

public class CheckedAdd extends CheckedOperationBin {
    public CheckedAdd(Term term1, Term term2) {
        super(term1, term2, "+", 3, 3, 3);
    }

    @Override
    protected int getValue(int x, int y) {
        if ((x > 0 && y > 0 && x + y <= 0) || (x < 0 && y < 0 && x + y >= 0)) {
            throw new OverflowException("overflow");
        }
        return x + y;
    }

}