var configuration = {
    "defaultDistanceBetweenSystems": 50,
    "distanceFromHorizontalBorder": 10,
}

function SystemBuilder(name, width) {
    this.width = function () {
        return width
    }
    this.name = function () {
        return name
    }
}

QUnit.test(
    "given 3 systems A B C with longest message description AC less the default distance and " +
    "with longest message description BC less then the default one and " +
    "with longest message description AB less the default distance " +
    "then system position depends on default distance and no from message description length",
    function (assert) {

        var defaultDistanceBetweenABMiddlePoints = 50 + configuration.defaultDistanceBetweenSystems + 10

        var defaultDistanceBetweenACMiddlePoints = 50 + configuration.defaultDistanceBetweenSystems + 10 + 10 + configuration.defaultDistanceBetweenSystems + 20

        var defaultDistanceBetweenBCMiddlePoints = 10 + configuration.defaultDistanceBetweenSystems + 20

        function MaxDescriptionMessageLengthCalculator() {

            this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
                if (firstSystemName === "A" && secondSystemName === "C") {
                    return defaultDistanceBetweenACMiddlePoints - 1
                }
                if (firstSystemName === "B" && secondSystemName === "C") {
                    return defaultDistanceBetweenBCMiddlePoints - 1
                }
                if (firstSystemName === "A" && secondSystemName === "B") {
                    return defaultDistanceBetweenABMiddlePoints - 1
                }
                throw "Cannot find distance for " + firstSystemName + " and " + secondSystemName
            }
        }

        var conversationReport = {
            "systemNames": ["A", "B", "C"]
        }

        var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

        var distancesCalculator = new DistancesCalculator(
            configuration, systemsBuilder, conversationReport, new MaxDescriptionMessageLengthCalculator()
        )

        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("A"), 0, "distance from A to A")
        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("B"), 100 + configuration.defaultDistanceBetweenSystems, "distance from A to B is equals to the default one")
        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("C"), 100 + configuration.defaultDistanceBetweenSystems + 20 + configuration.defaultDistanceBetweenSystems, "distance from A to C is equals to the default one")

    })

QUnit.test(
    "given 3 systems A B C with distance AB equals to the default one because longest description AB is less the default distance, " +
    "and AC longest message 0 and BC longest message longer " +
    "then BC default distance then C position depends on BC longest description value and not from the default distance, or messages with A",
    function (assert) {

        var defaultDistanceBetweenABMiddlePoints = 50 + configuration.defaultDistanceBetweenSystems + 10

        var maxDescriptionLengthBetweenAB = defaultDistanceBetweenABMiddlePoints - 1

        var defaultDistanceBetweenBCMiddlePoints = 10 + configuration.defaultDistanceBetweenSystems + 20

        var maxDescriptionLengthBetweenBC = defaultDistanceBetweenBCMiddlePoints + 1

        function MaxDescriptionMessageLengthCalculator() {

            this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
                if (firstSystemName === "A" && secondSystemName === "B") {
                    return maxDescriptionLengthBetweenAB
                }
                if (firstSystemName === "B" && secondSystemName === "C") {
                    return maxDescriptionLengthBetweenBC
                }
                if (firstSystemName === "A" && secondSystemName === "C") {
                    return 0
                }
                throw "Cannot find distance for " + firstSystemName + " and " + secondSystemName
            }
        }

        var conversationReport = {
            "systemNames": ["A", "B", "C"]
        }

        var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

        var distancesCalculator = new DistancesCalculator(
            configuration, systemsBuilder, conversationReport, new MaxDescriptionMessageLengthCalculator()
        )

        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("A"), 0, "distance from A to A is the 0 ")

        assert.equal(
            distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("B"),
            100 + configuration.defaultDistanceBetweenSystems,
            "distance from A to B is equals to the default one"
        )

        assert.equal(
            distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("C"),
            100 + configuration.defaultDistanceBetweenSystems + 10 + maxDescriptionLengthBetweenBC - 20,
            "distance from A to C depends on the longest description message from BC and is not equal to the default one"
        )

    })

