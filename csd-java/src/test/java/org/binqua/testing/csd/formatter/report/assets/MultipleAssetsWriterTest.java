package org.binqua.testing.csd.formatter.report.assets;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MultipleAssetsWriterTest {

    @Test
    public void writeDelegatesToEachWriters() throws Exception {

        AssetsWriter firstWriter = mock(AssetsWriter.class);
        AssetsWriter secondWriter = mock(AssetsWriter.class);

        MultipleAssetsWriter underTest = new MultipleAssetsWriter(firstWriter, secondWriter);

        underTest.write();

        verify(firstWriter).write();
        verify(secondWriter).write();
    }

}