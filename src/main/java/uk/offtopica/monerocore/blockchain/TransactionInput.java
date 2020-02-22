package uk.offtopica.monerocore.blockchain;

import java.util.Objects;

public abstract class TransactionInput {
    private final byte inputType;

    public TransactionInput(byte inputType) {
        this.inputType = inputType;
    }

    public byte getInputType() {
        return inputType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInput that = (TransactionInput) o;
        return inputType == that.inputType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputType);
    }

    @Override
    public String toString() {
        return "TransactionInput{" +
                "inputType=" + inputType +
                '}';
    }
}
