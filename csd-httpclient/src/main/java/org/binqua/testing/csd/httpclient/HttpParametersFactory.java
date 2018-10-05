package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Headers;

public interface HttpParametersFactory {

    HttpRequest newHttpRequest(ExecutionContext context,
                               SystemAlias callerSystem,
                               HttpMessage.HttpMethod method,
                               HttpClientParameters.HttpBody httpBody,
                               HttpUri httpUri,
                               Headers headers);

    HttpResponse newHttpResponse(ExecutionContext context,
                                 HttpRequest httpRequest,
                                 int status,
                                 HttpClientParameters.HttpBody httpBody,
                                 Headers headers);

    HttpRequest newDirectHttpMethodCallRequest(ExecutionContext context,
                                               SystemAlias callerSystem,
                                               HttpRequest.HttpMethod method,
                                               Object body,
                                               HttpUri httpUri);

    HttpMessage newDirectHttpMethodCallResponse(ExecutionContext context,
                                                HttpRequest httpRequest,
                                                Object body);

}
