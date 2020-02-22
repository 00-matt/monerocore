package uk.offtopica.monerocore.internal;

public class NumberUtils {
    public static byte toByteExact(long val) {
        if (val > Byte.MAX_VALUE) {
            throw new ArithmeticException("byte overflow");
        }
        return (byte) val;
    }
}
