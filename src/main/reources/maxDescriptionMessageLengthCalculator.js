function MaxDescriptionMessageLengthCalculator(conversation) {

    function key(firstPart, secondPart) {
        return firstPart + "_@_" + secondPart
    }

    function sortedMessageKey(aMessage) {
        return aMessage.to.localeCompare(aMessage.from) < 0 ? key(aMessage.to, aMessage.from) : key(aMessage.from, aMessage.to)
    }

    function calculateMaxDescriptionLengthBetweenSystems(conversation) {
        return conversation.reduce(function (maxDescriptionLengthBetweenSystemsSystemsMap, aMessage) {

            var theMessageKey = sortedMessageKey(aMessage)

            var theDescriptionWithMaxLength = maxDescriptionLengthBetweenSystemsSystemsMap.get(theMessageKey)

            if (!theDescriptionWithMaxLength || theDescriptionWithMaxLength.length < aMessage.description.length) {
                maxDescriptionLengthBetweenSystemsSystemsMap.set(theMessageKey, aMessage.description)
            }

            return maxDescriptionLengthBetweenSystemsSystemsMap
        }, new Map())
    }

    var maxDescriptionLengthBetweenSystemsSystemsMap = calculateMaxDescriptionLengthBetweenSystems(conversation)

    this.maxDescriptionLengthBetweenSystems = function (firstSystem, secondSystem) {
        return maxDescriptionLengthBetweenSystemsSystemsMap.get(sortedMessageKey({from: firstSystem, to: secondSystem}))
    }

}
