import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringDecompositionTest {

    @Test
    void testStringDecomposition() {
        validateComposition("0.05", false, true, false, 2, new int[]{5});
        validateComposition("-1.01", false, false, true, 0, new int[]{1, 0, 1});
        validateComposition("-123456789", false, false, true, 8, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        validateComposition("-1000", false, false, true, 3, new int[]{1});
        validateComposition("-1", false, false, true, 0, new int[]{1});
        validateComposition("0", true, true, true, 0, new int[]{0});
        validateComposition("00.00", true, true, true, 0, new int[]{0});
        validateComposition("0.1", false, true, false, 1, new int[]{1});
        validateComposition("-0.1", false, false, false, 1, new int[]{1});
        validateComposition("1123.12345234", false, true, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4});
        validateComposition("-1123.12345234", false, false, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4});
        validateComposition("-01123.123452340", false, false, true, 3, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4});
        validateComposition("-0.0000000112312345234", false, false, false, 8, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4});
        validateComposition("0.0000000112312345234", false, true, false, 8, new int[]{1, 1, 2, 3, 1, 2, 3, 4, 5, 2, 3, 4});
    }

    void validateComposition(String input, boolean isZero, boolean isPositive, boolean isExponentNonNegative, int absoluteExponent, int[] digits) {
        DecimalDecomposition output = new StringDecomposition(input);
        assertEquals(isZero, output.isZero(), input);
        assertEquals(isPositive, output.isPositive(), input);
        assertEquals(isExponentNonNegative, output.isExponentNonNegative(), input);
        assertEquals(absoluteExponent, output.getAbsoluteExponent(), input);
        assertArrayEquals(digits, output.getDigits(), input);
    }
}