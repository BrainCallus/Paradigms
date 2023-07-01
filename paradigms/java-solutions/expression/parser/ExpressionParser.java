package expression.parser;


import expression.*;
import expression.exceptions.TripleParser;

import java.util.List;

public class ExpressionParser extends CommonParser implements TripleParser {

    //set of operations
    private final char ADD = '+';
    private final char SUB = '-';
    private final char MUL = '*';
    private final char DIV = '/';
    private final char COUNT = 'c';
    private final char GCD = 'g';
    private final char LCM = 'l';

    private final List<Character> OPERATORS = List.of(ADD, SUB, MUL, DIV, COUNT, GCD, LCM);

    public ExpressionParser() {
        super();
    }

    public TripleExpression parse(String expression) {
        setSource(expression);

        return parseGcdLcm();
    }

    private boolean isOper() {
        return OPERATORS.contains(curSimb);
    }

    private int parseNumb() {
        int num = 0;
        while (isDigit()) {
            num = num * 10 + Integer.parseInt(String.valueOf(curSimb));
            if (hasNext())
                next();
            else break;
        }
        return num;
    }

    private TripleExpression parseAll() {
        TripleExpression expr;
        if (isDigit()) {
            expr = new Const(parseNumb());//next сделан в parseNumb
            return expr;
        } else if (isVar()) {
            expr = new Variable(String.valueOf(curSimb));
            next();
            return expr;
        } else if (curSimb == '(') {
            next();
            expr = parseGcdLcm();
            next();
            return expr;
        } else if (isOper()) {
            if (curSimb == SUB) {
                curSimb = source.charAt(getCurPos());
                setCurPos(getCurPos() + 1);
                if (isDigit()) {
                    expr = new Const(parseNumb() * -1);
                    return expr;
                } else {
                    if (Character.isWhitespace(curSimb)) {
                        next();
                    }
                    expr = new UnoMinus((Term) parseAll());
                    return expr;
                }
            } else if (curSimb == COUNT) {
                next();
                expect("ount");
                expr = new Count((Term) parseAll());
                return expr;

            }
        }
        return null;
    }

    private TripleExpression parseMulDiv() {
        TripleExpression expr = parseAll();
        while (hasNext()) {
            if (curSimb == MUL) {
                next();
                expr = new Multiply((Term) expr, (Term) parseAll());
            } else if (curSimb == DIV) {
                next();
                expr = new Divide((Term) expr, (Term) parseAll());
            } else {
                break;
            }
        }
        return expr;
    }

    private TripleExpression parseAddSub() {
        TripleExpression expr;
        expr = parseMulDiv();
        while (hasNext()) {
            if (curSimb == ADD) {
                next();
                expr = new Add((Term) expr, (Term) parseMulDiv());
            } else if (curSimb == SUB) {
                next();
                expr = new Subtract((Term) expr, (Term) parseMulDiv());
            } else {
                break;
            }
        }
        return expr;
    }

    private TripleExpression parseGcdLcm() {
        TripleExpression expr;
        expr = parseAddSub();
        while (hasNext()) {
            if (curSimb == GCD) {
                next();
                expect("cd");
                expr = new GCD((Term) expr, (Term) parseAddSub());
            } else if (curSimb == LCM) {
                next();
                expect("cm");
                expr = new LCM((Term) expr, (Term) parseAddSub());
            } else {
                break;
            }
        }
        return expr;
    }
}
