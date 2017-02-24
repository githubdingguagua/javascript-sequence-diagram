function TextLengthCalculator(svgContainer) {

    this.sizeOf = function (text) {
        var size
        svgContainer.append('g')
            .selectAll('.dummyText')
            .data([text])
            .enter()
            .append("text")
            .attr("font-family", "sans-serif")
            .attr("font-size", "14px")
            .text(function (d) {
                return d
            })
            .each(function (d) {
                var thisBox = this.getBBox()
                size = {"width": thisBox.width, "height": thisBox.height}
                this.remove()
            })
        return size

    }

}

