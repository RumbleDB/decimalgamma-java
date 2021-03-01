public class LiteralDecomposition extends DecimalDec {

    boolean isZero;
    boolean isPositive;
    boolean isExponentNonNegative;
    int absoluteExponent;
    int[] digits;

    public LiteralDecomposition(boolean isZero, boolean isPositive, boolean isExponentNonNegative, int absoluteExponent, int[] digits) {
        this.isZero = isZero;
        this.isPositive = isPositive;
        this.isExponentNonNegative = isExponentNonNegative;
        this.absoluteExponent = absoluteExponent;
        this.digits = digits;
    }

    public LiteralDecomposition() {
    }

    @Override
    public boolean isZero() {
        return this.isZero;
    }

    @Override
    public boolean isPositive() {
        return this.isPositive;
    }

    @Override
    public boolean isExponentNonNegative() {
        return this.isExponentNonNegative;
    }

    @Override
    public int getAbsoluteExponent() {
        return this.absoluteExponent;
    }

    @Override
    public int[] getDigits() {
        return this.digits;
    }
}
