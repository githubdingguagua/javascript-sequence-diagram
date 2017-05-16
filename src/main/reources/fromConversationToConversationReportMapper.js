function FromConversationToConversationReportMapper() {

    this.map = function (conversation) {

        return conversation.reduce(function (conversationReport, aMessage) {
            var newElement = {
                from: aMessage.from,
                to: (aMessage.to && !aMessage.to.alias ? aMessage.to : aMessage.to.alias),
                description: aMessage.description
            }

            conversationReport.conversation.push(newElement)

            var systemNames = conversationReport.systemNames

            if (!systemNames.includes(newElement.from)) {
                systemNames.push(newElement.from)
            }
            if (!systemNames.includes(newElement.to)) {
                systemNames.push(newElement.to)
            }
            return conversationReport
        }, {systemNames: [], conversation: []})

    }

}

