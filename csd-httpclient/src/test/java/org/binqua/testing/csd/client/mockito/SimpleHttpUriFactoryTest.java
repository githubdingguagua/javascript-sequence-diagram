package org.binqua.testing.csd.client.mockito;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimpleHttpUriFactoryTest {

    private static final String MICROSERVICE_CALLEE_ROOT_URL = "http://microservice/callee/root/url";

    private final HttpUriFactory simpleHttpUriFactory = new SimpleHttpUriFactory();

    @Test
    public void givenAMethodWithNoPathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWithNoRequestParameters");

        final Object[] methodUnderExecutionArguments = {};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1/prefix2"))
        );

    }

    @Test
    public void givenAMethodWithOnly1PathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParameters", RequestArgument.class);

        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0")};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1/prefix2/a0"))
        );

    }

    @Test
    public void givenAMethodWith1RequestParameterAndAnotherNoPathParamAsFirstParameterThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParametersAndAnotherNoPathParamAsFirstParameter", String.class, RequestArgument.class);

        final Object[] methodUnderExecutionArguments = {"aValue", new RequestArgument("a0")};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1/prefix2/a0"))
        );

    }

    @Test
    public void givenAMethodWith1RequestParametersAndAnotherNoPathParamAsLastParameterThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParametersAndAnotherNoPathParamAsLastParameter", RequestArgument.class, String.class);

        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0"), "aValue"};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1/prefix2/a0"))
        );

    }

    @Test
    public void givenAMethodWithOnly2PathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith2RequestParameters", RequestArgument.class, RequestArgument.class);

        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0"), new RequestArgument("a1")};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1/prefix2/a0/a1"))
        );

    }


    @Test
    public void givenAResourceWithNoPathParamsAtMethodLevelThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorWithNoPathAtMethodLevelResource.class.getMethod("getABeanWithNoRequestParameters");

        final Object[] methodUnderExecutionArguments = {};

        assertThat(
                simpleHttpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL),
                is(equalTo("http://microservice/callee/root/url/prefix1"))
        );
    }
}