package org.binqua.testing.csd.external;

import org.mockito.listeners.MethodInvocationReport;

public interface MockitoNotifier {

    void notify(MethodInvocationReport methodInvocationReport, String microserviceCalleeRootUrl);

}
