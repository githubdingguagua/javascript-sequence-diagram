package org.binqua.testing.csd.formatter.svg;

import org.bniqua.testing.csd.bridge.external.Conversation;

public interface MessageDescriptionDictionaryFactory {

    MessageDescriptionDictionary createDictionary(Conversation conversation);

}
