package expression;

public abstract class OperationBin implements Term {
    private final Term term1;
    private final Term term2;
    private final String operator;
    private final int leftPrior;
    private final int rightPrior;
    private final int basePrior;

    public OperationBin(Term term1, Term term2, String operator, int leftPrior, int rightPrior, int basePrior) {
        this.term1 = term1;
        this.term2 = term2;
        this.operator = operator;
        this.leftPrior = leftPrior;
        this.rightPrior = rightPrior;
        this.basePrior = basePrior;
    }

    protected abstract int getValue(int x, int y);

    // @Override
    // public int evaluate(int x) {
    //     return getValue(term1.evaluate(x), term2.evaluate(x));
    // }

    @Override
    public int evaluate(int x, int y, int z) {
        return getValue(term1.evaluate(x, y, z), term2.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        StringBuilder sbuider = new StringBuilder("(");
        sbuider.append(term1.toString()).append(" ").append(operator).append(" ").append(term2.toString()).append(")");
        return sbuider.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sbuilder = new StringBuilder();
        if (term1 instanceof OperationBin && ((OperationBin) term1).basePrior < this.leftPrior) {
            sbuilder.append("(").append(term1.toMiniString()).append(")").append(" ").append(operator).append(" ");
        } else {
            sbuilder.append(term1.toMiniString()).append(" ").append(operator).append(" ");
        }
        if (term2 instanceof OperationBin && ((OperationBin) term2).basePrior < this.rightPrior
                && !(this instanceof GCD && term2 instanceof GCD
                || this instanceof LCM && term2 instanceof LCM)) {
            sbuilder.append("(").append(term2.toMiniString()).append(")");
        } else {
            sbuilder.append(term2.toMiniString());
        }
        return sbuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            OperationBin oper = (OperationBin) obj;
            return this.term1.equals(oper.term1) && this.term2.equals(oper.term2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (11 * operator.hashCode() + term1.hashCode()) * 41 + 73 * term2.hashCode();
    }
}
