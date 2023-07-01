package expression.generic.computable;

public class CheckedIntegerExecutor extends IntegerExecutor {
    @Override
    public Integer add(Integer x, Integer y) {
        return checkedBinaryOperation(x, y,
                (x > 0 && y > 0 && x + y <= 0) || (x < 0 && y < 0 && x + y >= 0),
                super::add);
    }

    @Override
    public Integer sub(Integer x, Integer y) {
        return checkedBinaryOperation(x, y, (y < 0 && x > x - y) || (y > 0 && x < x - y),
                super::sub);
    }


    @Override
    public Integer mul(Integer x, Integer y) {
        return checkedBinaryOperation(x, y,
                (x != 0 && x * y / x != y) || (y != 0 && x * y / y != x),
                super::mul);
    }

    @Override
    public Integer div(Integer x, Integer y) {
        return checkedBinaryOperation(x, y, x < 0 && y < 0 && x / y < 0, super::div);
    }

    @Override
    public Integer neg(Integer x) {
        return checkedUnaryOperation(x, x < 0 && -1 * x < 0, super::neg);
    }


}
