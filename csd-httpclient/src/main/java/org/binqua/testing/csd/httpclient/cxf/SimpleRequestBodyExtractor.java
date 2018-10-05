package org.binqua.testing.csd.httpclient.cxf;

import org.binqua.testing.csd.client.mockito.RequestBodyExtractor;

import java.lang.reflect.Method;

public class SimpleRequestBodyExtractor implements RequestBodyExtractor {
    @Override
    public Object extractBodyFrom(Method aResourceMethodUnderExecution, Object[] methodUnderExecutionArguments) {
        return null;
    }
}
