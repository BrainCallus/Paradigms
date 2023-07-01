package expression.generic.computable;

public class IntegerExecutor extends Executor<Integer> {
    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer mul(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer div(Integer x, Integer y) {
        if (y == 0) {
            return null;
        }
        return x / y;
    }

    @Override
    public Integer neg(Integer x) {
        return -x;
    }

    @Override
    public Integer parse(String x) throws Exception {
        return Integer.parseInt(x);
    }

    @Override
    public Integer castArg(int x) {
        return x;
    }
}
