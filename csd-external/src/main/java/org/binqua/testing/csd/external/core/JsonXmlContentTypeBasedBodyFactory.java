package org.binqua.testing.csd.external.core;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonXmlContentTypeBasedBodyFactory implements BodyFactory {

    private final ContentTypeBasedBodyFactory bodyFactory;

    public JsonXmlContentTypeBasedBodyFactory(ObjectMapper objectMapper) {
        final ModifiableContentTypeBodyRepresentationMapping contentTypeBodyRepresentationMapping = new MapBasedContentTypeBodyRepresentationMapping();
        contentTypeBodyRepresentationMapping.addKeyValue("application/xml", Body.ContentType.XML);
        contentTypeBodyRepresentationMapping.addKeyValue("text/xml", Body.ContentType.XML);
        contentTypeBodyRepresentationMapping.addKeyValue("application/json", Body.ContentType.JSON);
        contentTypeBodyRepresentationMapping.addKeyValue("text/json", Body.ContentType.JSON);
        this.bodyFactory = new ContentTypeBasedBodyFactory(contentTypeBodyRepresentationMapping, objectMapper);
    }

    @Override
    public Body createAMessageBody(String value, Headers headers) {
        return bodyFactory.createAMessageBody(value, headers);
    }

    @Override
    public Body createAMessageBody(String value, Body.ContentType contentType) {
        return bodyFactory.createAMessageBody(value, contentType);
    }

    @Override
    public Body createAJsonMessageBody(Object body) {
        return bodyFactory.createAJsonMessageBody(body);
    }

}
