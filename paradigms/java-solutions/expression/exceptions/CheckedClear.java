package expression.exceptions;

import expression.Term;

public class CheckedClear extends CheckedOperationBin {

    public CheckedClear(Term term1, Term term2) {
        super(term1, term2, "clear", 1, 2, 1);
    }

    @Override
    protected int getValue(int x, int y) {

        return x & (((1 << 31) -  1 - (1 << y)))+(x<0?Integer.MIN_VALUE:0);
    }
}
