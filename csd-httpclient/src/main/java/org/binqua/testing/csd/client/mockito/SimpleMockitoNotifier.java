package org.binqua.testing.csd.client.mockito;

import org.binqua.testing.csd.external.MockitoNotifier;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import static io.vavr.API.*;
import static io.vavr.Predicates.is;

public class SimpleMockitoNotifier implements MockitoNotifier {

    private SystemAlias callerSystemAlias;
    private HttpParametersFactory httpParametersFactory;
    private MessageObserver messageObserver;
    private UrlAliasResolver urlAliasResolver;
    private HttpUriFactory httpUriFactory;
    private HttpMethodExtractor httpMethodExtractor;
    private RequestBodyExtractor requestBodyExtractor;
    private Predicate<MethodInvocationReport> productionCodeRunningPredicate;

    public SimpleMockitoNotifier(SystemAlias callerSystemAlias,
                                 HttpParametersFactory httpParametersFactory,
                                 MessageObserver messageObserver,
                                 UrlAliasResolver urlAliasResolver,
                                 HttpUriFactory httpUriFactory,
                                 HttpMethodExtractor httpMethodExtractor,
                                 RequestBodyExtractor requestBodyExtractor,
                                 Predicate<MethodInvocationReport> productionCodeRunningPredicate) {
        this.callerSystemAlias = callerSystemAlias;
        this.httpParametersFactory = httpParametersFactory;
        this.messageObserver = messageObserver;
        this.urlAliasResolver = urlAliasResolver;
        this.httpUriFactory = httpUriFactory;
        this.httpMethodExtractor = httpMethodExtractor;
        this.requestBodyExtractor = requestBodyExtractor;
        this.productionCodeRunningPredicate = productionCodeRunningPredicate;
    }

    @Override
    public void notify(MethodInvocationReport methodInvocationReport, String microserviceCalleeRootUrl) {

        final boolean isInsideProductionCode = productionCodeRunningPredicate.test(methodInvocationReport);

        Match(isInsideProductionCode).of(
                Case($(is(true)), o -> run(() -> notifyRequestAndResponse(methodInvocationReport, microserviceCalleeRootUrl))),
                Case($(is(false)), o -> run(() -> {
                }))
        );

    }

    private void notifyRequestAndResponse(MethodInvocationReport methodInvocationReport, String microserviceCalleeRootUrl) {

        final InterceptedInvocation interceptedInvocation = (InterceptedInvocation) methodInvocationReport.getInvocation();

        final Method methodUnderExecution = interceptedInvocation.getMethod();

        final Object[] methodUnderExecutionArguments = interceptedInvocation.getArguments();

        final SimpleHttpUri calleeHttpUri = new SimpleHttpUri(
                new SimpleSystemAlias(urlAliasResolver.aliasFromUrl(microserviceCalleeRootUrl)),
                httpUriFactory.createHttpUri(methodUnderExecution, methodUnderExecutionArguments, microserviceCalleeRootUrl)
        );

        final HttpMessage.HttpMethod httpMethod = httpMethodExtractor.extractHttpMethodFrom(methodUnderExecution).get();

        final ExecutionContext executionContext = new ExecutionContext(methodUnderExecution, methodUnderExecutionArguments);

        final HttpRequest requestToBeNotified = httpParametersFactory.newDirectHttpMethodCallRequest(
                executionContext,
                callerSystemAlias,
                httpMethod,
                requestBodyExtractor.extractBodyFrom(methodUnderExecution, methodUnderExecutionArguments),
                calleeHttpUri
        );

        final Object returnedValue = methodInvocationReport.getReturnedValue();
        final HttpMessage responseToBeNotified = httpParametersFactory.newDirectHttpMethodCallResponse(
                executionContext,
                requestToBeNotified,
                returnedValue
        );

        messageObserver.notify(requestToBeNotified);
        messageObserver.notify(responseToBeNotified);
    }


}
