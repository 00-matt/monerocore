package uk.offtopica.monerocore.blockchain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TransactionInputKey extends TransactionInput {
    public static final byte TAG = 0x02;

    private final long amount;
    private final List<Long> keyOffsets;
    private final byte[] keyImage;

    public TransactionInputKey(long amount, List<Long> keyOffsets,
                               byte[] keyImage) {
        super(TAG);
        this.amount = amount;
        this.keyOffsets = Objects.requireNonNull(keyOffsets);
        this.keyImage = Objects.requireNonNull(keyImage);

        // TODO: Validate keyImage.
    }

    public long getAmount() {
        return amount;
    }

    public List<Long> getKeyOffsets() {
        return keyOffsets;
    }

    public byte[] getKeyImage() {
        return keyImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionInputKey that = (TransactionInputKey) o;
        return amount == that.amount &&
                keyOffsets.equals(that.keyOffsets) &&
                Arrays.equals(keyImage, that.keyImage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), amount, keyOffsets);
        result = 31 * result + Arrays.hashCode(keyImage);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionInputKey{" +
                "amount=" + amount +
                ", keyOffsets=" + keyOffsets +
                ", keyImage=" + Arrays.toString(keyImage) +
                '}';
    }
}
