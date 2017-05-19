package org.binqua.testing.csd.external.core;

interface ModifiableContentTypeBodyRepresentationMapping extends ContentTypeBodyRepresentationMapping {
    void addKeyValue(String contentTypeHttpValue, Body.ContentType contentType);
}
