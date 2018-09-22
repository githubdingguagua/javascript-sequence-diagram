package org.binqua.testing.csd.client.mockito;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;
import org.junit.Test;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class SimpleMockitoNotifierTest {

    private static final String MICROSERVICE_CALLEE_ROOT_URL = "http://microservice/callee/root/url";
    private static final String CALLEE_SYSTEM_ALIAS_NAME = "A_CALLEE_ALIAS_NAME";
    public static final String CALLEE_HTTP_URL = "callee http url";
    public static final HttpMessage.HttpMethod HTTP_METHOD = HttpMessage.HttpMethod.GET;

    private final SystemAlias callerSystemAlias = mock(SystemAlias.class);
    private final HttpParametersFactory httpParametersFactory = mock(HttpParametersFactory.class);
    private final InterceptedInvocation interceptedInvocation = mock(InterceptedInvocation.class);
    private final MethodInvocationReport methodInvocationReport = mock(MethodInvocationReport.class);
    private final UrlAliasResolver urlAliasResolver = mock(UrlAliasResolver.class);
    private final MessageObserver messageObserver = mock(MessageObserver.class);
    private final HttpRequest httpRequestToBeNotified = mock(HttpRequest.class);
    private final HttpUriFactory httpUriFactory = mock(HttpUriFactory.class);
    private final HttpMethodExtractor httpMethodExtractor = mock(HttpMethodExtractor.class);

    private final SimpleMockitoNotifier simpleMockitoNotifier = new SimpleMockitoNotifier(callerSystemAlias, httpParametersFactory, messageObserver, urlAliasResolver, httpUriFactory, httpMethodExtractor);

    @Test
    public void givenAResourceMethodThenMessageIsNotifiedCorrectly() throws NoSuchMethodException {

        Method aResourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWithNoRequestParameters");

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);
        when(interceptedInvocation.getMethod()).thenReturn(aResourceMethodUnderExecution);

        final Object[] methodUnderExecutionArguments = {};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);

        when(urlAliasResolver.aliasFromUrl(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS_NAME);
        when(httpUriFactory.createHttpUri(aResourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_HTTP_URL);
        when(httpMethodExtractor.extractHttpMethodFrom(aResourceMethodUnderExecution)).thenReturn(Try.success((HTTP_METHOD)));

        when(httpParametersFactory.newHttpRequest(
                new ExecutionContext(aResourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HTTP_METHOD,
                HttpClientParameters.HttpBody.empty(),
                new SimpleHttpUri(new SimpleSystemAlias(CALLEE_SYSTEM_ALIAS_NAME), CALLEE_HTTP_URL),
                new SimpleHeaders()
        )).thenReturn(httpRequestToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(messageObserver).notify(httpRequestToBeNotified);

    }

}