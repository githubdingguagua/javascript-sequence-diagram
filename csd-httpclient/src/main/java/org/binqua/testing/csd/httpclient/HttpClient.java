package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.core.Headers;

public interface HttpClient {

    <T> T post(HttpClientParameters.HttpBody httpBody, HttpClientParameters.UriPath uriPath, Headers headers, HttpClientParameters.ReturningInstance<T> responseType);

    <T> T get(HttpClientParameters.ReturningInstance<T> responseType, HttpClientParameters.UriPath uriPath, Headers headers);

}