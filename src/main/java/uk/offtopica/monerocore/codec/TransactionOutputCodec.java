package uk.offtopica.monerocore.codec;

import uk.offtopica.monerocore.blockchain.TransactionOutput;
import uk.offtopica.monerocore.blockchain.TransactionOutputTarget;
import uk.offtopica.monerocore.blockchain.TransactionOutputTargetKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TransactionOutputCodec implements Codec<TransactionOutput> {
    private final VarintCodec varintCodec;

    public TransactionOutputCodec() {
        varintCodec = new VarintCodec();
    }

    @Override
    public TransactionOutput decode(ByteBuffer byteBuffer) {
        final long amount = varintCodec.decode(byteBuffer);
        final byte targetTag = byteBuffer.get();
        final TransactionOutputTarget target;

        if (targetTag == TransactionOutputTargetKey.TAG) {
            final byte[] key = new byte[32];
            byteBuffer.get(key);
            target = new TransactionOutputTargetKey(key);
        } else {
            throw new IllegalArgumentException("Unknown target type");
        }

        return new TransactionOutput(amount, target);
    }

    @Override
    public byte[] encode(TransactionOutput transactionOutput) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(varintCodec.encode(transactionOutput.getAmount()));

            final TransactionOutputTarget target =
                    transactionOutput.getTarget();

            baos.write(target.getOutputType());

            if (target instanceof TransactionOutputTargetKey) {
                final var totk = (TransactionOutputTargetKey) target;
                baos.write(totk.getKey());
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
