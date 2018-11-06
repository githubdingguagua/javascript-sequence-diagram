package org.binqua.testing.csd.formatter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

public class ScenarioIdGenerator implements IdGenerator {

    public static final String KEY_PREFIX = "scenario-";
    private final Map<String, String> map = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public String record(String externalKey) {
        checkThatKeySyntaxIsValid(externalKey);
        final String keyMaybeAlreadyRegistered = map.get(externalKey);
        if (keyMaybeAlreadyRegistered != null) {
            return keyMaybeAlreadyRegistered;
        }
        final String generatedInternalKey = generateInternalKey();
        map.put(externalKey, generatedInternalKey);
        return generatedInternalKey;
    }

    private void checkThatKeySyntaxIsValid(String key) {
        checkNoWhitespacesIn(key);
        checkNoUppercaseIn(key);
//        checkAtLeastASemicolon(key);
    }

    private void checkAtLeastASemicolon(String key) {
        final int featureIdSeparatorIndex = key.lastIndexOf(";");
        if (featureIdSeparatorIndex == -1) {
            throw new IllegalArgumentException(format("Key must contain a semicolon: %s is wrong", key));
        }
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

    public String idOf(String externalKey) {
        checkNoWhitespacesIn(externalKey);
        checkNoUppercaseIn(externalKey);
        return map.get(externalKey);
    }

    @Override
    public String backgroundId() {
        return generateInternalKey();
    }

    private String generateInternalKey() {
        return new StringBuilder().append(KEY_PREFIX).append(id.getAndIncrement()).toString();
    }
}
