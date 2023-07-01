package expression;

public class GCD extends OperationBin {
    public GCD(Term term1, Term term2) {
        super(term1, term2, "gcd", 1, 2, 1);
    }

    @Override
    protected int getValue(int x, int y) {
        return gcd(x, y);
    }

    private int gcd(int x, int y) {
        if (x < y) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        while (y != 0) {
            x %= y;
            int tmp = x;
            x = y;
            y = tmp;
        }
        if (x < 0) x *= -1;
        return x;
    }
}