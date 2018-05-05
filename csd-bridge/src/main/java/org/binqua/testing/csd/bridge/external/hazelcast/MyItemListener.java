package org.binqua.testing.csd.bridge.external.hazelcast;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

import org.binqua.testing.csd.bridge.external.Messages;
import org.binqua.testing.csd.bridge.external.ListBackedMessages;
import org.binqua.testing.csd.external.core.Message;

import java.util.ArrayList;
import java.util.List;

public class MyItemListener implements ItemListener<Message> {

    private List<Message> messages = new ArrayList<>();

    @Override
    public void itemAdded(ItemEvent<Message> itemEvent) {
        messages.add(itemEvent.getItem());
    }

    @Override
    public void itemRemoved(ItemEvent<Message> itemEvent) {
        throw new IllegalStateException("this method should not be called " + itemEvent);
    }

    public void clear() {
        messages.clear();
    }

    public Messages httpMessages() {
        return new ListBackedMessages(messages);
    }
}
