package expression;

import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {

        // Subtract sub = new Subtract(
        //         new Multiply(
        //                 new Const(2),
        //                 new Variable("x")
        //         ),
        //         new Const(3)
        // );
        // System.out.println(sub.evaluate(5));
//
        // System.out.println(
        //         new Add(
        //                 new Subtract(
        //                         new Multiply(
        //                                 new Variable("x"),
        //                                 new Variable("x")
        //                         ),
        //                         new Multiply(
        //                                 new Const(2),
        //                                 new Variable("x")
        //                         )
        //                 ),
        //                 new Const(1)
        //         ).toMiniString()
        // );
        // Term expr1 = new Subtract(new Divide(new Const(2), new Variable("x")), new Subtract(new Variable("z"), new Const(5)));
        // System.out.println(expr1.toMiniString());
//
        // ExpressionParser parser = new ExpressionParser();
        // Term parsed = (Term) parser.parse("- 11");
        // System.out.println(parsed + " " + parsed.evaluate(3, 2, 3));
        // Term uno = new UnoMinus(new Divide(new Const(10), new Variable("x")));
        // System.out.println(uno.toString() + "  " + uno.evaluate(3));
        // Term insideUNO = new Add(new Variable("x"), new UnoMinus(new Const(0)));
        // System.out.println(insideUNO.toString() + "  " + insideUNO.evaluate(1, 100, 10));
        // parsed = (Term) parser.parse("-(0)");
        // System.out.println(parsed + " " + parsed.getClass());
        // System.out.println(parser.parse("-((-(x) / (y + z)))"));
        // System.out.println(parser.parse("x* (y-(z-2)*(3+x))"));
        // Term count = new Count(new Const(11));
        // System.out.println(count.toString() + " " + count.evaluate(3, 4, 5));
        // count = (Term) parser.parse("count(x-count(x+1)");
        // System.out.println(count.toString() + "   " + count.evaluate(3));
        // Term gcd = new GCD(new Variable("x"), new Const(-6));
        // System.out.println(gcd + "  " + gcd.evaluate(-2));
        // gcd = new LCM(new Variable("x"), new Const(-6));
        // System.out.println(gcd + "  " + gcd.evaluate(-2));
//
        // System.out.println(Math.abs(-2147483648) + " " + -2147483648 + " " + 2147483647);
        // System.out.println(Integer.MIN_VALUE);
        // gcd = new GCD(new Const(30), new Const(-2147483648));
        // System.out.println(gcd.evaluate(6666666, 23093, 193582));
    }
}