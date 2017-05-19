package org.binqua.testing.csd.external;

public interface CsdNotifiersFactory {

    CsdNotifiers newNotifiersFor(SystemAlias calleeSystemAlias);

    CsdNotifiers newNotifiers();

}
