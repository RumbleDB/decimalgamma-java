public class DecimalGamma {

    public static BitSequence Encode(String d) {
        return DecimalGamma.Encode(new StringDecomposition(d));
    }

    // Largely ported from the C++ implementation found on
    // https://github.com/ghislainfourny/decimalgamma-cpp
    public static BitSequence Encode(DecimalDecomposition d) {
        BitSequence s = new BitSequence();

        if (d.isPositive() || d.isZero()) {
            s.appendBits(0b10, 2);
        } else {
            s.appendBits(0b00, 2);
        }

        if (d.isZero()) return s;

        int abs_offset_exponent = d.getAbsoluteExponent() + 2;
        boolean invert_exponent_encoding = (d.isPositive() != d.isExponentNonNegative());
        int le = log2(abs_offset_exponent) + 1;
        int xor_ones = (1 << le) - 1;

        if (invert_exponent_encoding) {
            s.appendBits(((1 << le) - 2) ^ xor_ones, le);
            xor_ones = (1 << (le - 1)) - 1;
            s.appendBits((abs_offset_exponent - (1 << (le - 1))) ^ xor_ones, le - 1);
        } else {
            s.appendBits((1 << le) - 2, le);
            s.appendBits(abs_offset_exponent - (1 << (le - 1)), le - 1);
        }

        int[] digits = d.getDigits();
        if (!d.isPositive()) {
            digits = invert(digits);
        }

        int first = digits[0];
        s.appendBits(first, 4);

        int next = 1;
        while (next < digits.length) {
            int a = digits[next++], b = 0, c = 0;
            if (next < digits.length) {
                b = digits[next++];
            }
            if (next < digits.length) {
                c = digits[next++];
            }

            int toWrite = 100 * a + 10 * b + c;

            s.appendBits(toWrite, 10);
        }

        return s;
    }

    // TODO: Replace with log table
    private static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    private static int[] invert(int[] num) {
        int[] out = new int[num.length];
        boolean carry = false;
        for (int i = num.length - 1; i >= 0; i--) {
            out[i] = 10 - num[i];
            if (carry) out[i]--;

            if (out[i] == 10) {
                out[i] = 0;
                carry = false;
            } else {
                carry = true;
            }
        }
        return out;
    }
}
