package org.binqua.testing.csd.external.core;

import org.binqua.testing.csd.external.SystemAlias;

import java.util.Optional;

public interface JmsMessageFactory {

    Message createAJmsMessage(SystemAlias from, SystemAlias to, javax.jms.Message message, Optional<String> anOptionalExceptionText);

}
