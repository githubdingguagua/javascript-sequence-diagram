package org.binqua.testing.csd.httpclient;

public class DescriptionResolverFactory {

    public DescriptionResolver request() {
        return JavaMethodNameDescriptionResolver.request();
    }

    public DescriptionResolver response() {
        return JavaMethodNameDescriptionResolver.response();
    }
}
