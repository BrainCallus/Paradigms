package expression.generic;


import expression.exceptions.InputException;
import expression.generic.computable.*;
import expression.generic.generic_terms.*;
import expression.generic.parsers.ExpressionParserGen;


public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1,
                                 int y2, int z1, int z2) throws Exception {
        return tabulate(expression, getByMode(mode), x1, x2, y1, y2, z1, z2);
    }

    private <E> Object[][][] tabulate(String expression, Executor<E> exec,
                                      int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] tabulated = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionGen<E> expr = new ExpressionParserGen<>(exec).parse(expression);
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    tabulated[i][j][k] = compute(expr, exec, x1 + i, y1 + j, z1 + k);
                }
            }
        }
        return tabulated;
    }

    private <E> E compute(ExpressionGen<E> expr, Executor<E> exec, int i, int j, int k) throws Exception {
        return expr.evaluate(exec.castArg(i),
                exec.castArg(j), exec.castArg(k));
    }

    private Executor<?> getByMode(String mode) {
        return switch (mode) {
            case "i" -> new CheckedIntegerExecutor();
            case "d" -> new DoubleExecutor();
            case "bi" -> new BigIntegerExecutor();
            case "u" -> new IntegerExecutor();
            case "f" -> new FloatExecutor();
            case "s" -> new ShortExecutor();
            default -> throw new InputException("Can not resolve mode " + mode, 0);
        };
    }
}
