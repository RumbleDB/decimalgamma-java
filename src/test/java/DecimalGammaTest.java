import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DecimalGammaTest {

    @Test
    void encode() {
        assertEquals("0000111100011110010000", DecimalGamma.Encode(new MockDecomposition()).toString());
    }
}

class MockDecomposition implements DecimalDecomposition {

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isPositive() {
        return false;
    }

    @Override
    public boolean isExponentNonNegative() {
        return true;
    }

    @Override
    public int getAbsoluteExponent() {
        return 2;
    }

    @Override
    public int[] getDigits() {
        return new int[]{1, 0, 3, 2};
    }
}