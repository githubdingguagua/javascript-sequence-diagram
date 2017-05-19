package org.binqua.testing.csd.httpclient.cxf;

import com.google.gson.Gson;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.*;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedInputStream;
import java.io.IOException;

import static org.binqua.testing.csd.httpclient.HttpClientParameters.HttpBody;

class CsdRequestNotifier implements ClientRequestFilter {

    private SystemAlias thisSystemAlias;
    private HttpParametersFactory httpParametersFactory;
    private UrlAliasResolver urlAliasResolver;
    private MessageObserver messageObserver;

    CsdRequestNotifier(SystemAlias thisSystemAlias, HttpParametersFactory httpParametersFactory, UrlAliasResolver urlAliasResolver, MessageObserver messageObserver) {
        this.thisSystemAlias = thisSystemAlias;
        this.httpParametersFactory = httpParametersFactory;
        this.urlAliasResolver = urlAliasResolver;
        this.messageObserver = messageObserver;
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        final HttpRequest httpRequest = httpParametersFactory.newHttpRequest(
                ExecutionContext.currentExecutionContext(),
                thisSystemAlias,
                httpMethod(clientRequestContext),
                httpBody(clientRequestContext),
                calleeServerUri(clientRequestContext),
                httpHeaders(clientRequestContext)
        );
        Message currentMessage = PhaseInterceptorChain.getCurrentMessage();
        currentMessage.getExchange().put(HttpRequest.class.getName(), httpRequest);
        messageObserver.notify(httpRequest);
    }

    private Headers httpHeaders(ClientRequestContext clientRequestContext) {
        return toCustomHeaders(clientRequestContext.getHeaders());
    }

    private HttpUri calleeServerUri(ClientRequestContext clientRequestContext) {
        final String uri = clientRequestContext.getUri().toString();
        return new SimpleHttpUri(calleeSystem(uri), uri);
    }

    private HttpBody httpBody(ClientRequestContext clientRequestContext) {
        if (clientRequestContext.hasEntity()) {
            clientRequestContext.getAcceptableMediaTypes();

            Object entity = clientRequestContext.getEntity();

            if (entity.getClass() == BufferedInputStream.class) {
                return HttpBody.empty();
            }

            return HttpBody.aBodyWith(new Gson().toJson(entity));
        }
        return HttpBody.empty();
    }

    private HttpMessage.HttpMethod httpMethod(ClientRequestContext clientRequestContext) {
        return HttpMessage.HttpMethod.toHttpMethod(clientRequestContext.getMethod());
    }

    private SystemAlias calleeSystem(final String url) {
        return new SimpleSystemAlias(urlAliasResolver.aliasFromUrl(url));
    }

    private Headers toCustomHeaders(MultivaluedMap<String, Object> stringHeaders) {
        Headers headers = new SimpleHeaders();
        for (String key : stringHeaders.keySet()) {
            headers.withKeyValue(key, String.valueOf(stringHeaders.getFirst(key)));
        }
        return headers;
    }
}
