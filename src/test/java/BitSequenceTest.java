import static org.junit.jupiter.api.Assertions.*;

class Testcase {
    public int actual, length;
    public String expected;

    public Testcase(int actual, int length, String expected) {
        this.actual = actual;
        this.length = length;
        this.expected = expected;
    }
}

class BitSequenceTest {

    @org.junit.jupiter.api.Test
    void testToString() {
        Testcase[] tests = {
                new Testcase(0b10, 2, "10"),
                new Testcase(0b010, 3, "010"),
                new Testcase(0b101111111111110, 15, "101111111111110"),
        };

        for (Testcase t : tests) {
            BitSequence s = new BitSequence();
            s.appendBits(t.actual, t.length);
            assertEquals(t.expected, s.toString());
        }

    }
}