package uk.offtopica.monerocore.codec;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class VarintCodec implements Codec<Long> {
    @Override
    public Long decode(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() == 0) {
            throw new IllegalArgumentException("byteBuffer is empty");
        }

        long val = 0;
        int shift = 0;
        long last;

        do {
            last = byteBuffer.get();
            val |= (last & 0x7f) << shift;
            shift += 7;
        } while ((last & 0x80) != 0);

        return val;
    }

    @Override
    public byte[] encode(Long val) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while (val > 0x7f) {
            baos.write((byte) ((val & 0x7f) | 0x80));
            val >>>= 7;
        }
        baos.write((byte) (val & 0x7f));

        return baos.toByteArray();
    }
}
