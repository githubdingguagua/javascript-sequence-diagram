package org.bniqua.testing.csd.bridge.external;

import org.bniqua.testing.csd.bridge.internal.HttpMessagesFactory;
import org.binqua.testing.csd.external.core.Message;

import java.util.ArrayList;

public class ArrayListHttpMessagesFactory implements HttpMessagesFactory<Message> {

    @Override
    public Messages<Message> newHttpMessages() {
        return new ListBackedMessages(new ArrayList<>());
    }

}
