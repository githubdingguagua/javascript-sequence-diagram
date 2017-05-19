function ConversationMessagesRenderer(showSequenceDiagram) {

    var that = this

    function messagesHtml(messages) {
        var messagesHtml = "";
        for (i = 0; i < messages.length; i++) {
            messagesHtml += that.messageHtml(messages[i],"");
        }
        return messagesHtml;
    }

    function path(request) {
        if (request.path === undefined){
            return ""
        }
        return "<li class='attribute'><span class='attribute-key'>path</span><span class='attribute-separator'> : </span><span class='attribute-value'>" + request.path.value + "</span></li>"
    }

    function httpMethod(request) {
        if (request.method === undefined){
            return ""
        }
        return "<li class='attribute'><span class='attribute-key'>method</span><span class='attribute-separator'> : </span><span class='attribute-value'>" + request.method + "</span></li>"
    }

    this.messageHtml = function (message, aBodyEditorIdSuffix) {
        function requestHtml(request) {
            return "<div class='conversation-request'>" +
                "<p class='conversation-title conversation-request-title' id='"+request.id+"-title' >" + request.description + "</p>" +
                "<ol class='conversation-request-attributes'>" +
                httpMethod(request) +
                path(request) +
                headers(request) +
                bodyHtml(message,"request", request.id) +
                "</ol>" +
                "</div>";
        }

        function responseHtml(response) {
            return "<div class='conversation-response'>" +
                "<p class='conversation-title conversation-response-title' id='" + response.id + "-title' >" + response.description + "</p>" +
                "<ol class='conversation-response-attributes'>" +
                headers(response) +
                bodyHtml(message, "response", response["id"]) +
                "</ol>" +
                "</div>";
        }

        function headers(requestOrResponse) {
            if (requestOrResponse.headers === undefined){
                return ""
            }
            var headers = requestOrResponse.headers["http-headers"];
            var headersHtml = "";
            for (var key in headers) {
                headersHtml += "<li class='attribute'><span class='attribute-key'>" + key + "</span><span class='attribute-separator'> : </span><span class='attribute-value'>" + headers[key] + "</span></li>";
            }
            return headersHtml;
        }

        function bodyHtml(message, requestOrResponse, messageId) {
            function isEmptyObject(obj) {
                for(var prop in obj) {
                    if(obj.hasOwnProperty(prop))
                        return false;
                }
                return true;
            }
            var bodyHtmlHtmlContent = ""
            if (!isEmptyObject(message.body)){
               bodyHtmlHtmlContent += "<li><span>Body:</span><div id='" + messageId + "-body" + aBodyEditorIdSuffix + "' class='" + requestOrResponse + "-body'></div></li>";
            }
            if (!isEmptyObject(message.deliveryException)){
               bodyHtmlHtmlContent += "<li><span>Delivery Exception:</span><div id='delivery-exception-" + messageId + "-body" + aBodyEditorIdSuffix + "' class='delivery-exception-body'></div></li>";
            }
            return bodyHtmlHtmlContent
        }

        if (message.type == 'request') {
            return requestHtml(message);
        } else {
            return responseHtml(message);
        }
    }

    this.render =  function (conversation) {
        if (!showSequenceDiagram || conversation.messages.length == 0){
            return ""
        }

        return  "<div class='conversations-messages'>" +
                "<p class='conversations-messages-title'>Messages:</p>" +
                "<div class='conversations-body'>" +
                messagesHtml(conversation.messages) +
                "</div>" +
                "</div>" +
                "<div class='conversations-sequence-diagram'>" +
                "<p class='conversations-sequence-diagram-title'>Sequence Diagram:</p>" +
                "<div class='conversations-sequence-diagram-body'>" +
                conversation.sequenceDiagram +
                "</div>" +
                "</div>"
    }

}
