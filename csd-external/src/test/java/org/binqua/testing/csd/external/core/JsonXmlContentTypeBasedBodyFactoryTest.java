package org.binqua.testing.csd.external.core;

import org.junit.Test;

import org.binqua.testing.csd.external.core.Body.ContentType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonXmlContentTypeBasedBodyFactoryTest {

    private static final String CONTENT_TYPE_HTTP_KEY = "Content-Type";
    private static final String JSON_BODY_CONTENT = "{\"a\":1}";
    private static final String XML_BODY_CONTENT = "<a><a x='1'></a></a>";

    private final Headers httHeaders = mock(Headers.class);

    private final BodyFactory bodyFactoryUnderTest = new JsonXmlContentTypeBasedBodyFactory();

    @Test
    public void xmlBodyCanBeConstructedFromApplicationXml() {

        when(httHeaders.valueOf(CONTENT_TYPE_HTTP_KEY)).thenReturn("application/xml");

        final String bodyContent = "<a><a x='1'></a></a>";
        final XmlBody expectedXmlMessageBody = new XmlBody(bodyContent);

        assertThat(bodyFactoryUnderTest.createAMessageBody(bodyContent, httHeaders), is(expectedXmlMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(bodyContent, ContentType.XML), is(expectedXmlMessageBody));

    }

    @Test
    public void xmlBodyCanBeConstructedFromTextXml() {

        when(httHeaders.valueOf(CONTENT_TYPE_HTTP_KEY)).thenReturn("text/xml");

        final XmlBody expectedXmlMessageBody = new XmlBody(XML_BODY_CONTENT);

        assertThat(bodyFactoryUnderTest.createAMessageBody(XML_BODY_CONTENT, httHeaders), is(expectedXmlMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(XML_BODY_CONTENT, ContentType.XML), is(expectedXmlMessageBody));

    }

    @Test
    public void contentTypeKeyLookupInTheHeadersIsCaseInsensitive() {

        when(httHeaders.valueOf(CONTENT_TYPE_HTTP_KEY.toLowerCase())).thenReturn("application/xml");

        final XmlBody expectedXmlMessageBody = new XmlBody(XML_BODY_CONTENT);

        assertThat(bodyFactoryUnderTest.createAMessageBody(XML_BODY_CONTENT, httHeaders), is(expectedXmlMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(XML_BODY_CONTENT, ContentType.XML), is(expectedXmlMessageBody));

    }

    @Test
    public void jsonBodyCanBeConstructedFromApplicationJson() {

        when(httHeaders.valueOf(CONTENT_TYPE_HTTP_KEY.toLowerCase())).thenReturn("application/json");

        final JsonBody expectedJsonMessageBody = new JsonBody(JSON_BODY_CONTENT);

        assertThat(bodyFactoryUnderTest.createAMessageBody(JSON_BODY_CONTENT, httHeaders), is(expectedJsonMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(JSON_BODY_CONTENT, ContentType.JSON), is(expectedJsonMessageBody));

    }


    @Test
    public void jsonBodyCanBeConstructedFromTextJson() {

        when(httHeaders.valueOf(CONTENT_TYPE_HTTP_KEY.toLowerCase())).thenReturn("text/json");

        final JsonBody expectedJsonMessageBody = new JsonBody(JSON_BODY_CONTENT);

        assertThat(bodyFactoryUnderTest.createAMessageBody(JSON_BODY_CONTENT, httHeaders), is(expectedJsonMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(JSON_BODY_CONTENT, ContentType.JSON), is(expectedJsonMessageBody));

    }

    @Test
    public void giveThatContentTypeIsMapToTextThenNoFormatHappens() {

        final String bodyContent = "some content to be formatted as text";
        final TextBody expectedTextMessageBody = new TextBody(bodyContent);

        assertThat(bodyFactoryUnderTest.createAMessageBody(bodyContent, httHeaders), is(expectedTextMessageBody));
        assertThat(bodyFactoryUnderTest.createAMessageBody(bodyContent, ContentType.TEXT), is(expectedTextMessageBody));

    }

}