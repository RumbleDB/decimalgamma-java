public interface DecimalDecomposition {
    boolean isZero();

    boolean isPositive();

    boolean isExponentNonNegative();

    int getAbsoluteExponent();

    int[] getDigits();
}
