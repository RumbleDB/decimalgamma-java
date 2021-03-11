package decimalgamma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitSequenceTest {
    @Test
    void testToString() {
        validateSequence("10", 0b10, 2);
        validateSequence("010", 0b010, 3);
        validateSequence("101111111111110", 0b101111111111110, 15);

    }

    void validateSequence(String expected, int actual, int length) {
        BitSequence s = new BitSequence();
        s.appendBits(actual, length);
        assertEquals(expected, s.toString());
    }
}