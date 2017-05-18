function messageLengthCalculator(maxDescriptionMessageCalculator, textLengthCalculator) {

    return {
        calculate: function (firstSystem, secondSystem) {
            return textLengthCalculator.sizeof(maxDescriptionMessageCalculator.calculate(firstSystem, secondSystem)).width
        }
    }

}
