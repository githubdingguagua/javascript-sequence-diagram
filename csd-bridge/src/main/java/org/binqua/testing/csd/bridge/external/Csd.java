package org.binqua.testing.csd.bridge.external;

import com.hazelcast.core.IList;
import org.binqua.testing.csd.bridge.external.hazelcast.HazelcastSharedObjectFactory;
import org.binqua.testing.csd.external.CsdNotifiersFactory;
import org.binqua.testing.csd.external.MapUrlAliasResolver;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.YamlBasedUrlAliasResolver;
import org.binqua.testing.csd.external.core.Message;

import java.util.Map;

public class Csd {

    public static CsdNotifiersFactory notifiersFactory(UrlAliasResolver mapUrlAliasResolver) {
        return new CsdNotifiersFactoryImpl(mapUrlAliasResolver);
    }

    public static CsdNotifiersFactory notifiersFactory(String yamlFile) {
        return new CsdNotifiersFactoryImpl(new YamlBasedUrlAliasResolver(yamlFile));
    }

    public static CsdNotifiersFactory notifiersFactoryWithHazelcastSupportFor(String clusterIdentifier, int hazelcastPort, UrlAliasResolver urlAliasResolver) {
        final IList<? super Message> httpMessages = new HazelcastSharedObjectFactory().serverSide(clusterIdentifier, hazelcastPort);
        return new CsdNotifiersFactoryImpl(urlAliasResolver, clusterIdentifier, httpMessages::add);
    }

    public static CsdNotifiersFactory notifiersFactoryWithHazelcastSupportFor(String yamlFile, int hazelcastPort) {
        final YamlBasedUrlAliasResolver yamlBasedUrlAliasResolver = new YamlBasedUrlAliasResolver(yamlFile);
        final IList<? super Message> httpMessages = new HazelcastSharedObjectFactory().serverSide(yamlBasedUrlAliasResolver.serviceName(), hazelcastPort);
        return new CsdNotifiersFactoryImpl(yamlBasedUrlAliasResolver, yamlBasedUrlAliasResolver.serviceName(), httpMessages::add);
    }

    public static CsdNotifiersFactory notifiersFactoryWithHazelcastSupportFor(String clusterIdentifier, int hazelcastPort, Map<String, String> fromUrlToAliasMap) {
        return notifiersFactoryWithHazelcastSupportFor(clusterIdentifier, hazelcastPort, new MapUrlAliasResolver(fromUrlToAliasMap));
    }

}
