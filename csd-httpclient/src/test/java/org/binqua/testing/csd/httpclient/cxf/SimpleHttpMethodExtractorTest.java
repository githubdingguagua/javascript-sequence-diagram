package org.binqua.testing.csd.httpclient.cxf;

import io.vavr.control.Try;
import org.binqua.testing.csd.client.mockito.ACollaboratorResource;
import org.binqua.testing.csd.client.mockito.HttpMethodExtractor;
import org.binqua.testing.csd.httpclient.HttpMessage;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleHttpMethodExtractorTest {

    private final HttpMethodExtractor simpleHttpMethodExtractor = new SimpleHttpMethodExtractor();

    @Test
    public void aGetMethodCanBeExtracted() throws NoSuchMethodException {
        assertThat(
                simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod("getMethod")),
                is(equalTo(Try.success(HttpMessage.HttpMethod.GET)))
        );
    }

    @Test
    public void aPostMethodCanBeExtracted() throws NoSuchMethodException {
        assertThat(
                simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod("postMethod")),
                is(equalTo(Try.success(HttpMessage.HttpMethod.POST)))
        );
    }

    @Test
    public void aDeleteMethodCanBeExtracted() throws NoSuchMethodException {

        assertThat(
                simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod("deleteMethod")),
                is(equalTo(Try.success(HttpMessage.HttpMethod.DELETE)))
        );
    }

    @Test
    public void aPutMethodCanBeExtracted() throws NoSuchMethodException {
        assertThat(
                simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod("putMethod")),
                is(equalTo(Try.success(HttpMessage.HttpMethod.PUT)))
        );
    }

    @Test
    public void methodWithNoAnnotation() throws NoSuchMethodException {
        final String methodWithNoAnnotation = "methodWithNoAnnotation";
        final Try<HttpMessage.HttpMethod> actualResult = simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod(methodWithNoAnnotation));
        assertThat(
                actualResult.getCause(),
                is(instanceOf(IllegalStateException.class))
        );
        assertThat(
                actualResult.getCause().getMessage(),
                is(equalTo("One http method annotation is necessary for method " + methodWithNoAnnotation + " in " + ACollaboratorResource.class.getCanonicalName() + " but no annotation has been provided"))
        );
    }

    @Test
    public void methodWithMoreTheOneHttpAnnotation() throws NoSuchMethodException {
        final String methodAnalysed = "methodWithMoreTheOneHttpAnnotation";
        final Try<HttpMessage.HttpMethod> actualResult = simpleHttpMethodExtractor.extractHttpMethodFrom(ACollaboratorResource.class.getMethod(methodAnalysed));
        assertThat(
                actualResult.getCause(),
                is(instanceOf(IllegalStateException.class))
        );
        assertThat(
                actualResult.getCause().getMessage(),
                is(equalTo("Only one http method is allowed but @PUT and @GET have been found for method " + methodAnalysed + " in " + ACollaboratorResource.class.getCanonicalName()))
        );
    }
}