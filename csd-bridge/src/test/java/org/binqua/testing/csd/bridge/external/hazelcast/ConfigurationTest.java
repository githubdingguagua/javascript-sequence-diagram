package org.binqua.testing.csd.bridge.external.hazelcast;

import com.google.common.collect.Lists;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.nio.serialization.Serializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import org.binqua.testing.csd.client.jms.SimpleJmsMessage;
import org.binqua.testing.csd.httpclient.SimpleHttpRequest;
import org.binqua.testing.csd.httpclient.SimpleHttpRequestStreamSerializer;
import org.binqua.testing.csd.httpclient.SimpleHttpResponse;
import org.binqua.testing.csd.httpclient.SimpleHttpResponseStreamSerializer;
import org.binqua.testing.csd.httpclient.SimpleJmsMessageStreamSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {

    private static final String CLUSTER_ID = "clusterId";
    private static final Integer PORT = 5720;

    @Test
    public void serverConfigIsCorrect() throws Exception {
        final Config actualConfiguration = new Configuration().serverConfig(CLUSTER_ID, PORT);

        assertThat(actualConfiguration.getGroupConfig().getName(), is("cucumber-sequence-diagram-" + CLUSTER_ID));
        assertThat(actualConfiguration.getNetworkConfig().getPort(), is(PORT));
        assertThat(actualConfiguration.getNetworkConfig().isPortAutoIncrement(), is(false));
        assertThat(actualConfiguration.getSerializationConfig().getSerializerConfigs(), hasSize(3));

        assertThatSerializationConfigIsCorrect(actualSerializerConfig(actualConfiguration));
    }

    @Test
    public void clientConfigIsCorrect() throws Exception {
        final ClientConfig actualConfiguration = new Configuration().clientConfig(CLUSTER_ID, PORT);

        assertThat(actualConfiguration.getGroupConfig().getName(), is("cucumber-sequence-diagram-" + CLUSTER_ID));
        assertThat(actualConfiguration.getNetworkConfig().getAddresses().get(0), is("127.0.0.1:"+5720));

        assertThat(actualConfiguration.getSerializationConfig().getSerializerConfigs(), hasSize(3));

        assertThatSerializationConfigIsCorrect(actualConfiguration.getSerializationConfig());
    }

    private void assertThatSerializationConfigIsCorrect(SerializationConfig serializationConfig) {
        final ArrayList<SerializerConfig> serializerConfigs = Lists.newArrayList(serializationConfig.getSerializerConfigs().iterator());
        final List<TypeImplementationPair> typeImplementationPairs = serializerConfigs.stream().map(TypeImplementationPair::new).collect(Collectors.toList());

        assertThat(typeImplementationPairs, contains(new TypeImplementationPair(SimpleHttpRequest.class,SimpleHttpRequestStreamSerializer.class),
                                                     new TypeImplementationPair(SimpleHttpResponse.class, SimpleHttpResponseStreamSerializer.class),
                                                     new TypeImplementationPair(SimpleJmsMessage.class, SimpleJmsMessageStreamSerializer.class)
        ));
    }

    private SerializationConfig actualSerializerConfig(Config actualConfiguration) {
        return actualConfiguration.getSerializationConfig();
    }

    class TypeImplementationPair {

        private final Class name;
        private final Class<? extends Serializer> implementation;

        public TypeImplementationPair(SerializerConfig serializerConfig) {
            this(serializerConfig.getTypeClass(), serializerConfig.getImplementation().getClass());
        }

        public TypeImplementationPair(Class name, Class<? extends Serializer> implementation) {
            this.name = name;
            this.implementation = implementation;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }
}