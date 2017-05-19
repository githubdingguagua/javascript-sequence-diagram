package org.binqua.testing.csd.httpclient.cxf;

import org.apache.commons.io.IOUtils;

import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.ExecutionContext;
import org.binqua.testing.csd.httpclient.HttpParametersFactory;
import org.binqua.testing.csd.httpclient.HttpRequest;
import org.binqua.testing.csd.httpclient.HttpResponse;
import org.binqua.testing.csd.httpclient.SimpleHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import static org.apache.cxf.phase.PhaseInterceptorChain.getCurrentMessage;
import static org.binqua.testing.csd.httpclient.HttpClientParameters.HttpBody;

class CsdResponseNotifier implements ClientResponseFilter {

    private HttpParametersFactory httpParametersFactory;
    private MessageObserver httpMessageObserver;

    CsdResponseNotifier(HttpParametersFactory httpParametersFactory, MessageObserver httpMessageObserver) {
        this.httpParametersFactory = httpParametersFactory;
        this.httpMessageObserver = httpMessageObserver;
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        final HttpResponse httpResponse = httpParametersFactory.newHttpResponse(ExecutionContext.currentExecutionContext(),
                                                                                httpRequestFromCurrentThread(),
                                                                                clientResponseContext.getStatus(),
                                                                                body(clientResponseContext),
                                                                                toCustomHeaders(clientResponseContext));
        httpMessageObserver.notify(httpResponse);
    }

    private HttpRequest httpRequestFromCurrentThread() {
        return (HttpRequest) getCurrentMessage().getExchange().get(HttpRequest.class.getName());
    }

    private HttpBody body(ClientResponseContext clientResponseContext) {
        if (clientResponseContext.hasEntity()) {
            StringWriter writer = new StringWriter();
            try {
                InputStream entityStream = clientResponseContext.getEntityStream();

                IOUtils.copy(entityStream, writer, Charset.defaultCharset());

                clientResponseContext.setEntityStream(new ByteArrayInputStream(writer.toString().getBytes()));
            } catch (IOException e) {
                return HttpBody.aBodyWith("No Body but exception!!! " + e.getMessage());
            }
            return HttpBody.aBodyWith(writer.toString());
        }
        return HttpBody.empty();
    }

    private Headers toCustomHeaders(ClientResponseContext headers) {
        final Headers simpleHeaders = toCustomHeaders(headers.getHeaders());
        if (headers.getLanguage() != null) {
            simpleHeaders.withKeyValue("language", headers.getLanguage().getDisplayName());
        }
        if (headers.getLanguage() != null) {
            simpleHeaders.withKeyValue("last modified", headers.getLastModified().toString());
        }
        return simpleHeaders;
    }

    private Headers toCustomHeaders(MultivaluedMap<String, String> stringHeaders) {
        Headers headers = new SimpleHeaders();
        for (String key : stringHeaders.keySet()) {
            headers.withKeyValue(key, String.valueOf(stringHeaders.getFirst(key)));
        }
        return headers;
    }
}
