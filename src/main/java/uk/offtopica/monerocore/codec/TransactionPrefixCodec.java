package uk.offtopica.monerocore.codec;

import uk.offtopica.monerocore.blockchain.TransactionInput;
import uk.offtopica.monerocore.blockchain.TransactionOutput;
import uk.offtopica.monerocore.blockchain.TransactionPrefix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TransactionPrefixCodec implements Codec<TransactionPrefix> {
    private final TransactionInputCodec inputCodec;
    private final TransactionOutputCodec outputCodec;
    private final VarintCodec varintCodec;

    public TransactionPrefixCodec() {
        inputCodec = new TransactionInputCodec();
        outputCodec = new TransactionOutputCodec();
        varintCodec = new VarintCodec();
    }

    @Override
    public TransactionPrefix decode(ByteBuffer byteBuffer) {
        final long version = varintCodec.decode(byteBuffer);
        final long unlockTime = varintCodec.decode(byteBuffer);

        final int inputCount = Math.toIntExact(varintCodec.decode(byteBuffer));
        final List<TransactionInput> inputs = new ArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(inputCodec.decode(byteBuffer));
        }

        final int outputCount = Math.toIntExact(varintCodec.decode(byteBuffer));
        final List<TransactionOutput> outputs = new ArrayList<>(outputCount);
        for (int i = 0; i < outputCount; i++) {
            outputs.add(outputCodec.decode(byteBuffer));
        }

        final int extraLength = Math.toIntExact(varintCodec.decode(byteBuffer));
        final byte[] extra = new byte[extraLength];
        byteBuffer.get(extra);

        return new TransactionPrefix(version, unlockTime, inputs, outputs,
                extra);
    }

    @Override
    public byte[] encode(TransactionPrefix transactionPrefix) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(varintCodec.encode(transactionPrefix.getVersion()));
            baos.write(varintCodec.encode(transactionPrefix.getUnlockTime()));
            baos.write(varintCodec.encode((long) transactionPrefix.getInputs().size()));
            for (TransactionInput input : transactionPrefix.getInputs()) {
                baos.write(inputCodec.encode(input));
            }
            baos.write(varintCodec.encode((long) transactionPrefix.getOutputs().size()));
            for (TransactionOutput input : transactionPrefix.getOutputs()) {
                baos.write(outputCodec.encode(input));
            }
            baos.write(varintCodec.encode((long) transactionPrefix.getExtra().length));
            baos.write(transactionPrefix.getExtra());
        } catch (IOException e) {
            // This shouldn't happen as we use a ByteArrayOutputStream which
            // can't fail. Re-throw just in case.
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }
}
