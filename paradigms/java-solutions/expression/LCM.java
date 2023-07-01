package expression;

public class LCM extends OperationBin {
    public LCM(Term term1, Term term2) {
        super(term1, term2, "lcm", 1, 2, 1);
    }

    @Override
    protected int getValue(int x, int y) {
        if (gcd(x, y) != 0)
            return x / gcd(x, y) * y;
        else return 0;
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