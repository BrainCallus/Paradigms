package expression.generic.computable;

import java.math.BigInteger;

public class BigIntegerExecutor extends Executor<BigInteger> {
    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) {

        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) {
            return null;
        }
        return x.divide(y);
    }

    @Override
    public BigInteger neg(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger parse(String x) throws Exception {
        return new BigInteger(x);
    }

    @Override
    public BigInteger castArg(int x) {
        return BigInteger.valueOf(x);
    }

}