QUnit.test(
    "given 3 systems A B C with distance AB equals to the default one because longest description AB is less the default distance, " +
    "and AC longest message 1000 and BC longest message longer then BC default distance then C position depends on BC longest description value and not from the default distance, or messages with A",
    function (assert) {

        var defaultDistanceBetweenABMiddlePoints = 50 + configuration.defaultDistanceBetweenSystems + 10

        var defaultDistanceBetweenBCMiddlePoints = 10 + configuration.defaultDistanceBetweenSystems + 20

        var defaultDistanceBetweenACMiddlePoints = defaultDistanceBetweenABMiddlePoints + defaultDistanceBetweenBCMiddlePoints

        var maxDescriptionLengthBetweenAC = defaultDistanceBetweenACMiddlePoints + 1

        function MaxDescriptionMessageLengthCalculator() {

            this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
                if (firstSystemName === "A" && secondSystemName === "B") {
                    return defaultDistanceBetweenABMiddlePoints
                }
                if (firstSystemName === "B" && secondSystemName === "C") {
                    return defaultDistanceBetweenBCMiddlePoints
                }
                if (firstSystemName === "A" && secondSystemName === "C") {
                    return maxDescriptionLengthBetweenAC
                }
                if (firstSystemName === "A" && secondSystemName === "A") {
                    return 0
                }
                throw "Cannot find distance for " + firstSystemName + " and " + secondSystemName
            }
        }

        var conversationReport = {
            "systemNames": ["A", "B", "C"]
        }

        var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

        var distancesCalculator = new DistancesCalculator(
            configuration, systemsBuilder, conversationReport, new MaxDescriptionMessageLengthCalculator()
        )

        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("A"), 0, "distance from A to A")
        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("B"), 100 + configuration.defaultDistanceBetweenSystems, "distance from A to B is the default one")
        assert.equal(distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem("C"), 50 + maxDescriptionLengthBetweenAC - 20, "distance from A to C depends on max description length")

    }
)

QUnit.test(
    "given a diagram with positions of each systems given by the default system distance " +
    "then sequenceDiagramWidth is given from distance AC + C width + distance from left and right border",
    function (assert) {

        function MaxDescriptionMessageLengthCalculator() {

            this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
                return 0
            }
        }

        var conversationReport = {
            "systemNames": ["A", "B", "C"]
        }

        var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

        var distancesCalculator = new DistancesCalculator(
            configuration, systemsBuilder, conversationReport, new MaxDescriptionMessageLengthCalculator()
        )

        assert.equal(
            distancesCalculator.sequenceDiagramWidth(),
            configuration.distanceFromHorizontalBorder + 100 + configuration.defaultDistanceBetweenSystems + 20 + configuration.defaultDistanceBetweenSystems + 40 + configuration.distanceFromHorizontalBorder,
            "sequenceDiagramWidth is correct")
    })

QUnit.test(
    "given a diagram with positions of each systems given by the max description length " +
    "then sequenceDiagramWidth is given from distance AC + C width + distance from left and right border",
    function (assert) {

        var maxDescriptionLengthForAllSystems = 10000

        function MaxDescriptionMessageLengthCalculator() {

            this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
                return maxDescriptionLengthForAllSystems
            }
        }

        var conversationReport = {
            "systemNames": ["A", "B", "C"]
        }

        var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

        var distancesCalculator = new DistancesCalculator(
            configuration, systemsBuilder, conversationReport, new MaxDescriptionMessageLengthCalculator()
        )

        assert.equal(
            distancesCalculator.sequenceDiagramWidth(),
            configuration.distanceFromHorizontalBorder + 50 + maxDescriptionLengthForAllSystems + maxDescriptionLengthForAllSystems + 20 + configuration.distanceFromHorizontalBorder,
            "sequenceDiagramWidth is correct")
    })
