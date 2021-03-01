import java.util.ArrayList;

public class BitSequence {
    private final ArrayList<Boolean> bites = new ArrayList<>();

    public void appendBits(int bits, int n) {
        ArrayList<Boolean> append = new ArrayList<>(n);
        while (append.size() < n) append.add(false);

        int i = n - 1;
        while (bits > 0 && i >= 0) {
            append.set(i--, bits % 2 == 1);
            bits /= 2;
        }

        bites.addAll(append);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Boolean b : this.bites) {
            out.append(b ? "1" : "0");
        }
        return out.toString();
    }
}
