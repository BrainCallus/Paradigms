package expression;

public class UnoMinus extends OperationUno {
    public UnoMinus(Term term) {
        super(term, "-");
    }

    @Override
    protected int getValue(int x) {
        return x * -1;
    }


}
