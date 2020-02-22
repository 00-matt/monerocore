package uk.offtopica.monerocore.codec;

import org.junit.jupiter.api.Test;
import uk.offtopica.monerocore.HexUtils;
import uk.offtopica.monerocore.InvalidHexStringException;
import uk.offtopica.monerocore.blockchain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockCodecTest {
    final BlockCodec blockCodec;

    BlockCodecTest() {
        blockCodec = new BlockCodec();
    }

    @Test
    void testDecode() throws InvalidHexStringException {
        final byte[] input = HexUtils.hexStringToByteArray(
                "0c0cded8bbf205809aa6d70085" +
                "2e8fbd2c780194e7530e0c9d75742da5d72c3286609949095bf800000000" +
                "02adb27c01fff1b17c01ddb3aa9c9e37025836603a0b12023559f7264358" +
                "e155be0fb62decbdf4fc2a165501d004daf89a5f016904294c4d807d6fb7" +
                "0e2c9ea725938af553d2d4f263fcecb07f23781ea97ec8023c0000000000" +
                "000000000000000000000000000000000000000000000000000000000000" +
                "000000000000000000000000000000000000000000000000000009e6ad1f" +
                "04a4e6ad37acf6c09586e7553750f3ef87f45b93e5c93fffc535ceeba032" +
                "91fe5a249030c8bd32f26fb06d118d3f6d17485e8817ca5e50c83287da2f" +
                "c8455fe6a31410baf8fda2378ca72750d710dd5d85a172ddcaca5d3eb2e6" +
                "cce8db966e654d7541d9538bcda203734ecd2c549176b17b688b4c76667e" +
                "ce0fe8d40c3e770aa1338358491b64dfbe17b5daaf6b33c07195218d50e9" +
                "aa4a12e24b948421c2d9cad1f257ae08e4d12e4cadb4328e35be3a344ddc" +
                "8cc9a0bb5892378477019f958b8d0f26317e5e2dc860a245aa39b85a89e5" +
                "fd9d4c2897912b690e4454da6805e0be6c09dadfe589ea829976ba0fb8f7" +
                "2811614ca0bc3f7fbbb58c38867fe804d0927bf3955590c3ffc3e72c7345" +
                "c0d9278325b848dcdfa658936bbab0");

        final Block actual = blockCodec.decode(input);

        final Block expected = new Block(
                new BlockHeader(
                        (byte) 12,
                        (byte) 12,
                        1582230622L,
                        HexUtils.hexStringToByteArray(
                                "809aa6d700852e8fbd2c780194e7530" +
                                "e0c9d75742da5d72c3286609949095bf8"),
                        HexUtils.hexStringToByteArray("00000000")),
                new TransactionPrefix(
                        2,
                        2038061,
                        List.of(new TransactionInputGen(2038001)),
                        List.of(new TransactionOutput(
                                1897898088925L,
                                new TransactionOutputTargetKey(
                                        HexUtils.hexStringToByteArray(
                                                "5836603a0b12023" +
                                                "559f7264358e155be0fb62decbdf" +
                                                "4fc2a165501d004daf89a")
                                )
                        )),
                        HexUtils.hexStringToByteArray(
                                "016904294c4d807d6fb70e2c9ea725938af553d2d4f2" +
                                        "63fcecb07f23781ea97ec8023c0000000000" +
                                        "000000000000000000000000000000000000" +
                                        "000000000000000000000000000000000000" +
                                        "000000000000000000000000000000000000" +
                                        "00")
                ),
                List.of(
                        HexUtils.hexStringToByteArray(
                                "e6ad1f04a4e6ad37acf6c09586e7553" +
                                "750f3ef87f45b93e5c93fffc535ceeba0"),
                        HexUtils.hexStringToByteArray(
                                "3291fe5a249030c8bd32f26fb06d118" +
                                "d3f6d17485e8817ca5e50c83287da2fc8"),
                        HexUtils.hexStringToByteArray(
                                "455fe6a31410baf8fda2378ca72750d" +
                                "710dd5d85a172ddcaca5d3eb2e6cce8db"),
                        HexUtils.hexStringToByteArray(
                                "966e654d7541d9538bcda203734ecd2" +
                                "c549176b17b688b4c76667ece0fe8d40c"),
                        HexUtils.hexStringToByteArray(
                                "3e770aa1338358491b64dfbe17b5daa" +
                                "f6b33c07195218d50e9aa4a12e24b9484"),
                        HexUtils.hexStringToByteArray(
                                "21c2d9cad1f257ae08e4d12e4cadb43" +
                                "28e35be3a344ddc8cc9a0bb5892378477"),
                        HexUtils.hexStringToByteArray(
                                "019f958b8d0f26317e5e2dc860a245a" +
                                "a39b85a89e5fd9d4c2897912b690e4454"),
                        HexUtils.hexStringToByteArray(
                                "da6805e0be6c09dadfe589ea829976b" +
                                "a0fb8f72811614ca0bc3f7fbbb58c3886"),
                        HexUtils.hexStringToByteArray(
                                "7fe804d0927bf3955590c3ffc3e72c7" +
                                "345c0d9278325b848dcdfa658936bbab0")
                )
        );

        assertEquals(actual, expected);
    }
}
