package org.binqua.testing.csd.external.core;

public interface BodyFactory {

    Body createAMessageBody(String value, Headers headers);

    Body createAMessageBody(String value, Body.ContentType contentType);

    Body createAJsonMessageBody(Object body);

}
