package expression;

public class Add extends OperationBin {
    public Add(Term term1, Term term2) {
        super(term1, term2, "+", 3, 3, 3);
    }

    @Override
    protected int getValue(int x, int y) {
        return x + y;
    }

}