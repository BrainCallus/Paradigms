package expression.exceptions;

public class CommonParser {
    protected final char EOF = '\0';
    protected String source;
    private int curPos;
    protected char curSimb;
    private int length;

    public CommonParser() {
    }

    protected void setSource(String source) {
        this.source = source;
        curPos = 0;
        length = source.length();
        next();
    }

    protected int getCurPos() {
        return curPos;
    }

    protected boolean hasNext() {
        return curPos < length;
    }

    protected void next() {
        if (hasNext()) {
            curSimb = source.charAt(curPos++);
            cutWhitespaces();
        }
    }

    protected void getOne() {
        if (hasNext()) {
            curSimb = source.charAt(curPos++);
        }
    }

    protected boolean isDigit() {
        return '0' <= curSimb && curSimb <= '9';
    }

    protected boolean isVar() {
        return curSimb == 'x' || curSimb == 'y' || curSimb == 'z';
    }

    protected boolean isLetter() {
        return 'a' <= curSimb && curSimb <= 'z' || 'A' <= curSimb && curSimb <= 'Z';
    }

    protected void cutWhitespaces() {
        if (Character.isWhitespace(curSimb)) {
            next();
        }
    }

    protected boolean expect(final char expected) {
        return curSimb == expected;
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            if (!expect(c)) {
                throw new InputException("expected " + c + " found " + curSimb, curPos);
            }
            getOne();
        }
    }
}
