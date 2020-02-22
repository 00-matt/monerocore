package uk.offtopica.monerocore.codec;

import java.nio.ByteBuffer;

public interface Decoder<T> {
    T decode(ByteBuffer byteBuffer);

    default T decode(byte[] bytes) {
        return decode(ByteBuffer.wrap(bytes));
    }
}
