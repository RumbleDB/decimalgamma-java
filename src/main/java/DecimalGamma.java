import java.lang.reflect.Array;

public class DecimalGamma {

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

        // TODO: Mismatch paper / cpp implementation
        // invert digits / triplets individually or entirely?
        int[] digits = d.getDigits();
        int first = digits[0];
        if (!d.isPositive()) {
            first = 10 - first;
        }

        s.appendBits(first, 4);

        int next = 1;
        while (next < digits.length) {
            int a = digits[next++], b = 0, c = 0;
            boolean isLast = (next + 3 >= digits.length);
            if (next < digits.length) {
                b = digits[next++];
            }
            if (next < digits.length) {
                c = digits[next++];
            }

            int toWrite = 100 * a + 10 * b + c;
            if (!d.isPositive()) {
                if (isLast) {
                    toWrite = 1000 - toWrite;
                } else {
                    toWrite = 999 - toWrite;
                }
            }

            s.appendBits(toWrite, 10);
        }

        return s;
    }

    // TODO: Replace with log table
    private static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }
}
