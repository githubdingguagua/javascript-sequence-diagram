package org.binqua.testing.csd.external;

public interface MicroserviceAliasResolver {

    SystemAlias aliasOf(String microserviceCalleeRootUrl);
}
