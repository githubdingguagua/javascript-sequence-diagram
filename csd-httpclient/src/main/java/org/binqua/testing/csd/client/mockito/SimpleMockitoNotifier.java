package org.binqua.testing.csd.client.mockito;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;
import org.binqua.testing.csd.external.MicroserviceAliasResolver;
import org.binqua.testing.csd.external.MockitoNotifier;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;
import org.mockito.internal.invocation.InterceptedInvocation;
import org.mockito.listeners.MethodInvocationReport;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

public class SimpleMockitoNotifier implements MockitoNotifier {

    private SystemAlias callerSystemAlias;
    private HttpParametersFactory httpParametersFactory;
    private MessageObserver messageObserver;
    private MicroserviceAliasResolver microserviceAliasResolver;

    public SimpleMockitoNotifier(SystemAlias callerSystemAlias, HttpParametersFactory httpParametersFactory, MessageObserver messageObserver, MicroserviceAliasResolver microserviceAliasResolver) {
        this.callerSystemAlias = callerSystemAlias;
        this.httpParametersFactory = httpParametersFactory;
        this.messageObserver = messageObserver;
        this.microserviceAliasResolver = microserviceAliasResolver;
    }

    @Override
    public void notify(MethodInvocationReport methodInvocationReport, String microserviceCalleeRootUrl) {
        final InterceptedInvocation interceptedInvocation = (InterceptedInvocation) methodInvocationReport.getInvocation();

        final Method methodUnderExecution = interceptedInvocation.getMethod();
        final HttpMessage.HttpMethod httpMethod = httpMethodFrom(methodUnderExecution);

        final Option<String> pathValueOfResourceInvoked = List.of(methodUnderExecution.getDeclaringClass().getAnnotations())
                .filter(a -> a.annotationType() == Path.class)
                .map(annotation -> ((Path) annotation).value())
                .headOption();

        List<Tuple2<String, Integer>> methodPathParamIndexPositionPair = List.of(methodUnderExecution.getParameters())
                .zipWithIndex()
                .filter(this::parametersWithPathParamAnnotation)
                .map(p -> new Tuple2<>(p._1.getAnnotationsByType(PathParam.class)[0].value(), p._2));

        Object[] methodUnderExecutionArguments = interceptedInvocation.getArguments();
        Map<String, String> urlKeyValueMap = methodPathParamIndexPositionPair.map(t -> new Tuple2<>(t._1, methodUnderExecutionArguments[t._2].toString()))
                .toMap(stringStringTuple2 -> stringStringTuple2._1, stringStringTuple2 -> stringStringTuple2._2);

        final String urlWithPlaceHoldersReplaced = urlKeyValueMap.foldLeft(
                extractUrlWithPlaceholdersFrom(methodUnderExecution),
                (theNewUrlWithSomePlaceholders, stringStringTuple2) -> theNewUrlWithSomePlaceholders.replace("{" + stringStringTuple2._1 + "}", stringStringTuple2._2)
        );

        messageObserver.notify(httpParametersFactory.newHttpRequest(
                new ExecutionContext(methodUnderExecution, methodUnderExecutionArguments),
                callerSystemAlias,
                httpMethod,
                bodyFrom(methodUnderExecution, httpMethod),
                new SimpleHttpUri(microserviceAliasResolver.aliasOf(microserviceCalleeRootUrl), createFullUrl(pathValueOfResourceInvoked, microserviceCalleeRootUrl, urlWithPlaceHoldersReplaced)),
                new SimpleHeaders()

        ));

    }

    private String extractUrlWithPlaceholdersFrom(Method methodUnderExecution) {
        return Option.of(methodUnderExecution.getAnnotation(Path.class)).map(Path::value).getOrElse("");
    }

    private String createFullUrl(Option<String> classPathValue, String microserviceCalleeRootUrl, String urlWithoutPlaceHolders) {
        final Option<String> amededClassPathAnnotationValue = classPathValue.map(v -> v.startsWith("/") ? v.replaceFirst("/", "") : v);
        return StringUtils.removeEnd(microserviceCalleeRootUrl.concat("/").concat(amededClassPathAnnotationValue.getOrElse("").concat("/").concat(urlWithoutPlaceHolders)), "/");
    }

    private boolean parametersWithPathParamAnnotation(Tuple2<Parameter, Integer> p) {
        return List.of(p._1.getAnnotations()).map(Annotation::annotationType).exists(a -> a.getClass() == PathParam.class.getClass());
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
