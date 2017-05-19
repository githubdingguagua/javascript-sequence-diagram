package org.binqua.testing.csd.external.core;

import java.util.HashMap;
import java.util.Map;

class MapBasedContentTypeBodyRepresentationMapping implements ModifiableContentTypeBodyRepresentationMapping {

    private Map<String, Body.ContentType> map = new HashMap<>();

    @Override
    public Body.ContentType valueAssociatedTo(String contentTypeHttpValue) {
        final Body.ContentType valueFound = map.get(contentTypeHttpValue);
        return valueFound == null ? Body.ContentType.TEXT : valueFound;
    }

    @Override
    public void addKeyValue(String contentTypeValue, Body.ContentType contentType) {
        map.put(contentTypeValue, contentType);
    }
}
