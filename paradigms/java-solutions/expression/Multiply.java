package expression;

public class Multiply extends OperationBin {
    public Multiply(Term term1, Term term2) {
        super(term1, term2, "*", 5, 6, 6);
    }

    @Override
    protected int getValue(int x, int y) {
        return x * y;
    }
}

