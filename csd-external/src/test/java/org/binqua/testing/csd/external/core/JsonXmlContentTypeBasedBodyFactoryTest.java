package org.binqua.testing.csd.external.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.binqua.testing.csd.external.core.Body.ContentType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonXmlContentTypeBasedBodyFactoryTest {

    private static final String CONTENT_TYPE_HTTP_KEY = "Content-Type";
    private static final String JSON_BODY_CONTENT = "{\"a\":1}";
    private static final String XML_BODY_CONTENT = "<a><a x='1'></a></a>";

    private final Headers httHeaders = mock(Headers.class);

    private final ObjectMapper objectMapperForTestPurpose = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private final BodyFactory bodyFactoryUnderTest = new JsonXmlContentTypeBasedBodyFactory(objectMapperForTestPurpose);

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

    @Test
    public void canCreateAJsonMessageBodyViaTheObjectMapper() {
        assertThat(bodyFactoryUnderTest.createAJsonMessageBody(new ABean("rob")), is(new JsonBody("{\n  \"name\": \"rob\"\n}")));
    }

    @Test
    public void canCreateAJsonMessageBodyViaTheObjectMapperEvenIfConversionToJsonFails() {
        final JsonXmlContentTypeBasedBodyFactory bodyFactoryUnderTest = new JsonXmlContentTypeBasedBodyFactory(anObjectMapperThatCannotSerialisedABean());

        assertThat(bodyFactoryUnderTest.createAJsonMessageBody(new ABean("bob")), is(new JsonBody("{\n  \"exception\": \"could not create a json body for <Bean toString>\"\n}")));
    }

    private ObjectMapper anObjectMapperThatCannotSerialisedABean() {
        return new ObjectMapper();
    }

    class ABean  {
        private String name;

        ABean(@JsonProperty("name") String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "<Bean toString>";
        }
    }

}