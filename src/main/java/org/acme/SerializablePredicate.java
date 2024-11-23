package org.acme;

import java.io.Serializable;

@FunctionalInterface
public interface SerializablePredicate<T> extends Serializable {
    boolean test(T t);
}
