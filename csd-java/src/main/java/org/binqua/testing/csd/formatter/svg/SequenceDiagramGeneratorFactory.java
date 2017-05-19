package org.binqua.testing.csd.formatter.svg;

public class SequenceDiagramGeneratorFactory {

    private static final SequenceDiagramGenerator sequenceDiagramGenerator = new PlantumlSequenceDiagramGenerator(
            new ClickableSvgDecorator(),
            new SimpleFromPlantumSyntaxToSvg()
    );

    public static SequenceDiagramGenerator instance() {
        return sequenceDiagramGenerator;
    }

}
