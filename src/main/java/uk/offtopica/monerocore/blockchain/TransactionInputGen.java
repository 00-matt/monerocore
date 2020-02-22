package uk.offtopica.monerocore.blockchain;

import java.util.Objects;

public class TransactionInputGen extends TransactionInput {
    public static final byte TAG = (byte) 0xff;

    private final long height;

    public TransactionInputGen(long height) {
        super(TAG);
        // TODO: Validate height.
        this.height = height;
    }

    public long getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInputGen that = (TransactionInputGen) o;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height);
    }

    @Override
    public String toString() {
        return "TransactionInputGen{" +
                "height=" + height +
                '}';
    }
}
