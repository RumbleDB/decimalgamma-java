public abstract class DecimalDec implements DecimalDecomposition {
    @Override
    public String toString() {
        if (this.isZero()) return "0";

        StringBuilder b = new StringBuilder();

        if (!this.isPositive()) b.append("-");

        int absoluteExponent = this.getAbsoluteExponent();

        if (!this.isExponentNonNegative()) {
            b.append("0.");
            absoluteExponent--;

            while (absoluteExponent > 0) {
                b.append(0);
                absoluteExponent--;
            }
        }

        int[] digits = this.getDigits();
        boolean printedDot = false;
        for (int i = 0; i < digits.length; i++) {
            b.append(digits[i]);

            if (!this.isExponentNonNegative() || i == digits.length - 1 || printedDot) continue;

            if (absoluteExponent == 0) {
                b.append('.');
                printedDot = true;
            } else {
                absoluteExponent--;
            }
        }

        while (absoluteExponent > 0) {
            b.append(0);
            absoluteExponent--;
        }

        return b.toString();
    }
}
