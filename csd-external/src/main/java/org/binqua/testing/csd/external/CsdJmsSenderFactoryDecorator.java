package org.binqua.testing.csd.external;

import uk.gov.dwp.universe.messaging.sender.JmsSenderFactory;

public interface CsdJmsSenderFactoryDecorator {

    JmsSenderFactory decorate(JmsSenderFactory jmsSenderFactory, String to);

}
