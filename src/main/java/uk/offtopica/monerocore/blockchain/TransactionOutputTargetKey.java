package uk.offtopica.monerocore.blockchain;

import java.util.Arrays;
import java.util.Objects;

public class TransactionOutputTargetKey extends TransactionOutputTarget {
    public static final byte TAG = 0x02;

    private final byte[] key;

    public TransactionOutputTargetKey(byte[] key) {
        super(TAG);
        this.key = Objects.requireNonNull(key);

        // TODO: Validate key.
    }

    public byte[] getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionOutputTargetKey that = (TransactionOutputTargetKey) o;
        return Arrays.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(key);
    }

    @Override
    public String toString() {
        return "TransactionOutputTargetKey{" +
                "key=" + Arrays.toString(key) +
                '}';
    }
}
