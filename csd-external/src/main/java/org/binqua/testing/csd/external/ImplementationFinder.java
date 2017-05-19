package org.binqua.testing.csd.external;

public interface ImplementationFinder<T> {
    T findAnImplementationOf(Class<T> aClass, Object[] implementations);
}
