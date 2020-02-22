package uk.offtopica.monerocore.codec;

import uk.offtopica.monerocore.blockchain.TransactionInput;
import uk.offtopica.monerocore.blockchain.TransactionInputGen;
import uk.offtopica.monerocore.blockchain.TransactionInputKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TransactionInputCodec implements Codec<TransactionInput> {
    private final VarintCodec varintCodec;

    public TransactionInputCodec() {
        varintCodec = new VarintCodec();
    }

    @Override
    public TransactionInput decode(ByteBuffer byteBuffer) {
        final byte tag = byteBuffer.get();

        if (tag == TransactionInputGen.TAG) {
            final long height = varintCodec.decode(byteBuffer);
            return new TransactionInputGen(height);
        } else if (tag == TransactionInputKey.TAG) {
            final long amount = varintCodec.decode(byteBuffer);
            final int keyOffsetCount =
                    Math.toIntExact(varintCodec.decode(byteBuffer));
            final List<Long> keyOffsets = new ArrayList<>(keyOffsetCount);
            for (int i = 0; i < keyOffsetCount; i++) {
                keyOffsets.add(varintCodec.decode(byteBuffer));
            }
            final byte[] keyImage = new byte[32];
            byteBuffer.get(keyImage);
            return new TransactionInputKey(amount, keyOffsets, keyImage);
        } else {
            throw new IllegalArgumentException("Unknown input type");
        }
    }

    @Override
    public byte[] encode(TransactionInput transactionInput) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(transactionInput.getInputType());

            if (transactionInput instanceof TransactionInputGen) {
                final var tig = (TransactionInputGen) transactionInput;
                baos.write(varintCodec.encode(tig.getHeight()));
            } else if (transactionInput instanceof TransactionInputKey) {
                final var tik = (TransactionInputKey) transactionInput;
                baos.write(varintCodec.encode(tik.getAmount()));
                baos.write(varintCodec.encode((long) tik.getKeyOffsets().size()));
                for (long keyOffset : tik.getKeyOffsets()) {
                    baos.write(varintCodec.encode(keyOffset));
                }
                baos.write(tik.getKeyImage());
            } else {
                throw new IllegalArgumentException("Unknown target type");
            }
        } catch (IOException e) {
            // This shouldn't happen as we use a ByteArrayOutputStream which
            // can't fail. Re-throw just in case.
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }
}
