package uk.offtopica.monerocore.codec;

import uk.offtopica.monerocore.blockchain.Block;
import uk.offtopica.monerocore.blockchain.BlockHeader;
import uk.offtopica.monerocore.blockchain.TransactionPrefix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BlockCodec implements Codec<Block> {
    private final BlockHeaderCodec blockHeaderCodec;
    private final TransactionPrefixCodec transactionPrefixCodec;
    private final VarintCodec varintCodec;

    public BlockCodec() {
        blockHeaderCodec = new BlockHeaderCodec();
        transactionPrefixCodec = new TransactionPrefixCodec();
        varintCodec = new VarintCodec();
    }

    @Override
    public Block decode(ByteBuffer byteBuffer) {
        final BlockHeader blockHeader = blockHeaderCodec.decode(byteBuffer);

        final TransactionPrefix minerReward =
                transactionPrefixCodec.decode(byteBuffer);

        // HACK: The miner reward transaction is a full transaction, not just
        // the transaction prefix. However, (at least now) the only
        // difference this makes is the RingCT data. The type is 0 (no data)
        // so we can just pretend that it's a prefix and skip over this.
        final byte minerRewardRingCtType = byteBuffer.get();
        if (minerRewardRingCtType != 0) {
            throw new IllegalArgumentException("minerRewardRingCtType not 0");
        }

        final int transactionHashCount =
                Math.toIntExact(varintCodec.decode(byteBuffer));
        final List<byte[]> transactionHashes =
                new ArrayList<>(transactionHashCount);
        for (int i = 0; i < transactionHashCount; i++) {
            final byte[] hash = new byte[32];
            byteBuffer.get(hash);
            transactionHashes.add(hash);
        }

        return new Block(blockHeader, minerReward, transactionHashes);
    }

    @Override
    public byte[] encode(Block block) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(blockHeaderCodec.encode(block.getHeader()));

            baos.write(transactionPrefixCodec.encode(block.getMinerReward()));

            baos.write(varintCodec.encode((long) block.getTransactionHashes().size()));
            for (byte[] hash : block.getTransactionHashes()) {
                baos.write(hash);
            }
        } catch (IOException e) {
            // This shouldn't happen as we use a ByteArrayOutputStream which
            // can't fail. Re-throw just in case.
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }
}
