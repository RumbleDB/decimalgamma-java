package decimalgamma;

import java.util.ArrayList;

public class BitSequence {
    private final ArrayList<Boolean> bits = new ArrayList<>();

    public BitSequence(String bits) {
        for (char b : bits.toCharArray()) {
            if (b == '0') this.appendBits(0b0, 1);
            else if (b == '1') this.appendBits(0b1, 1);
            else if (b == ' ' || b == '_') continue;
            else throw new RuntimeException("unexpected character, expected a string of 0 and 1");
        }
    }

    public BitSequence(int bits, int n) {
        this.appendBits(bits, n);
    }

    public BitSequence() {
    }

    public void appendBits(int bits, int n) {
        ArrayList<Boolean> append = new ArrayList<>(n);
        while (append.size() < n) append.add(false);

        int i = n - 1;
        while (bits > 0 && i >= 0) {
            append.set(i--, bits % 2 == 1);
            bits /= 2;
        }

        this.bits.addAll(append);
    }

    public int length() {
        return this.bits.size();
    }

    public int getBits(int i, int n) {
        n += i;
        int out = 0;
        for (; i < n; i++) {
            out = out * 2 + (this.bits.get(i) ? 1 : 0);
        }

        return out;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Boolean b : this.bits) {
            out.append(b ? "1" : "0");
        }
        return out.toString();
    }

    public boolean[] toBytes() {
        boolean[] out = new boolean[this.bits.size()];
        for (int i = 0; i < this.bits.size(); i++)
            out[i] = this.bits.get(i);

        return out;
    }
}
