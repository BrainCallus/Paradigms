package expression.exceptions;

import expression.Term;

public abstract class CheckedOperationUno implements Term {
    private final Term term;
    private final String operator;

    public CheckedOperationUno(Term term, String operator) {
        this.term = term;
        this.operator = operator;
    }

    protected abstract int getValue(int x);

    //  @Override
    //  public int evaluate(int x) {
    //      return getValue(term.evaluate(x));
    //  }

    @Override
    public int evaluate(int x, int y, int z) {
        return getValue(term.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        StringBuilder sbuider = new StringBuilder(operator);
        sbuider.append("(").append(term.toString()).append(")");
        return sbuider.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sbuilder = new StringBuilder(operator);
        if (!(term instanceof CheckedOperationBin)) {
            sbuilder.append(" ").append(term.toMiniString());
        } else sbuilder.append("(").append(term.toMiniString()).append(")");
        return sbuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            CheckedOperationUno uno = (CheckedOperationUno) obj;
            return this.term.equals(uno.term);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return term.hashCode() * 19 + operator.hashCode();
    }
}
