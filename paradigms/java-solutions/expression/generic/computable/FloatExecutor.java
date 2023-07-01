package expression.generic.computable;

public class FloatExecutor extends Executor<Float> {
    @Override
    public Float add(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float sub(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float mul(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float div(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float neg(Float x) {
        return -x;
    }

    @Override
    public Float parse(String x) throws Exception {
        return Float.parseFloat(x);
    }

    @Override
    public Float castArg(int x) {
        return (float) x;
    }
}
