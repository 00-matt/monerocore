package uk.offtopica.monerocore;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static uk.offtopica.monerocore.HexUtils.hexStringToByteArray;

class BlockHashingBlobCreatorTest {
    final BlockHashingBlobCreator bhbc;

    BlockHashingBlobCreatorTest() {
        bhbc = new BlockHashingBlobCreator();
    }

    @ParameterizedTest(name = "[{index}]")
    @CsvFileSource(resources = "hashing-blobs.csv")
    void testGetHashingBlob(String templateBlobHex, String hashingBlobHex)
            throws InvalidHexStringException {
        final var templateBlob = hexStringToByteArray(templateBlobHex);
        final var expected = hexStringToByteArray(hashingBlobHex);
        final var actual = bhbc.getHashingBlob(templateBlob);

        assertArrayEquals(expected, actual);
    }
}
