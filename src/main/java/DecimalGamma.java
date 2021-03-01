import java.util.ArrayList;

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

    public static DecimalDecomposition Decode(String bits) {
        return DecimalGamma.Decode(new BitSequence(bits));
    }

    public static DecimalDecomposition Decode(int bits, int n) {
        return DecimalGamma.Decode(new BitSequence(bits, n));
    }

    // TODO: Fix exceptions (custom class, checked)
    // TODO Lots of unsafe stuff
    public static DecimalDecomposition Decode(BitSequence b) {
        LiteralDecomposition d = new LiteralDecomposition();

        Boolean[] bytes = b.rawBites();

        if (bytes.length < 2) throw new RuntimeException("too short");

        if (b.getBytes(0, 2) == 0b10 && bytes.length == 2) {
            d.isZero = true;

            return d;
        }

        if (!bytes[0] && !bytes[1]) {
            d.isPositive = false;
        } else if (bytes[0] && !bytes[1]) {
            d.isPositive = true;
        } else {
            throw new RuntimeException("again something broken");
        }

        d.isExponentNonNegative = d.isPositive == bytes[2];

        int exponentLength = 1;
        while (bytes[exponentLength + 1] == bytes[exponentLength + 2]) exponentLength++;

        if (!bytes[2]) {
            for (int i = 2; i < 2 + 2 * exponentLength + 1; i++) {
                bytes[i] = !bytes[i];
            }
        }

        d.absoluteExponent = 1;
        for (int i = 1; i < exponentLength + 1; i++) {
            int offset = 2 + exponentLength + i;

            d.absoluteExponent = d.absoluteExponent * 2 + toInt(bytes[offset]);
        }

        d.absoluteExponent -= 2;

        if (d.absoluteExponent < 0 || d.absoluteExponent == 0 && !d.isExponentNonNegative)
            throw new RuntimeException("again");

        ArrayList<Integer> digits = new ArrayList<>();
        int nextOffset = 2 + 2 * exponentLength + 1;

        // First digit
        digits.add(toInt(bytes[nextOffset]) * 8
                + toInt(bytes[nextOffset + 1]) * 4
                + toInt(bytes[nextOffset + 2]) * 2
                + toInt(bytes[nextOffset + 3]));

        nextOffset += 4;

        // Remaining triplets
        while (nextOffset < bytes.length) {
            int num = b.getBytes(nextOffset, 10);

            if (num < 0 || num > 999) throw new RuntimeException("ahhh");

            digits.add(num / 100);
            digits.add((num / 10) % 10);
            digits.add(num % 10);

            nextOffset += 10;
        }

        for (int i = digits.size() - 1; i > 0; i--) {
            if (digits.get(i) != 0) break;

            digits.remove(i);
        }

        d.digits = digits.stream().mapToInt(i -> i).toArray();
        if (!d.isPositive) d.digits = invert(d.digits);

        return d;
    }

    private static int toInt(boolean x) {
        return x ? 1 : 0;
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

