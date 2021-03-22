import java.text.ParseException;
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

    public static DecimalDecomposition Decode(String bits) throws ParseException {
        return DecimalGamma.Decode(new BitSequence(bits));
    }

    public static DecimalDecomposition Decode(int bits, int n) throws ParseException {
        return DecimalGamma.Decode(new BitSequence(bits, n));
    }

    public static DecimalDecomposition Decode(BitSequence b) throws ParseException {
        LiteralDecomposition d = new LiteralDecomposition();

        int length = b.length();

        if (length < 2) throw new ParseException("expected at least 2 bits", 0);

        if (b.getBits(0, 2) == 0b10 && length == 2) {
            d.isZero = true;

            return d;
        }

        if (b.getBits(0, 2) == 0b00) d.isPositive = false;
        else if (b.getBits(0, 2) == 0b10) d.isPositive = true;
        else throw new ParseException("invalid sign bits: must be either 00 or 10", 0);

        d.isExponentNonNegative = d.isPositive == (b.getBits(2, 1) == 0b1);

        int exponentLength = 1;
        while (true) {
            if (exponentLength + 2 >= length)
                throw new ParseException("exponent length encoding not delimited", 2);

            if (b.getBits(exponentLength + 1, 1) != b.getBits(exponentLength + 2, 1))
                break;

            exponentLength++;
        }

        boolean inverse = b.getBits(2, 1) == 0b0;

        if (3 + 2 * exponentLength > length)
            throw new ParseException("not enough bits for exponent encoding", 3);

        d.absoluteExponent = 1; // first digit is always 1
        for (int i = 3 + exponentLength; i < 3 + 2 * exponentLength; i++) {
            d.absoluteExponent *= 2;
            d.absoluteExponent += inverse ? 1 - b.getBits(i, 1) : b.getBits(i, 1);
        }

        d.absoluteExponent -= 2;

        if (d.absoluteExponent < 0)
            throw new ParseException("absolute exponent cannot be negative", 2);

        if (d.absoluteExponent == 0 && !d.isExponentNonNegative)
            throw new ParseException("if absolute exponent is 0, the exponent sign must be positive", 2);

        ArrayList<Integer> digits = new ArrayList<>();
        int nextOffset = 2 + 2 * exponentLength + 1;

        // First digit
        if (nextOffset + 4 > length)
            throw new ParseException("significand must be at least 4 bits", nextOffset);
        int num = b.getBits(nextOffset, 4);
        if (num < 0 || num > 9)
            throw new ParseException("first digit of significant must be between 0 and 9 (inclusive)", nextOffset);

        digits.add(num);
        nextOffset += 4;

        // Remaining triplets
        while (nextOffset < length) {
            if (nextOffset + 10 > length)
                throw new ParseException("each non-initial digit-triplet of significand must be 10 bits", nextOffset);

            num = b.getBits(nextOffset, 10);
            if (num < 0 || num > 999)
                throw new ParseException("significant triplet must be between 0 and 999 (inclusive)", nextOffset);

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

    // TODO: Replace with log table
    private static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    private static int[] invert(int[] num) {
        int[] out = new int[num.length];
        for (int i = num.length - 1; i >= 0; i--) {
            out[i] = 10 - num[i];
            if (i < num.length - 1) out[i]--;
        }

        return out;
    }
}

