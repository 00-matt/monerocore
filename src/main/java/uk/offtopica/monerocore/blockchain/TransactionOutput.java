package uk.offtopica.monerocore.blockchain;

import java.util.Objects;

public class TransactionOutput {
    private final long amount;
    private final TransactionOutputTarget target;

    public TransactionOutput(long amount, TransactionOutputTarget target) {
        this.amount = amount;
        this.target = Objects.requireNonNull(target);
    }

    public long getAmount() {
        return amount;
    }

    public TransactionOutputTarget getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionOutput that = (TransactionOutput) o;
        return amount == that.amount &&
                target.equals(that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, target);
    }

    @Override
    public String toString() {
        return "TransactionOutput{" +
                "amount=" + amount +
                ", target=" + target +
                '}';
    }
}
