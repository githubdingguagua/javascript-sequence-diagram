package org.binqua.testing.csd.client.mockito;

import io.vavr.control.Try;
import org.binqua.testing.csd.httpclient.HttpMessage;

import java.lang.reflect.Method;

public interface HttpMethodExtractor {
    Try<HttpMessage.HttpMethod> extractHttpMethodFrom(Method aResourceMethodUnderExecution);
}
