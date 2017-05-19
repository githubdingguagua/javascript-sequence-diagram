package org.binqua.testing.csd.external.core;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.xml.transform.TransformerException;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_DASHES;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class XmlBodyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenAValidXmlBodyThenAsJsonIsCorrectAndPrettyFormatted() throws Exception {
        final String bodyContent = "<a><a x='1'><a>1</a></a></a>";
        final XmlBody xmlBody = new XmlBody(bodyContent);

        assertThat(asString(xmlBody.asJson()), is("{\"value\":\"<a>\\n  <a x='1'>\\n    <a>1</a>\\n  </a>\\n</a>\\n\",\"content-type\":\"xml\"}"));

        assertThat(xmlBody.contentType(), is(Body.ContentType.XML));
        assertThat(xmlBody.rawValue(), is(bodyContent));
    }

    @Test
    public void givenAnEmptyBodyThenAsJsonPresentationHasAnEmptyValue() throws Exception {
        assertThat(asString(new XmlBody("").asJson()), is("{\"value\":\"\",\"content-type\":\"xml\"}"));
    }

    @Test
    public void notValidXmlThrowsRuntimeException() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Problem formatting in xml content: a");
        thrown.expectCause(is(instanceOf(TransformerException.class)));
        new XmlBody("a");
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

}