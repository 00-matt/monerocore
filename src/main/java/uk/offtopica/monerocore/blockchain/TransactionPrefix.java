package uk.offtopica.monerocore.blockchain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TransactionPrefix {
    private final long version;
    private final long unlockTime;
    private final List<TransactionInput> inputs;
    private final List<TransactionOutput> outputs;
    private final byte[] extra;

    public TransactionPrefix(long version,
                             long unlockTime,
                             List<TransactionInput> inputs,
                             List<TransactionOutput> outputs,
                             byte[] extra) {
        this.version = version;
        this.unlockTime = unlockTime;
        this.inputs = Objects.requireNonNull(inputs);
        this.outputs = Objects.requireNonNull(outputs);
        this.extra = Objects.requireNonNull(extra);
    }

    public long getVersion() {
        return version;
    }

    public long getUnlockTime() {
        return unlockTime;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public byte[] getExtra() {
        return extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionPrefix that = (TransactionPrefix) o;
        return version == that.version &&
                unlockTime == that.unlockTime &&
                inputs.equals(that.inputs) &&
                outputs.equals(that.outputs) &&
                Arrays.equals(extra, that.extra);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(version, unlockTime, inputs, outputs);
        result = 31 * result + Arrays.hashCode(extra);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionPrefix{" +
                "version=" + version +
                ", unlockTime=" + unlockTime +
                ", inputs=" + inputs +
                ", outputs=" + outputs +
                ", extra=" + Arrays.toString(extra) +
                '}';
    }
}
