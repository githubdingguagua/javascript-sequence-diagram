package org.binqua.testing.csd.formatter.builder;

class SimpleHookListBuilderFactory implements HookListBuilderFactory {

    @Override
    public HookListBuilder createABuilder() {
        return new MapBasedHookListBuilder();
    }
}
