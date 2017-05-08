function FromConversationToConversationReportMapper() {

    this.map = function (conversation) {
        return conversation.map(function (aMessage) {
            return {
                from: aMessage.from,
                to: (aMessage.to && !aMessage.to.alias ? aMessage.to : aMessage.to.alias),
                description: aMessage.description
            }
        })
    }

}

