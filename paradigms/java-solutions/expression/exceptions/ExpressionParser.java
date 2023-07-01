package expression.exceptions;


import java.util.List;

import expression.Term;
import expression.Const;
import expression.Variable;
import expression.TripleExpression;

public class ExpressionParser extends CommonParser implements TripleParser {

    //set of operations
    private final char ADD = '+';
    private final char SUB = '-';
    private final char MUL = '*';
    private final char DIV = '/';
    private final char COUNT = 'c';
    private final char GCD = 'g';
    private final char LCM = 'l';
    private final char SET = 's';
    private int balance = 0;

    private final List<Character> OPERATORS = List.of(ADD, SUB, MUL, DIV, COUNT, GCD, LCM, SET);

    public ExpressionParser() {
        super();
    }

    public TripleExpression parse(String expression) {
        if (expression == null || expression.isEmpty() || expression.isBlank()) {
            throw new InputException("expression can't be empty", 0);
        }
        setSource(expression + "\0");
        balance = 0;
        TripleExpression expr = parseGcdLcm();
        if (curSimb != EOF) {
            throw new InputException("illegal symbol '" + curSimb + "' ending expression", getCurPos());
        }
        return expr;
    }

    private boolean isOper() {
        return OPERATORS.contains(curSimb);
    }

    private int parseNumb(boolean minus) {
        int num = 0;
        while (isDigit()) {
            int minorDigit = Integer.parseInt(String.valueOf(curSimb));
            if (Integer.MAX_VALUE - num * 10 - minorDigit < -1 && minus) {
                throw new InputException("incorrect constant: too small value", getCurPos());
            } else if (Integer.MAX_VALUE - num * 10 - minorDigit < 0 && !minus) {
                throw new InputException("incorrect constant: too large value", getCurPos());
            }
            num = num * 10 + minorDigit;
            if (hasNext()) {
                getOne();
                if (isLetter()) {
                    throw new InputException("unexpected symbol '" + curSimb + "'", getCurPos());
                }
            } else {
                break;
            }
        }
        if (Character.isWhitespace(curSimb)) {
            next();
        }
        return num;
    }

    private void checkSymbol() {
        if (curSimb == ')' && balance <= 0) {
            throw new InputException("wrong bracket sequence", getCurPos());
        }
        if (!isDigit() && !isVar() && !isOper() && !(curSimb == '(' || curSimb == ')' || curSimb == EOF)) {
            throw new InputException("illegal symbol '" + curSimb + "'", getCurPos());
        }
    }

    private void checkOperatorSpell(String tale) {
        getOne();
        expect(tale);
        if (Character.isWhitespace(curSimb)) {
            next();
        }
    }

    private TripleExpression parseAll() {
        TripleExpression expr;
        checkSymbol();
        if (isDigit()) {
            expr = new Const(parseNumb(false));
            return expr;
        } else if (isVar()) {
            expr = new Variable(String.valueOf(curSimb));
            next();
            return expr;
        } else if (curSimb == '(') {
            balance++;
            next();
            expr = parseGcdLcm();
            if (curSimb != ')') {
                throw new InputException("missed closing bracket", getCurPos());
            }
            balance--;
            next();
            return expr;
        } else if (isOper()) {
            if (curSimb == SUB) {
                getOne();
                if (isDigit()) {
                    expr = new Const(parseNumb(true) * -1);
                } else {
                    if (Character.isWhitespace(curSimb)) {
                        next();
                    }
                    expr = new CheckedNegate((Term) parseAll());
                }
                return expr;
            } else if (curSimb == COUNT) {
                checkOperatorSpell("ount");
                if (!Character.isWhitespace(source.charAt(getCurPos() - 2)) && curSimb != '(') {
                    throw new InputException("expected 'count' found 'count" + curSimb + "'", getCurPos());
                }
                expr = new CheckedCount((Term) parseAll());
                return expr;

            } else {
                throw new InputException("illegal operator '" + curSimb + "'", getCurPos());
            }
        } else {
            throw new InputException("symbol '" + curSimb + "' can't be expression part", getCurPos());
        }
    }

    private TripleExpression parseMulDiv() {
        TripleExpression expr = parseAll();
        checkSymbol();
        while (hasNext()) {
            if (curSimb == MUL) {
                next();
                expr = new CheckedMultiply((Term) expr, (Term) parseAll());
            } else if (curSimb == DIV) {
                next();
                expr = new CheckedDivide((Term) expr, (Term) parseAll());
            } else {
                break;
            }
        }
        return expr;
    }

    private TripleExpression parseAddSub() {
        TripleExpression expr;
        expr = parseMulDiv();
        checkSymbol();
        while (hasNext()) {
            if (curSimb == ADD) {
                next();
                expr = new CheckedAdd((Term) expr, (Term) parseMulDiv());
            } else if (curSimb == SUB) {
                next();
                expr = new CheckedSubtract((Term) expr, (Term) parseMulDiv());
            } else {
                break;
            }
        }
        return expr;
    }

    private TripleExpression parseGcdLcm() {
        TripleExpression expr;
        expr = parseSetClear();
        checkSymbol();
        while (hasNext()) {
            if (curSimb == GCD) {
                checkOperatorSpell("cd");
                expr = new CheckedGCD((Term) expr, (Term) parseSetClear());
            } else if (curSimb == LCM) {
                checkOperatorSpell("cm");
                expr = new CheckedLCM((Term) expr, (Term) parseSetClear());
            } else {
                break;
            }
        }
        return expr;
    }

    private TripleExpression parseSetClear() {
        TripleExpression expr;
        expr = parseAddSub();
        checkSymbol();
        while (hasNext()) {
            if(curSimb==COUNT){
                checkOperatorSpell("lear");
                expr = new CheckedClear((Term) expr, (Term) parseAddSub());
            }
            else if(curSimb==SET){
                checkOperatorSpell("et");
                expr = new CheckedSet((Term) expr,(Term) parseAddSub());
            }else{
                break;
            }
        }
        return expr;
    }
}
