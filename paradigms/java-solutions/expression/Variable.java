package expression;

public class Variable implements Term {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    //@Override
    //public int evaluate(int x) {
    //     return x;
    // }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case ("x") -> x;
            case ("y") -> y;
            case ("z") -> z;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            Variable var = (Variable) obj;
            return this.toString().equals(var.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
