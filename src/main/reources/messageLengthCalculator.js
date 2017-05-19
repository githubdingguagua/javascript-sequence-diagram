function messageLengthCalculator(maxDescriptionMessageCalculator, textLengthCalculator) {

    return {
        calculateMaxMessageLengthBetweenSystems: function (firstSystem, secondSystem) {
            return textLengthCalculator.sizeof(maxDescriptionMessageCalculator.calculate(firstSystem, secondSystem)).width
        }
    }

}
