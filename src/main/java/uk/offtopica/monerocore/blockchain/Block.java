package uk.offtopica.monerocore.blockchain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Block {
    private final BlockHeader header;
    private final TransactionPrefix minerReward;
    private final List<byte[]> transactionHashes;

    public Block(BlockHeader header, TransactionPrefix minerReward,
                 List<byte[]> transactionHashes) {
        this.header = Objects.requireNonNull(header);
        this.minerReward = Objects.requireNonNull(minerReward);
        this.transactionHashes = Objects.requireNonNull(transactionHashes);

        // TODO: Validate minerReward.
        // TODO: Validate transactionHashes.
    }

    public BlockHeader getHeader() {
        return header;
    }

    public TransactionPrefix getMinerReward() {
        return minerReward;
    }

    public List<byte[]> getTransactionHashes() {
        return transactionHashes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return header.equals(block.header) &&
                minerReward.equals(block.minerReward) &&
                Arrays.deepEquals(transactionHashes.toArray(),
                        block.transactionHashes.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, minerReward, transactionHashes);
    }

    @Override
    public String toString() {
        return "Block{" +
                "header=" + header +
                ", minerReward=" + minerReward +
                ", transactionHashes=" + transactionHashes +
                '}';
    }
}
