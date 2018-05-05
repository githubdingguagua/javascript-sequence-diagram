package org.binqua.testing.csd.bridge.external;

import org.binqua.testing.csd.external.core.Message;

import java.util.ArrayList;
import java.util.List;

public class ListBackedMessages implements Messages<Message> {

    private List<Message> httpMessageList;

    public ListBackedMessages(List<Message> httpMessageList) {
        this.httpMessageList = httpMessageList;
    }

    @Override
    public void add(Message message) {
        httpMessageList.add(message);
    }

    @Override
    public List<Message> list() {
        return new ArrayList<>(httpMessageList);
    }
}
