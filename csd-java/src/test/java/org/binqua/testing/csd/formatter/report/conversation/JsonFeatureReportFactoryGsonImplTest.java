package org.binqua.testing.csd.formatter.report.conversation;

import org.junit.Test;
import org.mockito.Mockito;
import org.binqua.testing.csd.cucumberreports.model.Feature;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class JsonFeatureReportFactoryGsonImplTest {

    @Test
    public void createJsonFeatureReportCreatesAGsonFeatureReportInstance() throws Exception {

        final Feature feature = Mockito.mock(Feature.class);

        assertThat(new JsonFeatureReportFactoryGsonImpl().createJsonFeatureReport(feature), instanceOf(GsonFeatureReport.class));

    }
}