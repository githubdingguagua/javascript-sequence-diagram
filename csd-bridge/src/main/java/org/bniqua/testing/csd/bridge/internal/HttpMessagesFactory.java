package org.bniqua.testing.csd.bridge.internal;

import org.bniqua.testing.csd.bridge.external.Messages;

public interface HttpMessagesFactory<T> {

    Messages<T> newHttpMessages();

}
