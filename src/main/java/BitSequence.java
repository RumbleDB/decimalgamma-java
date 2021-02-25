public class BitSequence {
    private int sequence = 0;
    private int lenght = 0;

    public void appendBits(int bits, int n) {
        this.lenght += n;
        this.sequence <<= n;
        this.sequence += bits;
    }

    @Override
    public String toString() {
        return String
                .format("%1$" + this.lenght + "s", Integer.toBinaryString(this.sequence))
                .replace(" ", "0");
    }
}
