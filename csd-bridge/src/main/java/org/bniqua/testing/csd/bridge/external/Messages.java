package org.bniqua.testing.csd.bridge.external;

import java.util.List;

public interface Messages<T> {

    void add(T message);

    List<T> list();
}
