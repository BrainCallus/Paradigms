package expression.exceptions;

public class InputException extends RuntimeException {
    public InputException(String message, int position) {
        super(message + ": position " + position);
    }
}
