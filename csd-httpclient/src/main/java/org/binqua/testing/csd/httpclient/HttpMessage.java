package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.core.Message;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public interface HttpMessage extends Message {

    enum HttpMethod {
        DELETE,
        GET,
        POST,
        PUT;

        public static HttpMethod toHttpMethod(String method) {
            return asList(values()).stream().filter(m -> method.toUpperCase().equals(m.toString())).findFirst().orElseThrow(
                () -> new RuntimeException(format("Cannot find HTTP method matching %s in %s", method, asList(values()).stream().map(Enum::toString).collect(joining(", ")))));
        }

    }

}
