package expression.generic.computable;

public class ShortExecutor extends Executor<Short> {
    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short sub(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short mul(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short div(Short x, Short y) {
        if (y == 0) {
            return null;
        }
        return (short) (x / y);
    }

    @Override
    public Short neg(Short x) {
        return (short) (-x);
    }

    @Override
    public Short parse(String x) throws Exception {
        return Short.parseShort(x);
    }

    @Override
    public Short castArg(int x) {
        return (short) x;
    }
}
