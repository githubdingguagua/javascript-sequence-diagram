package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.*;

public class SimpleHttpParametersFactory implements HttpParametersFactory {

    private final DescriptionResolverFactory descriptionResolverFactory;
    private final BodyFactory bodyFactory;
    private final IdentifierGenerator identifierGenerator;

    public SimpleHttpParametersFactory(IdentifierGenerator identifierGenerator,
                                       DescriptionResolverFactory descriptionResolverFactory,
                                       BodyFactory bodyFactory) {
        this.identifierGenerator = identifierGenerator;
        this.descriptionResolverFactory = descriptionResolverFactory;
        this.bodyFactory = bodyFactory;
    }

    @Override
    public HttpRequest newHttpRequest(ExecutionContext context,
                                      SystemAlias callerSystem,
                                      HttpRequest.HttpMethod method,
                                      HttpClientParameters.HttpBody httpBody,
                                      HttpUri httpUri,
                                      Headers headers) {
        final Identifier correlationId = identifierGenerator.newIdentifier();
        final Identifier requestIdentifier = aPrefixedIdentifier("request", correlationId);
        final String description = descriptionResolverFactory.request().resolve(context, method, callerSystem, httpUri);
        final Body messageBody = bodyFactory.createAMessageBody(httpBody.value(), headers);
        return new SimpleHttpRequest(description,
                messageBody,
                requestIdentifier,
                correlationId,
                callerSystem,
                method,
                httpUri,
                headers
        );
    }

    @Override
    public HttpResponse newHttpResponse(ExecutionContext context, HttpRequest httpRequest, int status, HttpClientParameters.HttpBody httpBody, Headers headers) {
        final Identifier responseIdentifier = aPrefixedIdentifier("response", identifierGenerator.newIdentifier());
        final String description = descriptionResolverFactory.response().resolve(context, httpRequest.from(), httpRequest.uri());
        final Body messageBody = bodyFactory.createAMessageBody(httpBody.value(), headers);
        return new SimpleHttpResponse(description,
                messageBody,
                responseIdentifier,
                status,
                headers,
                new SimpleSystemAlias(httpRequest.uri().alias().name()),
                new SimpleSystemAlias(httpRequest.callerSystem().name()),
                httpRequest.correlationIdentifier()
        );
    }

    @Override
    public HttpRequest newDirectHttpMethodCallRequest(ExecutionContext context,
                                      SystemAlias callerSystem,
                                      HttpRequest.HttpMethod method,
                                      Object body,
                                      HttpUri httpUri) {
        final Identifier correlationId = identifierGenerator.newIdentifier();
        final Identifier requestIdentifier = aPrefixedIdentifier("request", correlationId);
        final String description = descriptionResolverFactory.request().resolve(context, method, callerSystem, httpUri);
        final Body messageBody = bodyFactory.createAJsonMessageBody(body);
        return new SimpleDirectHttpMethodCallRequest(description,
                messageBody,
                requestIdentifier,
                correlationId,
                callerSystem,
                method,
                httpUri
        );
    }

    @Override
    public HttpMessage newDirectHttpMethodCallResponse(ExecutionContext context, HttpRequest httpRequest, Object body) {
        final Identifier responseIdentifier = aPrefixedIdentifier("response", identifierGenerator.newIdentifier());
        final String description = descriptionResolverFactory.response().resolve(context, httpRequest.from(), httpRequest.uri());
        final Body messageBody = bodyFactory.createAJsonMessageBody(body);
        return new SimpleDirectHttpMethodCallResponse(description,
                messageBody,
                responseIdentifier,
                new SimpleSystemAlias(httpRequest.uri().alias().name()),
                new SimpleSystemAlias(httpRequest.callerSystem().name()),
                httpRequest.correlationIdentifier()
        );
    }


    private Identifier aPrefixedIdentifier(final String prefix, final Identifier correlationId) {
        return new SimpleIdentifier(prefix + "-" + correlationId.id());
    }

}

