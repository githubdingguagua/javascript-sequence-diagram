package org.binqua.testing.csd.external.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

class ContentTypeBasedBodyFactory implements BodyFactory {

    private static final String HTTP_CONTENT_TYPE_HEADER_KEY = "Content-Type";

    private Map<Body.ContentType, MessageBodyFactory> contentTypePrettyPrinterMap = new HashMap<Body.ContentType, MessageBodyFactory>() {
        {
            put(Body.ContentType.XML, XmlBody::new);
            put(Body.ContentType.JSON, JsonBody::new);
        }
    };

    private ContentTypeBodyRepresentationMapping contentTypeBodyRepresentationMapping;
    private ObjectMapper objectMapper;

    ContentTypeBasedBodyFactory(ContentTypeBodyRepresentationMapping contentTypeBodyRepresentationMapping, ObjectMapper objectMapper) {
        this.contentTypeBodyRepresentationMapping = contentTypeBodyRepresentationMapping;
        this.objectMapper = objectMapper;
    }

    @Override
    public Body createAMessageBody(String bodyValue, Headers headers) {
        final Body.ContentType contentTypeFromHeaders = contentTypeBodyRepresentationMapping.valueAssociatedTo(retrieveContentTypeFromHeaders(headers));
        return createAMessageBody(bodyValue, contentTypeFromHeaders);
    }

    @Override
    public Body createAMessageBody(String bodyValue, Body.ContentType contentType) {
        final MessageBodyFactory messageBodyFactory = contentTypePrettyPrinterMap.get(contentType);
        return messageBodyFactory == null ? new TextBody(bodyValue): messageBodyFactory.createABody(bodyValue);
    }

    @Override
    public Body createAJsonMessageBody(Object body) {
        try {
            return new JsonBody(objectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            return new JsonBody("{'exception':'could not create a json body for "+body.toString()+"'}");
        }
    }

    private String retrieveContentTypeFromHeaders(Headers headers) {
        final String standardContentTypeKeyValue = headers.valueOf(HTTP_CONTENT_TYPE_HEADER_KEY);
        return standardContentTypeKeyValue == null ? headers.valueOf(HTTP_CONTENT_TYPE_HEADER_KEY.toLowerCase()) : standardContentTypeKeyValue;
    }

    interface MessageBodyFactory{
        Body createABody(String body);
    }
}
