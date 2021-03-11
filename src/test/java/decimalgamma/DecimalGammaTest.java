package decimalgamma;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DecimalGammaTest {

    static int seed = 0x123;
    static int fuzzyIterations = 100000;
    static int neighborIterations = 100000;


    @Test
    void testIntegerNeighboursOrdered() {
        for (int i = -neighborIterations; i < neighborIterations; i++) {
            String a = String.valueOf(i);
            String b = String.valueOf(i + 1);

            BitSequence encodedA = DecimalGamma.Encode(a);
            BitSequence encodedB = DecimalGamma.Encode(b);

            assertTrue(encodedA.toString().compareTo(encodedB.toString()) < 0, a + " " + b);
        }
    }

    @Test
    void testFuzzyOrdered() {
        Random r = new Random(seed);

        for (int i = 0; i < fuzzyIterations; i++) {
            double a = nextRandom(r);
            double b = nextRandom(r);

            BitSequence encodedA = DecimalGamma.Encode(a);
            BitSequence encodedB = DecimalGamma.Encode(b);

            assertEquals(Double.compare(a, b), compare(encodedA.toString(), encodedB.toString()), a + " " + b);
        }
    }

    @Test
    void testIntegerInverse() throws ParseException {
        for (int i = -neighborIterations; i < neighborIterations; i++) {
            validateInverse(String.valueOf(i));
        }
    }

    @Test
    void testFuzzyInverse() throws ParseException {
        Random r = new Random(seed);

        for (int i = 0; i < fuzzyIterations; i++) {
            double a = nextRandom(r);
            validateInverse(new BigDecimal(a).toPlainString());
        }
    }

    @Test
    void testEncode() throws ParseException {
        validateEncoding("00 001 11 1000 1111001000", "-103.2");
        validateEncoding("00 110 00 0101 1110110110", "-0.0405");
        validateEncoding("10 01 0 0111 0001000111 0000111100", "0.707106");
        validateEncoding("10 1110 011 0100 0000000101 0000001100 0101011001", "4005012345");

        // Tests from decimalgamma-cpp
        // Only the first test-output differs due to the difference in encoding
        validateEncoding("0000011011000101111110101101100000001101110", "-123456789");
        validateEncoding("00001101001", "-1000");
        validateEncoding("00001111001", "-100");
        validateEncoding("000101001", "-10");
        validateEncoding("000110001", "-9");
        validateEncoding("000110010", "-8");
        validateEncoding("000110011", "-7");
        validateEncoding("000110100", "-6");
        validateEncoding("000110101", "-5");
        validateEncoding("000110110", "-4");
        validateEncoding("000110111", "-3");
        validateEncoding("000111000", "-2");
        validateEncoding("000111001", "-1");
        validateEncoding("10", "0");
        validateEncoding("10001110010", "0.02");
        validateEncoding("100100010", "0.2");
        validateEncoding("101000001", "1");
        validateEncoding("101000010", "2");
        validateEncoding("101000011", "3");
        validateEncoding("101000100", "4");
        validateEncoding("101000101", "5");
        validateEncoding("101000110", "6");
        validateEncoding("101000111", "7");
        validateEncoding("101001000", "8");
        validateEncoding("101001001", "9");
        validateEncoding("101010001", "10");
        validateEncoding("1010100010001100100", "11");
        validateEncoding("101010010", "20");
        validateEncoding("10110000001", "100");
        validateEncoding("10110000010", "200");
        validateEncoding("10110010010", "2000");
        validateEncoding("10110100010", "20000");
        validateEncoding("10110110010", "200000");
        validateEncoding("1011100000010", "2000000");
        validateEncoding("1011100010010", "20000000");
        validateEncoding("1011100100001001110101010001101111101111010", "123456789");
    }

    @Test
    void testInvalidEncodings() {
        assertThrows(ParseException.class, () -> DecimalGamma.Decode(""), "no sign bit");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("01"), "invalid sign");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 00"), "exponent length encoding not delimited");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 11110"), "not enough exponent bits");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 011"), "negative exponent sign with absolute exponent value 0");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 100 1"), "invalid length for first digit of significant");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 100 1010"), "first digit of significant is 10 (invalid)");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 100 1001 10"), "invalid length for non-initial digit triplet");
        assertThrows(ParseException.class, () -> DecimalGamma.Decode("10 100 1001 1111101000"), "first triplet is 1000 (invalid)");
    }

    void validateEncoding(String expected, String input) throws ParseException {
        BitSequence output = DecimalGamma.Encode(input);
        assertEquals(expected.replace(" ", ""), output.toString(), input);

        validateInverse(input);
    }

    void validateInverse(String input) throws ParseException {
        assertEquals(input, DecimalGamma.Decode(DecimalGamma.Encode(input)).toString());
    }

    int compare(String a, String b) {
        if (a.compareTo(b) < 0) return -1;
        if (a.compareTo(b) > 0) return 1;
        return 0;
    }


    double nextRandom(Random r) {
        // Make sure our variable is centered around 0
        return (r.nextFloat() * 2 - 1) * r.nextInt(10000);
    }
}
