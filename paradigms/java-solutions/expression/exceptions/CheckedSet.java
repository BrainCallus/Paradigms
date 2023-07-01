package expression.exceptions;

import expression.Term;

public class CheckedSet extends CheckedOperationBin{
    public CheckedSet(Term term1, Term term2){
        super(term1,term2,"set",1,2,1);
    }

    @Override
    protected int getValue(int x, int y) {
        return x|(1<<y);
    }
}
