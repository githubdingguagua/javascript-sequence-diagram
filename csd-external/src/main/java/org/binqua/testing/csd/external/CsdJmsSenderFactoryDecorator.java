package org.binqua.testing.csd.external;

import org.binqua.blabla.messaging.sender.JmsSenderFactory;

public interface CsdJmsSenderFactoryDecorator {

    JmsSenderFactory decorate(JmsSenderFactory jmsSenderFactory, String to);

}
