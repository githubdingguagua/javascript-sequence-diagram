package org.binqua.testing.csd.external.core;

import org.binqua.testing.csd.external.SystemAlias;

public interface Message {

    SystemAlias from();

    SystemAlias to();

    MessageType messageType();

    Body body();

    String description();

    String asJson();

    Identifier identifier();

    Identifier correlationIdentifier();

    Headers headers();

}
