package uk.offtopica.monerocore.codec;

import uk.offtopica.monerocore.blockchain.BlockHeader;
import uk.offtopica.monerocore.internal.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BlockHeaderCodec implements Codec<BlockHeader> {
    private final VarintCodec varintCodec;

    public BlockHeaderCodec() {
        varintCodec = new VarintCodec();
    }

    @Override
    public BlockHeader decode(ByteBuffer byteBuffer) {
        final byte majorVersion =
                NumberUtils.toByteExact(varintCodec.decode(byteBuffer));
        final byte minorVersion =
                NumberUtils.toByteExact(varintCodec.decode(byteBuffer));
        final long timestamp = varintCodec.decode(byteBuffer);

        byte[] prevHash = new byte[32];
        byteBuffer.get(prevHash);

        byte[] nonce = new byte[4];
        byteBuffer.get(nonce);

        return new BlockHeader(majorVersion, minorVersion, timestamp,
                prevHash, nonce);
    }

    @Override
    public byte[] encode(BlockHeader blockHeader) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(varintCodec.encode((long) blockHeader.getMajorVersion()));
            baos.write(varintCodec.encode((long) blockHeader.getMinorVersion()));
            baos.write(varintCodec.encode(blockHeader.getTimestamp()));
            baos.write(blockHeader.getPrevHash());
            baos.write(blockHeader.getNonce());
        } catch (IOException e) {
            // This shouldn't happen as we use a ByteArrayOutputStream which
            // can't fail. Re-throw just in case.
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }
}
