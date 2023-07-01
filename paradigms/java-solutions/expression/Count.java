package expression;

public class Count extends OperationUno {
    public Count(Term term) {
        super(term, "count");
    }

    @Override
    protected int getValue(int x) {
        int cnt = 0;
        for (int i = 0; i < 32; i++) {
            int bit = x;
            bit = bit << 31 - i;
            bit = bit >> 31;
            if (bit != 0) cnt++;
        }
        return cnt;
    }
}
