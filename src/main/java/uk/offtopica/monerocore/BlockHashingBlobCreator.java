package uk.offtopica.monerocore;

import org.bouncycastle.crypto.digests.KeccakDigest;
import uk.offtopica.monerocore.blockchain.Block;
import uk.offtopica.monerocore.blockchain.TransactionPrefix;
import uk.offtopica.monerocore.codec.BlockCodec;
import uk.offtopica.monerocore.codec.BlockHeaderCodec;
import uk.offtopica.monerocore.codec.TransactionPrefixCodec;
import uk.offtopica.monerocore.codec.VarintCodec;
import uk.offtopica.monerocore.internal.TreeHash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BlockHashingBlobCreator {
    private final BlockCodec blockCodec;
    private final BlockHeaderCodec blockHeaderCodec;
    private final TransactionPrefixCodec transactionPrefixCodec;
    private final VarintCodec varintCodec;

    public BlockHashingBlobCreator() {
        blockCodec = new BlockCodec();
        blockHeaderCodec = new BlockHeaderCodec();
        transactionPrefixCodec = new TransactionPrefixCodec();
        varintCodec = new VarintCodec();
    }

    public byte[] getHashingBlob(byte[] block) {
        return getHashingBlob(blockCodec.decode(block));
    }

    public byte[] getHashingBlob(Block block) {
        final byte[] blockHeader = blockHeaderCodec.encode(block.getHeader());
        final byte[] merkleRoot = getMerkleRoot(block);
        final byte[] num =
                varintCodec.encode((long) block.getTransactionHashes().size() + 1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(blockHeader);
            baos.write(merkleRoot);
            baos.write(num);
        } catch (IOException e) {
            // This shouldn't happen as we use a ByteArrayOutputStream which
            // can't fail. Re-throw just in case.
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }

    private byte[] getMerkleRoot(Block block) {
        final byte[][] transactions =
                new byte[block.getTransactionHashes().size() + 1][];
        transactions[0] = getMinerTxHash(block.getMinerReward());
        for (int i = 0; i < block.getTransactionHashes().size(); i++) {
            transactions[i + 1] = block.getTransactionHashes().get(i);
        }
        return TreeHash.treeHash(transactions);
    }

    private byte[] getMinerTxHash(TransactionPrefix transactionPrefix) {
        final var digest = new KeccakDigest(256);

        final var hashes = new byte[3][32];

        final var prefix = transactionPrefixCodec.encode(transactionPrefix);
        digest.reset();
        digest.update(prefix, 0, prefix.length);
        digest.doFinal(hashes[0], 0);

        // Pretending
        digest.reset();
        digest.update((byte) 0);
        digest.doFinal(hashes[1], 0);

        digest.reset();
        digest.update(hashes[0], 0, hashes[0].length);
        digest.update(hashes[1], 0, hashes[1].length);
        digest.update(hashes[2], 0, hashes[2].length);
        digest.doFinal(hashes[2], 0);

        return hashes[2];
    }
}
