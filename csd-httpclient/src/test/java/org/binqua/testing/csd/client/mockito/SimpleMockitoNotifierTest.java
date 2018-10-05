package org.binqua.testing.csd.client.mockito;

import io.vavr.control.Try;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.*;
import org.binqua.testing.csd.httpclient.*;
import org.junit.Test;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;

public class SimpleMockitoNotifierTest {

    private static final String MICROSERVICE_CALLEE_ROOT_URL = "http://microservice/callee/root/url";
    private static final String CALLEE_SYSTEM_ALIAS_NAME = "A_CALLEE_ALIAS_NAME";
    private static final String CALLEE_HTTP_URL = "callee http url";
    private static final HttpMessage.HttpMethod HTTP_METHOD = HttpMessage.HttpMethod.POST;

    private final SystemAlias callerSystemAlias = new SimpleSystemAlias("CallerSystemAlias");
    private final HttpParametersFactory httpParametersFactory = mock(HttpParametersFactory.class, withSettings().verboseLogging());
    private final InterceptedInvocation interceptedInvocation = mock(InterceptedInvocation.class);
    private final MethodInvocationReport methodInvocationReport = mock(MethodInvocationReport.class);
    private final UrlAliasResolver urlAliasResolver = mock(UrlAliasResolver.class);
    private final MessageObserver messageObserver = mock(MessageObserver.class);
    private final HttpRequest httpRequestToBeNotified = aHttpRequestToBeNotified();

    private final HttpMessage httResponseToBeNotified = mock(HttpMessage.class, "response to be notified");

    private final HttpUriFactory httpUriFactory = mock(HttpUriFactory.class);
    private final HttpMethodExtractor httpMethodExtractor = mock(HttpMethodExtractor.class);
    private final RequestBodyExtractor requestBodyExtractor = mock(RequestBodyExtractor.class);
    private final Predicate runningCodeContextFinder = mock(Predicate.class);
    private final SimpleMockitoNotifier simpleMockitoNotifier = new SimpleMockitoNotifier(
            callerSystemAlias,
            httpParametersFactory,
            messageObserver,
            urlAliasResolver,
            httpUriFactory,
            httpMethodExtractor,
            requestBodyExtractor,
            runningCodeContextFinder
    );

    @Test
    public void givenWeAreRunningProductionCodeThenMessageIsNotifiedCorrectly() throws NoSuchMethodException {

        Method resourceMethodUnderExecution = ACollaboratorResource.class.getMethod("getABeanWithNoRequestParameters");

        when(runningCodeContextFinder.test(methodInvocationReport)).thenReturn(true);

        when(methodInvocationReport.getInvocation()).thenReturn(interceptedInvocation);

        final Object resourceMethodUnderExecutionReturnedValue = new Object();

        when(methodInvocationReport.getReturnedValue()).thenReturn(resourceMethodUnderExecutionReturnedValue);
        when(interceptedInvocation.getMethod()).thenReturn(resourceMethodUnderExecution);

        final Object[] methodUnderExecutionArguments = {};
        when(interceptedInvocation.getArguments()).thenReturn(methodUnderExecutionArguments);

        when(httpMethodExtractor.extractHttpMethodFrom(resourceMethodUnderExecution)).thenReturn(Try.success((HTTP_METHOD)));

        final Object theBodyOfTheRequest = new Object();
        when(requestBodyExtractor.extractBodyFrom(resourceMethodUnderExecution, methodUnderExecutionArguments)).thenReturn(theBodyOfTheRequest);

        when(urlAliasResolver.aliasFromUrl(MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_SYSTEM_ALIAS_NAME);

        when(httpUriFactory.createHttpUri(resourceMethodUnderExecution, methodUnderExecutionArguments, MICROSERVICE_CALLEE_ROOT_URL)).thenReturn(CALLEE_HTTP_URL);

        when(httpParametersFactory.newDirectHttpMethodCallRequest(
                new ExecutionContext(resourceMethodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                HTTP_METHOD,
                theBodyOfTheRequest,
                new SimpleHttpUri(new SimpleSystemAlias(CALLEE_SYSTEM_ALIAS_NAME), CALLEE_HTTP_URL))
        ).thenReturn(httpRequestToBeNotified);

        when(httpParametersFactory.newDirectHttpMethodCallResponse(
                new ExecutionContext(resourceMethodUnderExecution, methodUnderExecutionArguments),
                httpRequestToBeNotified,
                resourceMethodUnderExecutionReturnedValue)
        ).thenReturn(httResponseToBeNotified);

        simpleMockitoNotifier.notify(methodInvocationReport, MICROSERVICE_CALLEE_ROOT_URL);

        verify(messageObserver).notify(httpRequestToBeNotified);

        verify(messageObserver).notify(httResponseToBeNotified);

    }

    @Test
    public void givenWeAreRunningTestCodeThenMessageIsNotNotifiedAttAll(){

        when(runningCodeContextFinder.test(methodInvocationReport)).thenReturn(false);

        verifyZeroInteractions(messageObserver);

    }


    private HttpRequest aHttpRequestToBeNotified() {
        return new HttpRequest() {
            @Override
            public HttpUri uri() {
                return null;
            }

            @Override
            public SystemAlias callerSystem() {
                return null;
            }

            @Override
            public HttpMethod method() {
                return null;
            }

            @Override
            public SystemAlias from() {
                return null;
            }

            @Override
            public SystemAlias to() {
                return null;
            }

            @Override
            public MessageType messageType() {
                return null;
            }

            @Override
            public Body body() {
                return null;
            }

            @Override
            public String description() {
                return null;
            }

            @Override
            public String asJson() {
                return null;
            }

            @Override
            public Identifier identifier() {
                return null;
            }

            @Override
            public Identifier correlationIdentifier() {
                return null;
            }

            @Override
            public Headers headers() {
                return null;
            }
        };
    }
}