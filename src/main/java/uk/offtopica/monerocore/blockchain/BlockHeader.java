package uk.offtopica.monerocore.blockchain;

import java.util.Arrays;
import java.util.Objects;

public class BlockHeader {
    private final byte majorVersion;
    private final byte minorVersion;
    private final long timestamp;
    private final byte[] prevHash;
    private final byte[] nonce;

    public BlockHeader(byte majorVersion, byte minorVersion, long timestamp,
                       byte[] prevHash, byte[] nonce) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.timestamp = timestamp;
        this.prevHash = Objects.requireNonNull(prevHash);
        this.nonce = Objects.requireNonNull(nonce);

        if (prevHash.length != 32) {
            throw new IllegalArgumentException("prevHash be 32 bytes");
        }
        if (nonce.length != 4) {
            throw new IllegalArgumentException("nonce must be 4 bytes");
        }
    }

    public byte getMajorVersion() {
        return majorVersion;
    }

    public byte getMinorVersion() {
        return minorVersion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public byte[] getPrevHash() {
        return prevHash;
    }

    public byte[] getNonce() {
        return nonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockHeader blockHeader = (BlockHeader) o;
        return majorVersion == blockHeader.majorVersion &&
                minorVersion == blockHeader.minorVersion &&
                timestamp == blockHeader.timestamp &&
                Arrays.equals(prevHash, blockHeader.prevHash) &&
                Arrays.equals(nonce, blockHeader.nonce);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(majorVersion, minorVersion, timestamp);
        result = 31 * result + Arrays.hashCode(prevHash);
        result = 31 * result + Arrays.hashCode(nonce);
        return result;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "majorVersion=" + majorVersion +
                ", minorVersion=" + minorVersion +
                ", timestamp=" + timestamp +
                ", prevHash=" + Arrays.toString(prevHash) +
                ", nonce=" + Arrays.toString(nonce) +
                '}';
    }
}
