package org.binqua.testing.csd.client.mockito;

import java.lang.reflect.Method;

public interface RequestBodyExtractor {
    Object extractBodyFrom(Method aResourceMethodUnderExecution, Object[] methodUnderExecutionArguments);
}
