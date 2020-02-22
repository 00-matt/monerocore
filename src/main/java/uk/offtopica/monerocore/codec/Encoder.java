package uk.offtopica.monerocore.codec;

public interface Encoder<T> {

    byte[] encode(T t);
}
