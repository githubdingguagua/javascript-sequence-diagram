package org.binqua.testing.csd.client.mockito;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;

public class SimpleHttpUriFactory implements HttpUriFactory {

    @Override
    public String createHttpUri(Method methodUnderExecution, Object[] methodUnderExecutionArguments, String microserviceCalleeRootUrl) {

        final Option<String> pathValueOfResourceClassInvoked = List.of(methodUnderExecution.getDeclaringClass().getAnnotations())
                .filter(a -> a.annotationType() == Path.class)
                .map(annotation -> ((Path) annotation).value())
                .headOption();

        final Tuple2<List<Tuple2<Parameter, Integer>>, List<Tuple2<Parameter, Integer>>> pathParamsAndGetParamsPartition = List.of(methodUnderExecution.getParameters())
                .zipWithIndex()
                .filter(p -> parametersWithRightAnnotation(p, PathParam.class, QueryParam.class))
                .partition(p -> parametersWithRightAnnotation(p, PathParam.class));

        final List<Tuple2<String, Integer>> pathParamValueAndPositionPairs = pathParamsAndGetParamsPartition._1
                .map(p -> new Tuple2<>(p._1.getAnnotationsByType(PathParam.class)[0].value(), p._2));

        final List<Tuple2<String, Integer>> getParamValueAndPositionPairs = pathParamsAndGetParamsPartition._2
                .map(p -> new Tuple2<>(p._1.getAnnotationsByType(QueryParam.class)[0].value(), p._2));

        final Map<String, String> pathParamKeyValueMap = toUrlKeyValueMap(methodUnderExecutionArguments, pathParamValueAndPositionPairs);

        final String urlWithPathParamsPlaceHoldersReplaced = pathParamKeyValueMap.foldLeft(
                extractUrlWithPlaceholdersFrom(methodUnderExecution),
                (theNewUrlWithSomePlaceholders, stringStringTuple2) -> theNewUrlWithSomePlaceholders.replace("{" + stringStringTuple2._1 + "}", stringStringTuple2._2)
        );

        final Map<String, String> getParamKeyValueMap = toUrlKeyValueMap(methodUnderExecutionArguments, getParamValueAndPositionPairs);

        final String queryParametersUrlPart = getParamKeyValueMap
                .foldLeft(
                        List.<String>empty(),
                        (theNewUrlWithSomePlaceholders, getParamNameValueTuple) -> theNewUrlWithSomePlaceholders.push(getParamNameValueTuple._1().concat("=").concat(getParamNameValueTuple._2()))
                )
                .mkString("&");

        return createFullUrl(pathValueOfResourceClassInvoked, microserviceCalleeRootUrl, urlWithPathParamsPlaceHoldersReplaced, queryParametersUrlPart);
    }

    private Map<String, String> toUrlKeyValueMap(Object[] methodUnderExecutionArguments, List<Tuple2<String, Integer>> pathParamValueAndPositionPairs) {
        return pathParamValueAndPositionPairs.map(t -> new Tuple2<>(t._1, methodUnderExecutionArguments[t._2].toString()))
                .toSortedMap(Comparator.reverseOrder(), stringStringTuple2 -> stringStringTuple2._1, stringStringTuple2 -> stringStringTuple2._2);
    }

    private String extractUrlWithPlaceholdersFrom(Method methodUnderExecution) {
        return Option.of(methodUnderExecution.getAnnotation(Path.class)).map(Path::value).getOrElse("");
    }

    private String createFullUrl(Option<String> classPathValue, String microserviceCalleeRootUrl, String urlWithoutPlaceHolders, String queryParametersUrlPart) {
        final Option<String> amendedClassPathAnnotationValue = classPathValue.map(v -> v.startsWith("/") ? v.replaceFirst("/", "") : v);
        return StringUtils.removeEnd(microserviceCalleeRootUrl.concat("/").concat(amendedClassPathAnnotationValue.getOrElse("").concat("/").concat(urlWithoutPlaceHolders)), "/")
                .concat(queryParametersUrlPart.isEmpty() ? "" : "?".concat(queryParametersUrlPart));
    }

    private boolean parametersWithRightAnnotation(Tuple2<Parameter, Integer> p, Class<?>... pathParamClass) {
        return List.of(p._1.getAnnotations())
                .map(Annotation::annotationType)
                .exists(actualAnnotation -> List.ofAll(Arrays.stream(pathParamClass)).exists(paramClass -> paramClass == actualAnnotation));
    }
}
