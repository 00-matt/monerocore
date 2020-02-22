package uk.offtopica.monerocore.codec;

import org.junit.jupiter.api.Test;
import uk.offtopica.monerocore.HexUtils;
import uk.offtopica.monerocore.InvalidHexStringException;
import uk.offtopica.monerocore.blockchain.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionPrefixCodecTest {
    TransactionPrefixCodec transactionPrefixCodec;

    TransactionPrefixCodecTest() {
        transactionPrefixCodec = new TransactionPrefixCodec();
    }

    @Test
    void decodeMinerReward() throws InvalidHexStringException {
        // The miner reward transaction from block #2037961.
        final byte[] input = HexUtils.hexStringToByteArray(
                "0285b27c01ffc9b17c0190e1ed" +
                "b39c38024fbd18111a6dde8d922010d962f051ecb714f72748b5b5079799" +
                "8e8fcfd8cd323e0178c1c5c99b6dbf2c8f7fa9f008581b4be51649334f4e" +
                "8cedf0305c318f6315d4021b6d696e65786d722e636f6d19030000000001" +
                "810000000000000000");

        final TransactionPrefix expected = new TransactionPrefix(
                2,
                2038021,
                List.of(new TransactionInputGen(2037961)),
                List.of(new TransactionOutput(
                        1931770294416L,
                        new TransactionOutputTargetKey(HexUtils.hexStringToByteArray(
                                "4fbd18111a6dde8d922010d962f051ecb714f72748b5" +
                                        "b50797998e8fcfd8cd32")))),
                HexUtils.hexStringToByteArray(
                        "0178c1c5c99b6dbf2c8f7fa9f008581b4be5164" +
                        "9334f4e8cedf0305c318f6315d4021b6d696e65786d722e636f6" +
                        "d19030000000001810000000000000000"
                )
        );

        final TransactionPrefix actual = transactionPrefixCodec.decode(input);

        assertEquals(expected, actual);
    }

    @Test
    void testDecodePayment() throws InvalidHexStringException, IOException {
        // A random 1-in-2-out transaction from block #2037961.
        final byte[] input = HexUtils.hexStringToByteArray(
                "02000102000be3dbae06bed432" +
                "c3e12987d505858001c0ad02de46c158832fef0eef163aa9547c173e963c" +
                "c8599567c10f5317261d21df722f51a8494da8c885100700020002238ab4" +
                "f612537bfb8ae294b505fca6730b9dc66a9a4048318a8610a084b76c7800" +
                "024a36492a3e06c3b20f5edf9a193f1bed6310c81e80a9ac03951864cacc" +
                "a1e5782c02090195042079a522dbf701d46f59aac73ffd28e94ce9716e45" +
                "eec2fc36a6a67a8c4087b5b7d01b2bddbbb5");

        final TransactionPrefix expected = new TransactionPrefix(
                2,
                0,
                List.of(new TransactionInputKey(
                        0,
                        List.of(
                                13348323L,
                                830014L,
                                684227L,
                                92807L,
                                16389L,
                                38592L,
                                9054L,
                                11329L,
                                6019L,
                                1903L,
                                2927L
                        ),
                        HexUtils.hexStringToByteArray(
                                "3aa9547c173e963cc8599567c10f531" +
                                "7261d21df722f51a8494da8c885100700")
                )),
                List.of(
                        new TransactionOutput(
                                0L,
                                new TransactionOutputTargetKey(
                                        HexUtils.hexStringToByteArray(
                                                "238ab4f612537bf" +
                                                "b8ae294b505fca6730b9dc66a9a4" +
                                                "048318a8610a084b76c78")
                                )
                        ),
                        new TransactionOutput(
                                0L,
                                new TransactionOutputTargetKey(
                                        HexUtils.hexStringToByteArray(
                                                "4a36492a3e06c3b" +
                                                "20f5edf9a193f1bed6310c81e80a" +
                                                "9ac03951864cacca1e578")
                                )
                        )
                ),
                HexUtils.hexStringToByteArray(
                        "02090195042079a522dbf701d46f59aac73ffd2" +
                        "8e94ce9716e45eec2fc36a6a67a8c4087b5b7d01b2bddbbb5")
        );

        final TransactionPrefix actual = transactionPrefixCodec.decode(input);

        assertEquals(expected, actual);
    }
}
