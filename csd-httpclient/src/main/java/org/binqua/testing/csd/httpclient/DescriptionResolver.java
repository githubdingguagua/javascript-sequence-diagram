package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.SystemAlias;

public interface DescriptionResolver {

    String resolve(ExecutionContext executionContext, HttpRequest.HttpMethod method, SystemAlias from, HttpUri to);

    String resolve(ExecutionContext executionContext, SystemAlias from, HttpUri to);

}
