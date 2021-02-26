import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringDecompositionTestcase {
    protected String input;
    protected boolean isZero;
    protected boolean isPositive;
    protected boolean isExponentNonNegative;
    protected int absoluteExponent;
    protected int[] digits;

    public StringDecompositionTestcase(String input, boolean isZero, boolean isPositive, boolean isExponentNonNegative, int absoluteExponent, int[] digits) {
        this.input = input;
        this.isZero = isZero;
        this.isPositive = isPositive;
        this.isExponentNonNegative = isExponentNonNegative;
        this.absoluteExponent = absoluteExponent;
        this.digits = digits;
    }
}

class StringDecompositionTest {

    @Test
    void testStringDecomposition() {
        StringDecompositionTestcase[] testcases = new StringDecompositionTestcase[]{
                new StringDecompositionTestcase("0.05", false, true, false, 2, new int[]{5}),
                new StringDecompositionTestcase("-1.01", false, false, true, 0, new int[]{1, 0, 1}),
                new StringDecompositionTestcase("-123456789", false, false, true, 8, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}),
                new StringDecompositionTestcase("-1000", false, false, true, 3, new int[]{1}),
                new StringDecompositionTestcase("-1", false, false, true, 0, new int[]{1}),
                new StringDecompositionTestcase("0", true, true, true, 0, new int[]{0}),
                new StringDecompositionTestcase("00.00", true, true, true, 0, new int[]{0}),
                new StringDecompositionTestcase("0.1", false, true, false, 1, new int[]{1}),
                new StringDecompositionTestcase("-0.1", false, false, false, 1, new int[]{1}),
                new StringDecompositionTestcase("1123.12345234", false, true, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4}),
                new StringDecompositionTestcase("-1123.12345234", false, false, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4}),
                new StringDecompositionTestcase("-01123.123452340", false, false, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4}),
                new StringDecompositionTestcase("-0.0000000112312345234", false, false, false, 8, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4}),
                new StringDecompositionTestcase("0.0000000112312345234", false, true, false, 8, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4}),
        };

        for (StringDecompositionTestcase test : testcases) {
            DecimalDecomposition output = new StringDecomposition(test.input);
            assertEquals(test.isZero, output.isZero(), test.input);
            assertEquals(test.isPositive, output.isPositive(), test.input);
            assertEquals(test.isExponentNonNegative, output.isExponentNonNegative(), test.input);
            assertEquals(test.absoluteExponent, output.getAbsoluteExponent(), test.input);
            assertArrayEquals(test.digits, output.getDigits(), test.input);
        }
    }
}