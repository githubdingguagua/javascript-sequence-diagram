package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.SystemAlias;

public interface HttpRequest extends HttpMessage {

    HttpUri uri();

    SystemAlias callerSystem();

    HttpMessage.HttpMethod method();

}
