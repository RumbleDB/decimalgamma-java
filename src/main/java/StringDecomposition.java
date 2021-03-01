import java.util.ArrayList;

public class StringDecomposition extends DecimalDec {

    private boolean positive = true;
    private int exponent = -1;
    private final ArrayList<Integer> digits = new ArrayList<>();

    public StringDecomposition(String number) {
        boolean period = false;
        boolean digit = false;

        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);

            if (i == 0 && c == '-') {
                this.positive = false;
                continue;
            }

            if (!period && c == '.') {
                period = true;
                continue;
            }

            if (c == '0') {
                if (digit) {
                    digits.add(0);
                    if (!period) this.exponent++;
                } else if (period) {
                    this.exponent--;
                }

                continue;
            }

            if (c >= '1' && c <= '9') {
                this.digits.add(c - '0');
                if (!period) this.exponent++;
                digit = true;
                continue;
            }

            throw new RuntimeException("unexpected token");
        }

        int i = this.digits.size() - 1;
        while (i != -1 && this.digits.get(i) == 0) {
            this.digits.remove(i--);
        }
    }

    @Override
    public boolean isZero() {
        return this.digits.size() == 0;
    }

    @Override
    public boolean isPositive() {
        return this.positive;
    }

    @Override
    public boolean isExponentNonNegative() {
        if (this.isZero()) return true;

        return this.exponent >= 0;
    }

    @Override
    public int getAbsoluteExponent() {
        if (this.isZero()) return 0;
        return Math.abs(this.exponent);
    }

    @Override
    public int[] getDigits() {
        if (this.isZero()) return new int[]{0};

        return this.digits.stream().mapToInt(i -> i).toArray();
    }
}
