function DistancesCalculator() {

    this.sequenceDiagramWidth = function () {
        return 500
    }

    this.distanceFromFirstSystemAndSystem = function (systemBuilder) {
        if (systemBuilder.name() === "A"){
            return 0
        }
        return 100
    }

}

