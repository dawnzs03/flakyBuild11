package io.quarkus.runtime;

/**
 * An interface that can be used to substitute a non-bytecode serializable class
 * with a replacement.
 *
 * Instances of this interface must be public, and have a public default constructor,
 * as they will be instantiated directly via generated bytecode
 *
 * @param <F> The non-serializable class
 * @param <T> The serializable class
 */
public interface ObjectSubstitution<F, T> {

    T serialize(F obj);

    F deserialize(T obj);

}
