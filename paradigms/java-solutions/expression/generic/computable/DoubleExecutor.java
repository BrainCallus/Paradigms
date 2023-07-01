package expression.generic.computable;

public class DoubleExecutor extends Executor<Double> {
    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double neg(Double x) {
        return -x;
    }

    @Override
    public Double parse(String x) {
        return Double.parseDouble(x);
    }

    @Override
    public Double castArg(int x) {
        return (double) x;
    }

}
