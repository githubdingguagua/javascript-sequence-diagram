function MaxDescriptionMessageLengthCalculator(conversation) {

    function key(firstPart, secondPart) {
        return firstPart + "_@_" + secondPart
    }

    function sortedMessageKey(aMessage) {
        return aMessage.to.localeCompare(aMessage.from) < 0 ? key(aMessage.to, aMessage.from) : key(aMessage.from, aMessage.to)
    }

    function calculateMaxDescriptionLengthBetweenSystems(conversation) {
        return conversation.reduce(function (maxDescriptionLengthBetweenSystemsMap, aMessage) {

                var theMessageKey = sortedMessageKey(aMessage)

                var theDescriptionWithMaxLength = maxDescriptionLengthBetweenSystemsMap.get(theMessageKey)

                if (!theDescriptionWithMaxLength || theDescriptionWithMaxLength.length < aMessage.description.length) {
                    maxDescriptionLengthBetweenSystemsMap.set(theMessageKey, aMessage.description)
                }

                return maxDescriptionLengthBetweenSystemsMap
            }, new Map()
        )

    }

    var maxDescriptionLengthBetweenSystemsMap = calculateMaxDescriptionLengthBetweenSystems(conversation)

    this.maxDescriptionLengthBetweenSystems = function (firstSystem, secondSystem) {
        return maxDescriptionLengthBetweenSystemsMap.get(sortedMessageKey({from: firstSystem, to: secondSystem}))
    }

}
