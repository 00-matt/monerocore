package uk.offtopica.monerocore.internal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import uk.offtopica.monerocore.HexUtils;
import uk.offtopica.monerocore.InvalidHexStringException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TreeHashTest {
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "tree-hashes.csv")
    void testTreeHash(String expectedHex, String inputHex) throws InvalidHexStringException {
        final var input = HexUtils.hexStringToByteArray(inputHex);
        final var inputCount = input.length / 32;
        final var inputs = new byte[inputCount][];
        for (int i = 0; i < inputCount; i++) {
            inputs[i] = Arrays.copyOfRange(input, i * 32, (i + 1) * 32);
        }

        final var actual = TreeHash.treeHash(inputs);
        final var expected = HexUtils.hexStringToByteArray(expectedHex);
        assertArrayEquals(expected, actual);
    }
}
