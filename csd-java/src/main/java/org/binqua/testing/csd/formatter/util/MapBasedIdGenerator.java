package org.binqua.testing.csd.formatter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

public class MapBasedIdGenerator implements IdGenerator {

    private final AtomicInteger id = new AtomicInteger(1);
    private final String prefix;
    private final Map<String, String> map = new HashMap<>();

    public MapBasedIdGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String record(String key) {
        checkNoWhitespacesIn(key);
        checkNoUppercaseIn(key);
        final String generatedKey = generateAKey(prefix);
        map.put(key, generatedKey);
        return generatedKey;
    }

    private void checkNoUppercaseIn(String key) {
        if (key.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException(format("Key cannot contain uppercase: %s is wrong", key));
        }
    }

    private void checkNoWhitespacesIn(String key) {
        if (key.contains(" ")) {
            throw new IllegalArgumentException(format("Key cannot contain white spaces: %s is wrong", key));
        }
    }

    @Override
    public String idOf(String key) {
        checkNoWhitespacesIn(key);
        checkNoUppercaseIn(key);
        return map.get(key);
    }

    @Override
    public String backgroundId() {
        throw new UnsupportedOperationException();
    }

    private String generateAKey(String prefix) {
        return new StringBuilder().append(prefix).append("-").append(id.getAndIncrement()).toString();
    }
}
