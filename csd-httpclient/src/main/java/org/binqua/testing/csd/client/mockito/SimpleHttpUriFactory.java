package org.binqua.testing.csd.client.mockito;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SimpleHttpUriFactory implements HttpUriFactory {

    @Override
    public String createHttpUri(Method methodUnderExecution, Object[] methodUnderExecutionArguments, String microserviceCalleeRootUrl) {

        final Option<String> pathValueOfResourceClassInvoked = List.of(methodUnderExecution.getDeclaringClass().getAnnotations())
                .filter(a -> a.annotationType() == Path.class)
                .map(annotation -> ((Path) annotation).value())
                .headOption();

        final List<Tuple2<String, Integer>> methodPathParamIndexPositionPair = List.of(methodUnderExecution.getParameters())
                .zipWithIndex()
                .filter(this::parametersWithPathParamAnnotation)
                .map(p -> new Tuple2<>(p._1.getAnnotationsByType(PathParam.class)[0].value(), p._2));

        final Map<String, String> urlKeyValueMap = methodPathParamIndexPositionPair.map(t -> new Tuple2<>(t._1, methodUnderExecutionArguments[t._2].toString()))
                .toMap(stringStringTuple2 -> stringStringTuple2._1, stringStringTuple2 -> stringStringTuple2._2);

        final String urlWithPlaceHoldersReplaced = urlKeyValueMap.foldLeft(
                extractUrlWithPlaceholdersFrom(methodUnderExecution),
                (theNewUrlWithSomePlaceholders, stringStringTuple2) -> theNewUrlWithSomePlaceholders.replace("{" + stringStringTuple2._1 + "}", stringStringTuple2._2)
        );

        return createFullUrl(pathValueOfResourceClassInvoked, microserviceCalleeRootUrl, urlWithPlaceHoldersReplaced);
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
}
