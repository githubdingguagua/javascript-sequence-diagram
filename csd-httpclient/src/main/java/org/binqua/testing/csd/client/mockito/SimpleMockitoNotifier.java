package org.binqua.testing.csd.client.mockito;

import org.binqua.testing.csd.external.MockitoNotifier;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import javax.ws.rs.GET;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class SimpleMockitoNotifier implements MockitoNotifier {

    private SystemAlias callerSystemAlias;
    private HttpParametersFactory httpParametersFactory;
    private MessageObserver messageObserver;
    private UrlAliasResolver urlAliasResolver;
    private HttpUriFactory httpUriFactory;

    public SimpleMockitoNotifier(SystemAlias callerSystemAlias, HttpParametersFactory httpParametersFactory, MessageObserver messageObserver, UrlAliasResolver urlAliasResolver, HttpUriFactory httpUriFactory, HttpMethodExtractor httpMethodExtractor) {
        this.callerSystemAlias = callerSystemAlias;
        this.httpParametersFactory = httpParametersFactory;
        this.messageObserver = messageObserver;
        this.urlAliasResolver = urlAliasResolver;
        this.httpUriFactory = httpUriFactory;
    }

    @Override
    public void notify(MethodInvocationReport methodInvocationReport, String microserviceCalleeRootUrl) {
        final InterceptedInvocation interceptedInvocation = (InterceptedInvocation) methodInvocationReport.getInvocation();

        final Method methodUnderExecution = interceptedInvocation.getMethod();
        final HttpMessage.HttpMethod httpMethod = httpMethodFrom(methodUnderExecution);

        messageObserver.notify(httpParametersFactory.newHttpRequest(
                new ExecutionContext(methodUnderExecution, interceptedInvocation.getArguments()),
                callerSystemAlias,
                httpMethod,
                bodyFrom(methodUnderExecution, httpMethod),
                new SimpleHttpUri(
                        new SimpleSystemAlias(urlAliasResolver.aliasFromUrl(microserviceCalleeRootUrl)),
                        httpUriFactory.createHttpUri(methodUnderExecution, interceptedInvocation.getArguments(), microserviceCalleeRootUrl)
                ),
                new SimpleHeaders()

        ));

    }

    private HttpClientParameters.HttpBody bodyFrom(Method methodUnderExecution, HttpMessage.HttpMethod httpMethod) {
        if (httpMethod == HttpMessage.HttpMethod.GET) {
            return HttpClientParameters.HttpBody.empty();
        }
        return null;
    }

    private HttpMessage.HttpMethod httpMethodFrom(Method methodUnderExecution) {
        Annotation[] declaredAnnotations = methodUnderExecution.getDeclaredAnnotations();
        Optional<String> maybeAHttpMethod = Arrays.stream(declaredAnnotations)
                .map(annotation -> annotation.annotationType().getSimpleName())
                .filter(name -> name.equals(GET.class.getSimpleName()))
                .findFirst();

        return HttpMessage.HttpMethod.toHttpMethod("GET");
    }
}
