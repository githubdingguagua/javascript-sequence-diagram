function DistancesCalculator(configuration, systemsBuilder, conversationReport, maxDescriptionMessageLengthCalculator) {

    var that = this

    var distancesCacheMap = new Map()

    var firstSystemBuilder = systemsBuilder[0]

    function lastSystemBuilderIn(systemsBuilder) {
        return systemsBuilder[systemsBuilder.length - 1]
    }

    function nameOfTheSystemsBefore(systemName) {
        return conversationReport.systemNames.slice(0, conversationReport.systemNames.indexOf(systemName))
    }

    function systemPreviousOf(nameOfTheSystemToAnalyse) {
        var systemName = conversationReport.systemNames[conversationReport.systemNames.indexOf(nameOfTheSystemToAnalyse) - 1]

        return systemsBuilder.find(function (sb) {
            return sb.name() === systemName
        })
    }

    function systemBuilderWithName(systemName) {
        return systemsBuilder.find(function (sb) {
            return sb.name() === systemName
        })
    }

    function usingDefaultDistanceAsLastLegCalculateDistanceFromFirstSystemToSystem(nameOfTheSystemToAnalyse) {

        var systemPreviousOfSystemToAnalyse = systemPreviousOf(nameOfTheSystemToAnalyse)

        var distanceToPreviousSystem = that.leftBorderDistanceBetweenFirstSystemAndSystem(systemPreviousOfSystemToAnalyse.name())

        var value = distanceToPreviousSystem + systemPreviousOfSystemToAnalyse.width() + configuration.defaultDistanceBetweenSystems

        console.log(
            "New possible distance to " + nameOfTheSystemToAnalyse + " using default distance as last leg is " + value +
            " = (distance to " + systemPreviousOfSystemToAnalyse.name() + " = ) " + distanceToPreviousSystem +
            " + (width of " + systemPreviousOfSystemToAnalyse.name() + " = ) " + systemPreviousOfSystemToAnalyse.width() + " + (defaultDistanceBetweenSystems = ) " + configuration.defaultDistanceBetweenSystems
        )

        return value

    }

    this.sequenceDiagramWidth = function () {
        const lastSystemBuilder = lastSystemBuilderIn(systemsBuilder)

        return configuration.distanceFromVerticalBorder +
            this.leftBorderDistanceBetweenFirstSystemAndSystem(lastSystemBuilder.name()) +
            lastSystemBuilder.width() +
            configuration.distanceFromVerticalBorder
    }

    this.middlePointXCoordinateBetweenSystems = function (aSystem, anotherSystem) {

        var firstSystemIsOnTheLeft = function (aSystem, anotherSystem) {
            return conversationReport.systemNames.indexOf(aSystem) < conversationReport.systemNames.indexOf(anotherSystem)
        }

        var calculateMiddlePointBetweenSystems = function (systemOnTheLeft, systemOnTheRight) {
            return that.middlePointXCoordinateOfSystem(systemOnTheLeft) + (that.middlePointXCoordinateOfSystem(systemOnTheRight) - that.middlePointXCoordinateOfSystem(systemOnTheLeft)) / 2
        }

        return firstSystemIsOnTheLeft(aSystem, anotherSystem) ? calculateMiddlePointBetweenSystems(aSystem, anotherSystem) : calculateMiddlePointBetweenSystems(anotherSystem, aSystem)

    }

    this.middlePointXCoordinateOfSystem = function (nameOfTheSystem) {
        return configuration.distanceFromVerticalBorder + this.leftBorderDistanceBetweenFirstSystemAndSystem(nameOfTheSystem) + systemBuilderWithName(nameOfTheSystem).width() / 2
    }

    this.leftBorderDistanceBetweenFirstSystemAndSystem = function (nameOfTheSystemToAnalyse) {

        console.log("calculating left upper corner distance from " + firstSystemBuilder.name() + " to " + nameOfTheSystemToAnalyse)

        if (distancesCacheMap.has(nameOfTheSystemToAnalyse)) {
            var cachedDistance = distancesCacheMap.get(nameOfTheSystemToAnalyse);
            console.log("Distance to " + nameOfTheSystemToAnalyse + " found in cache " + cachedDistance)
            return cachedDistance
        }

        if (nameOfTheSystemToAnalyse === firstSystemBuilder.name()) {
            console.log("Distance to " + firstSystemBuilder.name() + " is 0")
            distancesCacheMap.set(nameOfTheSystemToAnalyse, 0)
            return 0
        }

        console.log("Analysing all systems before " + nameOfTheSystemToAnalyse + ", then [" + nameOfTheSystemsBefore(nameOfTheSystemToAnalyse).join(", ") + "] using max description length")

        var possibleDistances = nameOfTheSystemsBefore(nameOfTheSystemToAnalyse).reduce(function (possibleDistances, aPreviousSystemName) {

            var distanceToAPreviousSystem = that.leftBorderDistanceBetweenFirstSystemAndSystem(aPreviousSystemName)
            var aPreviousSystemHalfDistance = systemBuilderWithName(aPreviousSystemName).width() / 2
            var maxDescriptionLengthDistance = maxDescriptionMessageLengthCalculator.maxDescriptionLengthBetweenSystems(aPreviousSystemName, nameOfTheSystemToAnalyse)
            if (maxDescriptionLengthDistance) {
                var systemToAnalyseHalfDistanceDistance = systemBuilderWithName(nameOfTheSystemToAnalyse).width() / 2
                var possibleDistance = distanceToAPreviousSystem + aPreviousSystemHalfDistance + maxDescriptionLengthDistance - systemToAnalyseHalfDistanceDistance

                console.log(
                    "New possible distance [" + firstSystemBuilder.name() + "-" + nameOfTheSystemToAnalyse + "]" +
                    " = " + possibleDistance + " calculated as: distance " + firstSystemBuilder.name() + "-" + aPreviousSystemName + " = " + distanceToAPreviousSystem +
                    " + a previous system of " + nameOfTheSystemToAnalyse + " half distance (" + aPreviousSystemName + ") = " + aPreviousSystemHalfDistance +
                    " + max description length between " + aPreviousSystemName + "-" + nameOfTheSystemToAnalyse + " = " + maxDescriptionLengthDistance +
                    " - lastSystemHalfDistance (" + nameOfTheSystemToAnalyse + ") = " + systemToAnalyseHalfDistanceDistance)

                possibleDistances.push(possibleDistance)

                console.log("New possible distance to " + nameOfTheSystemToAnalyse + " is " + possibleDistance + " then all possibles distances are [" + possibleDistances.join(", ") + "] ")

            }
            return possibleDistances
        }, [])

        possibleDistances.push(usingDefaultDistanceAsLastLegCalculateDistanceFromFirstSystemToSystem(nameOfTheSystemToAnalyse))

        var distance = maxOf(possibleDistances)

        distancesCacheMap.set(nameOfTheSystemToAnalyse, distance)

        console.log("distance to " + nameOfTheSystemToAnalyse + " is the max cachedDistance in [" + possibleDistances.join(", ") + "] then " + distance)

        return distance
    }

    function maxOf(numArray) {
        return Math.max.apply(null, numArray);
    }

}

