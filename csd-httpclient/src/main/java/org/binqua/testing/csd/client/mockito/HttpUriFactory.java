package org.binqua.testing.csd.client.mockito;

import java.lang.reflect.Method;

public interface HttpUriFactory {

    String createHttpUri(Method aResourceMethodUnderExecution, Object[] methodUnderExecutionArguments, String microserviceCalleeRootUrl);

}
