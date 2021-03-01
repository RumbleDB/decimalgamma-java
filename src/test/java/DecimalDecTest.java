import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecimalDecTest {

    @Test
    void name() {
        assertEquals("1", new LiteralDecomposition(false, true, true, 0, new int[]{1}).toString());
        assertEquals("10", new LiteralDecomposition(false, true, true, 1, new int[]{1}).toString());
        assertEquals("100", new LiteralDecomposition(false, true, true, 2, new int[]{1}).toString());
        assertEquals("1000", new LiteralDecomposition(false, true, true, 3, new int[]{1}).toString());
        assertEquals("1001", new LiteralDecomposition(false, true, true, 3, new int[]{1, 0, 0, 1}).toString());
        assertEquals("1.0001", new LiteralDecomposition(false, true, true, 0, new int[]{1, 0, 0, 0, 1}).toString());

        assertEquals("0.1", new LiteralDecomposition(false, true, false, 1, new int[]{1}).toString());
        assertEquals("0.01", new LiteralDecomposition(false, true, false, 2, new int[]{1}).toString());
        assertEquals("0.001", new LiteralDecomposition(false, true, false, 3, new int[]{1}).toString());
        assertEquals("0.0001", new LiteralDecomposition(false, true, false, 4, new int[]{1}).toString());
    }
}