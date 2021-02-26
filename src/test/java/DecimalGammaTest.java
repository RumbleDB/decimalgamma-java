import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecimalGammaTestCase {
    protected String expected;
    protected String input;

    public DecimalGammaTestCase(String expected, String input) {
        this.expected = expected;
        this.input = input;
    }
}

class DecimalGammaTest {

    @Test
    void testEncode() {
        DecimalGammaTestCase[] testcases = new DecimalGammaTestCase[]{
                new DecimalGammaTestCase("00 001 11 1001 1111001000", "-103.2"),
                new DecimalGammaTestCase("00 110 00 0110 1110110110", "-0.0405"),
                new DecimalGammaTestCase("00 10 1 0011 1110100000 1110101100", "-0.707106"),
                new DecimalGammaTestCase("10 1110 011 0100 0000000101 0000001100 0101011001", "4005012345"),

                // Tests from decimalgamma-cpp
                new DecimalGammaTestCase("0000011011001101111110101101100000001101110", "-123456789"),
                new DecimalGammaTestCase("00001101001", "-1000"),
                new DecimalGammaTestCase("00001111001", "-100"),
                new DecimalGammaTestCase("000101001", "-10"),
                new DecimalGammaTestCase("000110001", "-9"),
                new DecimalGammaTestCase("000110010", "-8"),
                new DecimalGammaTestCase("000110011", "-7"),
                new DecimalGammaTestCase("000110100", "-6"),
                new DecimalGammaTestCase("000110101", "-5"),
                new DecimalGammaTestCase("000110110", "-4"),
                new DecimalGammaTestCase("000110111", "-3"),
                new DecimalGammaTestCase("000111000", "-2"),
                new DecimalGammaTestCase("000111001", "-1"),
                new DecimalGammaTestCase("10", "0"),
                new DecimalGammaTestCase("10001110010", "0.02"),
                new DecimalGammaTestCase("100100010", "0.2"),
                new DecimalGammaTestCase("101000001", "1"),
                new DecimalGammaTestCase("101000010", "2"),
                new DecimalGammaTestCase("101000011", "3"),
                new DecimalGammaTestCase("101000100", "4"),
                new DecimalGammaTestCase("101000101", "5"),
                new DecimalGammaTestCase("101000110", "6"),
                new DecimalGammaTestCase("101000111", "7"),
                new DecimalGammaTestCase("101001000", "8"),
                new DecimalGammaTestCase("101001001", "9"),
                new DecimalGammaTestCase("101010001", "10"),
                new DecimalGammaTestCase("1010100010001100100", "11"),
                new DecimalGammaTestCase("101010010", "20"),
                new DecimalGammaTestCase("10110000001", "100"),
                new DecimalGammaTestCase("10110000010", "200"),
                new DecimalGammaTestCase("10110010010", "2000"),
                new DecimalGammaTestCase("10110100010", "20000"),
                new DecimalGammaTestCase("10110110010", "200000"),
                new DecimalGammaTestCase("1011100000010", "2000000"),
                new DecimalGammaTestCase("1011100010010", "20000000"),
                new DecimalGammaTestCase("1011100100001001110101010001101111101111010", "123456789"),
        };


        for (DecimalGammaTestCase test : testcases) {
            BitSequence output = DecimalGamma.Encode(new StringDecomposition(test.input));
            assertEquals(test.expected.replace(" ", ""), output.toString(), test.input);
        }
    }
}
