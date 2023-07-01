package expression.parser;

public class CommonParser {
    protected String source;
    private int curPos;
    protected char curSimb;
    private int length;

    public CommonParser() {
    }

    public void setSource(String source) {
        this.source = source;
        curPos = 0;
        length = source.length();
        next();
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
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

    protected boolean isDigit() {
        return '0' <= curSimb && curSimb <= '9';
    }

    protected boolean isVar() {
        return curSimb == 'x' || curSimb == 'y' || curSimb == 'z';
    }

    protected void cutWhitespaces() {
        if (Character.isWhitespace(curSimb)) {
            next();
        }
    }

    protected boolean expect(final char expected) {
        if (curSimb != expected) {
            return false;
        }
        return true;
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            if (!expect(c)) {
                //throw new InputException("expected "+c+" found " +curSimb); //реализовано в дз 13
            }
            next();
        }
    }
}
