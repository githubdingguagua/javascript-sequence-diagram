package org.binqua.testing.csd.formatter.svg;


import org.binqua.testing.csd.external.core.Identifier;

import java.util.Map;

public class MessageDescriptionDictionary {

    private Map<Identifier, String> keyValuesMap;

    public MessageDescriptionDictionary(Map<Identifier, String> keyValuesMap) {
        this.keyValuesMap = keyValuesMap;
    }

    public String get(Identifier Identifier) {
        return keyValuesMap.get(Identifier);
    }

}
