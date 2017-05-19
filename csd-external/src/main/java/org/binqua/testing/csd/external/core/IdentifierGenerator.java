package org.binqua.testing.csd.external.core;

public interface IdentifierGenerator {

    Identifier newIdentifier();

    Identifier newIdentifier(String externalIdentifier);
}
