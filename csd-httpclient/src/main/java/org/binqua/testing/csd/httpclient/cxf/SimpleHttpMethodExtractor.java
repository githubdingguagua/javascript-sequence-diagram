package org.binqua.testing.csd.httpclient.cxf;

import io.vavr.collection.List;
import io.vavr.control.Try;
import org.binqua.testing.csd.client.mockito.HttpMethodExtractor;
import org.binqua.testing.csd.httpclient.HttpMessage;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import java.lang.reflect.Method;
import java.util.Arrays;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class SimpleHttpMethodExtractor implements HttpMethodExtractor {
    private static final List<String> RECOGNISED_HTTP_METHOD = List.of(GET.class, POST.class, DELETE.class, PUT.class).map(Class::getSimpleName);

    @Override
    public Try<HttpMessage.HttpMethod> extractHttpMethodFrom(Method methodUnderExecution) {
        final List<String> onlyHttpMethods = List.ofAll(Arrays.stream(methodUnderExecution.getDeclaredAnnotations()))
                .map(annotation -> annotation.annotationType().getSimpleName())
                .filter(this::httpMethodIsOneOfThoseAccepted);

        return Match(onlyHttpMethods).of(
                Case($(List::isEmpty), noHttpMethod -> Try.failure(new IllegalStateException("adad"))),
                Case($(httpMethodList -> !httpMethodList.tail().isEmpty()), moreThanOneHttpMethod -> Try.failure(new IllegalStateException("adad"))),
                Case($(), httpMethodList -> Try.success(HttpMessage.HttpMethod.toHttpMethod(httpMethodList.head())))
        );
    }

    private boolean httpMethodIsOneOfThoseAccepted(String annotationSimpleName) {
        return RECOGNISED_HTTP_METHOD.contains(annotationSimpleName);
    }

}
