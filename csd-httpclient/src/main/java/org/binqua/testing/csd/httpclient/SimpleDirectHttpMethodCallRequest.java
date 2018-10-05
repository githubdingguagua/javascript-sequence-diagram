package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.MessageType;

import static org.binqua.testing.csd.external.core.MessageTypeEnumImpl.request;

public class SimpleDirectHttpMethodCallRequest implements HttpRequest {
    private final String asJson;
    private final String description;
    private final Body body;
    private final Identifier identifier;
    private final Identifier correlationId;
    private final SystemAlias callerSystem;
    private final HttpMethod httpMethod;
    private final HttpUri serverUri;
    private final Headers headers = new SimpleHeaders();

    public String getDescription() {
        return description;
    }

    public SimpleDirectHttpMethodCallRequest(String description, Body body, Identifier identifier, Identifier correlationId, SystemAlias callerSystem, HttpMethod httpMethod, HttpUri serverUri) {
        this.description = description;
        this.body = body;
        this.identifier = identifier;
        this.correlationId = correlationId;
        this.callerSystem = callerSystem;
        this.httpMethod = httpMethod;
        this.serverUri = serverUri;
        this.asJson = new Gson().toJson(createJsonRepresentation());
    }

    @Override
    public HttpUri uri() {
        return serverUri;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Identifier correlationIdentifier() {
        return correlationId;
    }

    @Override
    public SystemAlias callerSystem() {
        return callerSystem;
    }

    @Override
    public HttpMethod method() {
        return httpMethod;
    }

    @Override
    public Headers headers() {
        return new SimpleHeaders();
    }

    @Override
    public String toString() {
        return asJson;
    }

    @Override
    public SystemAlias from() {
        return callerSystem;
    }

    @Override
    public SystemAlias to() {
        return serverUri.alias();
    }

    @Override
    public Body body() {
        return body;
    }

    @Override
    public MessageType messageType() {
        return request;
    }

    @Override
    public String description() {
        return description;
    }

    private JsonElement createJsonRepresentation() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("from", callerSystem.name());
        jsonObject.add("to", serverUri.asJson());
        jsonObject.addProperty("type", messageType().name());
        jsonObject.add("headers", headers.asJson());
        jsonObject.add("body", body.asJson());
        jsonObject.addProperty("id", identifier.id());
        jsonObject.addProperty("correlationId", correlationId.id());
        jsonObject.addProperty("method", httpMethod.name());
        jsonObject.addProperty("description", description());
        return jsonObject;
    }

    @Override
    public String asJson() {
        return asJson;
    }

}
