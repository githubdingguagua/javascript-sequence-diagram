function SystemBuilder(configuration, systemName, sequenceDiagramId, textLengthCalculator) {

    var textSize = textLengthCalculator.sizeOf(systemName)

    var width = configuration.marginFromHorizontalBorder * 2 + textSize.width

    var height = configuration.marginFromVerticalBorder * 2 + textSize.height

    function groupId() {
        return customisedIdWith("group")
    }

    function customisedIdWith(toBeDecorated) {
        return "systemBuilder_" + sequenceDiagramId + "_" + toBeDecorated
    }

    function rectId() {
        return customisedIdWith("rect")
    }

    function textId() {
        return customisedIdWith("text")
    }

    this.selector = function () {
        return "[id='" + groupId() + "']"
    }

    this.name = function () {
        return systemName
    }

    this.width = function () {
        return width
    }

    this.height = function () {
        return height
    }

    this.draw = function (svgContainer) {
        var mainContainer = svgContainer.append("g").attr("id", groupId())

        mainContainer.append("rect")
            .attr("id", rectId())
            .attr("x", 0)
            .attr("y", 0)
            .attr("width", width)
            .attr("height", height)
            .attr("fill", "#FEFECE")
            .attr("style", "stroke: #A80036; stroke-width: 1.5")

        mainContainer.append("text")
            .attr("id", textId())
            .attr("font-family", "sans-serif")
            .attr("font-size", "14px")
            .attr("fill", "#000000")
            .text(systemName)
            .attr("text-anchor", "middle")
            .attr("alignment-baseline", "middle")
            .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")")
    }

}

