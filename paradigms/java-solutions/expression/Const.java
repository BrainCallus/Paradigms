package expression;

public class Const implements Term {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    //  @Override
    //  public int evaluate(int x) {
    //      return value;
    //  }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String toMiniString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            Const constant = (Const) obj;
            return this.getValue() == constant.getValue();
        }
        return false;
    }

    private int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
