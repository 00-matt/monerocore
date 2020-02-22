package uk.offtopica.monerocore.codec;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VarintCodecTest {
    VarintCodec varintCodec;

    VarintCodecTest() {
        varintCodec = new VarintCodec();
    }

    static Stream<byte[]> invalid() {
        return Stream.of(
                new byte[]{}
                // TODO: Varints that end too soon, or that overflow a long.
        );
    }

    static Stream<Arguments> valid() {
        return Stream.of(
                Arguments.of(new byte[]{0x00}, 0),
                Arguments.of(new byte[]{0x6a}, 106),
                Arguments.of(new byte[]{(byte) 0xac, 0x02}, 300),
                Arguments.of(new byte[]{(byte) 0xf4, 0x03}, 500),
                Arguments.of(new byte[]{(byte) 0xfe, (byte) 0xff, 0x03}, 65534),
                Arguments.of(new byte[]{(byte) 0x80, (byte) 0x80, 0x04}, 65536),
                Arguments.of(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff,
                        (byte) 0xff, 0x07}, 2147483647), // (2^31) - 1
                Arguments.of(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff,
                        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                        0x0f}, 9007199254740991L) // (2^63) - 1
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalid")
    void decodeInvalid(byte[] varint) {
        assertThrows(IllegalArgumentException.class,
                () -> varintCodec.decode(varint));
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @MethodSource("valid")
    void decodeValid(byte[] input, long expected) {
        long actual = varintCodec.decode(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @MethodSource("valid")
    void encode(byte[] expected, long input) {
        byte[] actual = varintCodec.encode(input);
        assertArrayEquals(expected, actual);
    }
}
