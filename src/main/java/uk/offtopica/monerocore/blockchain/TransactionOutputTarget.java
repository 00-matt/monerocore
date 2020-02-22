package uk.offtopica.monerocore.blockchain;

public abstract class TransactionOutputTarget {
    private final byte outputType;

    public TransactionOutputTarget(byte outputType) {
        this.outputType = outputType;
    }

    public byte getOutputType() {
        return outputType;
    }
}
