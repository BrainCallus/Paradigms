package expression;

public class Divide extends OperationBin {

    public Divide(Term term1, Term term2) {
        super(term1, term2, "/", 5, 7, 5);
    }

    @Override
    protected int getValue(int x, int y) {
        return x / y;
    }

}

