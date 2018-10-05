package org.binqua.testing.csd.bridge.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binqua.testing.csd.external.CsdNotifiers;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.YamlBasedUrlAliasResolver;

import java.util.Optional;

public class Csd {

    private static Optional<CsdNotifiers> CSD_NOTIFIER;

    public static Optional<CsdNotifiers> instance() {
        return CSD_NOTIFIER;
    }

    public static CsdNotifiers initNotifiers(String yamlFile, ObjectMapper objectMapper, SystemAlias callerSystemAlias) {
        CsdNotifiersFactoryImpl csdNotifiersFactory = new CsdNotifiersFactoryImpl(new YamlBasedUrlAliasResolver(yamlFile), objectMapper);
        CSD_NOTIFIER = Optional.of(csdNotifiersFactory.newNotifiersFor(callerSystemAlias));
        return CSD_NOTIFIER.get();
    }
}
