package org.bniqua.testing.csd.bridge.external;

public interface ConversationSupport {

    Conversation conversation();

    void clearConversation();

    boolean hasConversation();

}
