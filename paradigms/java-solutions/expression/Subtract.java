package expression;

public class Subtract extends OperationBin {
    public Subtract(Term term1, Term term2) {
        super(term1, term2, "-", 3, 5, 4);
    }

    @Override
    protected int getValue(int x, int y) {
        return x - y;
    }

}

