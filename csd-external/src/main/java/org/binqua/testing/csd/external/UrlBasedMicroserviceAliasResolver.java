package org.binqua.testing.csd.external;

public class UrlBasedMicroserviceAliasResolver implements MicroserviceAliasResolver {

    @Override
    public SystemAlias aliasOf(String microserviceCalleeRootUrl) {
        return new SimpleSystemAlias(microserviceCalleeRootUrl);
    }

}
