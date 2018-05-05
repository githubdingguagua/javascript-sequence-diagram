package org.binqua.testing.csd.bridge.internal;

import org.binqua.testing.csd.bridge.external.Messages;

public interface HttpMessagesFactory<T> {

    Messages<T> newHttpMessages();

}
