package expression.generic.parsers;

import expression.exceptions.*;
import expression.generic.computable.Executor;
import expression.generic.generic_terms.*;

import java.util.List;

public class ExpressionParserGen<E> extends CommonParserGen implements ExpressionParser<E> {

    //set of operations
    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MUL = '*';
    private static final char DIV = '/';
    private static final char COUNT = 'c';
    private static final char GCD = 'g';
    private static final char LCM = 'l';
    private static final List<Character> OPERATORS = List.of(ADD, SUB, MUL, DIV, COUNT, GCD, LCM);
    private int balance = 0;
    private final Executor<E> executor;


    public ExpressionParserGen(Executor<E> executor) {
        this.executor = executor;
    }

    public ExpressionGen<E> parse(String expression) throws Exception {
        checkInput(expression);
        setSource(expression + "\0");
        balance = 0;
        ExpressionGen<E> expr = parseAddSub();
        if (curSimb != EOF) {
            throw new InputException("illegal symbol '" + curSimb + "' ending expression", getCurPos());
        }
        return expr;
    }

    private void checkInput(String s) {
        if (s == null || s.isEmpty() || s.isBlank()) {
            throw new InputException("expression can't be empty", 0);
        }
    }

    private boolean isOper() {
        return OPERATORS.contains(curSimb);
    }

    private int readNumb() {
        int c = getCurPos() - 1;
        while (isDigit() || source.charAt(getCurPos() - 1) == '.') {
            c++;
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
        return c;
    }

    private void checkSymbol() {
        if (curSimb == ')' && balance <= 0) {
            throw new InputException("wrong bracket sequence", getCurPos());
        }
        if (!isDigit() && !isVar() && !isOper() && !(curSimb == '(' || curSimb == ')' || curSimb == EOF)) {
            throw new InputException("illegal symbol '" + curSimb + "'", getCurPos());
        }
    }


    private ExpressionGen<E> parseAll() throws Exception {
        ExpressionGen<E> expr;
        checkSymbol();
        if (isDigit()) {
            expr = new ConstGen<>(executor.parse(source.substring(getCurPos() - 1, readNumb())));
            return expr;
        } else if (isVar()) {
            expr = new VariableGen<>(String.valueOf(curSimb));
            next();
            return expr;
        } else if (curSimb == '(') {
            balance++;
            next();
            expr = parseAddSub();
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
                    expr = new ConstGen<>(executor.parse(source.substring(getCurPos() - 2, readNumb())));
                } else {
                    if (Character.isWhitespace(curSimb)) {
                        next();
                    }
                    expr = new OperationGen<>(parseAll(), TermTypes.NEG, executor);
                }
                return expr;
            } else {
                throw new InputException("illegal operator '" + curSimb + "'", getCurPos());
            }
        } else {
            throw new InputException("symbol '" + curSimb + "' can't be expression part", getCurPos());
        }
    }

    private ExpressionGen<E> parseMulDiv() throws Exception {
        ExpressionGen<E> expr = parseAll();
        checkSymbol();
        while (hasNext()) {
            if (curSimb == MUL) {
                next();
                expr = new OperationGen<>(expr, parseMulDiv(), TermTypes.MUL, executor);
            } else if (curSimb == DIV) {
                next();
                expr = new OperationGen<>(expr, parseMulDiv(), TermTypes.DIV, executor);
            } else {
                break;
            }
        }
        return expr;
    }

    private ExpressionGen<E> parseAddSub() throws Exception {
        ExpressionGen<E> expr;
        expr = parseMulDiv();
        checkSymbol();
        while (hasNext()) {
            if (curSimb == ADD) {
                next();
                expr = new OperationGen<>(expr, parseMulDiv(), TermTypes.ADD, executor);
            } else if (curSimb == SUB) {
                next();
                expr = new OperationGen<>(expr, parseMulDiv(), TermTypes.SUB, executor);
            } else {
                break;
            }
        }
        return expr;
    }
    /* saved in case string-named operations occur
    private void checkOperatorSpell(String tale) {
    //     getOne();
    //     expect(tale);
    //     if (Character.isWhitespace(curSimb)) {
    //         next();
    //     }
    }

     */
}
