package org.binqua.testing.csd.client.mockito;

import org.binqua.testing.csd.external.MicroserviceAliasResolver;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class SimpleMockitoNotifierTest {

    private static final String MICROSERVICE_CALLEE_ROOT_URL = "http://microservice/callee/root/url";
    private static final SimpleSystemAlias CALLEE_SYSTEM_ALIAS = new SimpleSystemAlias("A_CALLEE_ALIAS");

    private final SystemAlias callerSystemAlias = mock(SystemAlias.class);
    private final HttpParametersFactory httpParametersFactory = mock(HttpParametersFactory.class);
    private final InterceptedInvocation interceptedInvocation = mock(InterceptedInvocation.class);
    private final MethodInvocationReport methodInvocationReport = mock(MethodInvocationReport.class);
    private final MicroserviceAliasResolver microserviceAliasResolver = mock(MicroserviceAliasResolver.class);
    private final MessageObserver messageObserver = mock(MessageObserver.class);
    private final HttpRequest httpRequestToBeNotified = mock(HttpRequest.class);

    private final SimpleMockitoNotifier simpleMockitoNotifier = new SimpleMockitoNotifier(callerSystemAlias, httpParametersFactory, messageObserver, microserviceAliasResolver);

    @Test
    public void givenAMethodWithNoPathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWithNoRequestParameters");

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);
        final Object[] methodUnderExecutionArguments = {};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        when(httpParametersFactory.newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1/prefix2"),
                new SimpleHeaders()
        )).thenReturn(httpRequestToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(messageObserver).notify(httpRequestToBeNotified);

    }

    @Test
    public void givenAMethodWithOnly1PathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParameters", RequestArgument.class);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);
        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0")};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        when(httpParametersFactory.newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1/prefix2/a0"),
                new SimpleHeaders()
        )).thenReturn(httpRequestToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(messageObserver).notify(httpRequestToBeNotified);

    }

    @Test
    public void givenAMethodWith1RequestParameterAndAnotherNoPathParamAsFirstParameterThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParametersAndAnotherNoPathParamAsFirstParameter", String.class, RequestArgument.class);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);

        final Object[] methodUnderExecutionArguments = {"aValue", new RequestArgument("a0")};

        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        when(httpParametersFactory.newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1/prefix2/a0"),
                new SimpleHeaders()
        )).thenReturn(httpRequestToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(messageObserver).notify(httpRequestToBeNotified);

    }

    @Test
    public void givenAMethodWith1RequestParametersAndAnotherNoPathParamAsLastParameterThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith1RequestParametersAndAnotherNoPathParamAsLastParameter", RequestArgument.class, String.class);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);
        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0"), "aValue"};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(httpParametersFactory).newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1/prefix2/a0"),
                new SimpleHeaders()
        );

    }

    @Test
    public void givenAMethodWithOnly2PathParamsThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWith2RequestParameters", RequestArgument.class, RequestArgument.class);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);
        final Object[] methodUnderExecutionArguments = {new RequestArgument("a0"), new RequestArgument("a1")};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        when(httpParametersFactory.newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1/prefix2/a0/a1"),
                new SimpleHeaders()
        )).thenReturn(httpRequestToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

    }

    @Test
    @Ignore
    public void postMethodCanBeExtracted() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("postABeanWith1RequestParameter", RequestArgument.class);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);

        SimpleMockitoNotifier simpleMockitoNotifier = new SimpleMockitoNotifier(callerSystemAlias, httpParametersFactory, messageObserver, microserviceAliasResolver);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

    }

    @Test
    public void givenAResourceWithNoPathParamsAtMethodLevelThenGetMethodCanBeExtractedAndUrlMappedToMethodArguments() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorWithNoPathAtMethodLevelResource.class.getMethod("getABeanWithNoRequestParameters");

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);
        final Object[] methodUnderExecutionArguments = {};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);
        when(microserviceAliasResolver.aliasOf(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(httpParametersFactory).newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HttpMessage.HttpMethod.GET,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(CALLEE_SYSTEM_ALIAS, "http://microservice/callee/root/url/prefix1"),
                new SimpleHeaders()
        );

    }


}