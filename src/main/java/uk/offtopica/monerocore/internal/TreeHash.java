package uk.offtopica.monerocore.internal;

import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.util.Arrays;

public class TreeHash {
    private static int treeHashCount(int count) {
        assert count >= 3;
        assert count <= 0x10000000;

        int pow = 2;
        while (pow < count) {
            pow <<= 1;
        }
        return pow >> 1;
    }

    public static byte[] treeHash(byte[][] chunks) {
        final var digest = new KeccakDigest(256);
        final var hashSize = 32;

        if (chunks.length == 0) {
            throw new IllegalArgumentException();
        }

        if (chunks.length == 1) {
            return chunks[0];
        }

        if (chunks.length == 2) {
            final var out = new byte[hashSize];
            digest.reset();
            digest.update(chunks[0], 0, hashSize);
            digest.update(chunks[1], 0, hashSize);
            digest.doFinal(out, 0);
            return out;
        }

        final var concat = Arrays.concatenate(chunks);
        final var count = chunks.length;
        var cnt = treeHashCount(count);
        final var ints = new byte[cnt * hashSize];
        System.arraycopy(concat, 0, ints, 0, (2 * cnt - count) * hashSize);

        for (int i = 2 * cnt - count, j = 2 * cnt - count; j < cnt; i += 2,
                ++j) {
            digest.reset();
            digest.update(chunks[i], 0, hashSize);
            digest.update(chunks[i + 1], 0, hashSize);
            digest.doFinal(ints, j * hashSize);
        }

        while (cnt > 2) {
            cnt >>= 1;
            for (int i = 0, j = 0; j < cnt; i += 2, j++) {
                digest.reset();
                digest.update(ints, i * hashSize, 64);
                digest.doFinal(ints, j * hashSize);
            }
        }

        digest.reset();
        digest.update(ints, 0, hashSize * 2);
        final var out = new byte[hashSize];
        digest.doFinal(out, 0);

        return out;
    }
}
