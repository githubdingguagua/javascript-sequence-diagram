package org.binqua.testing.csd.formatter.util;

public interface IdGenerator {

    String record(String name);

    String idOf(String name);

    String backgroundId();
}
