package expression.exceptions;

import expression.OperationBin;
import expression.Term;

public class CheckedLCM extends CheckedOperationBin {
    public CheckedLCM(Term term1, Term term2) {
        super(term1, term2, "lcm", 1, 2, 1);
    }

    @Override
    protected int getValue(int x, int y) {
        if (gcd(x, y) != 0) {
            int lcm = x / gcd(x, y) * y;
            if ((x < 0 && y < 0 && lcm < 0 && x != y) || (x >= 0 && y >= 0 && lcm < 0)
                    || (x < 0 && y > 0 && lcm > 0) || (x > 0 && y < 0 && lcm > 0) ||
                    (x != 0 && lcm % x != 0 || y != 0 && lcm % y != 0)) {
                throw new OverflowException("overflow");
            }
            return lcm;
        } else return 0;
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