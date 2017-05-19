package org.binqua.testing.csd.formatter.report.assets;

public class MultipleAssetsWriter implements AssetsWriter {

    private AssetsWriter firstWriter;
    private AssetsWriter secondWriter;

    public MultipleAssetsWriter(AssetsWriter firstWriter, AssetsWriter secondWriter) {
        this.firstWriter = firstWriter;
        this.secondWriter = secondWriter;
    }

    @Override
    public void write() {
        firstWriter.write();
        secondWriter.write();
    }

}
