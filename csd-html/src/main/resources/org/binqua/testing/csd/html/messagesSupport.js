function MessagesSupport(namingUtil, htmlIdManager, conversationMessagesRenderer) {

    var messageBodyList = []
    var conversationsMap = {}
    var messagesMap = {}
    var theOuterObj = this

    function init(scenarioId, conversationMessages) {

        var conversationBodiesForTheGivenScenario = conversationMessages.map(function (message) {
            return {
                "key":  message.id + "-body",
                "body": message.body,
                "deliveryException": message.hasOwnProperty("deliveryException") ? message.deliveryException : ""
            }
        });

        messageBodyList = messageBodyList.concat(conversationBodiesForTheGivenScenario);

        conversationMessages.forEach(function(message){
            messagesMap[message.id] = message;
        })

    }

    this.recordConversation = function (singleScenarioConversation) {

        function messagesOfA(singleScenarioConversation){
            var messages = []
            $.each(singleScenarioConversation.conversation, function( index, stepConversation ) {
                $.merge(messages, stepConversation.messages)}
            )
            return messages
        };

        init(singleScenarioConversation.scenario.name, messagesOfA(singleScenarioConversation))
    }

    this.message = function (id) {
        return messagesMap[id];
    }

    this.messagesBody = function () {
        return messageBodyList;
    }

    this.showMessage = function (messageId, contextSuffix) {

        var bodyData = $.grep(this.messagesBody(), function(e){ return e["key"] == messageId + "-body"; })[0];

        if (bodyData.body.value && bodyData.body.value.length != 0) {
            var editor = ace.edit(bodyData.key + contextSuffix);
            editor.setTheme("ace/theme/github");
            editor.setReadOnly(true);
            editor.session.setMode("ace/mode/" + bodyData.body["content-type"]);
            editor.setOptions({
                maxLines: 2000
            })
            editor.setValue(bodyData.body.value, 1);
        }

        if (bodyData.deliveryException.length != 0) {
            var deliveryExceptionEditor = ace.edit("delivery-exception-" + bodyData.key + contextSuffix);
            deliveryExceptionEditor.setTheme("ace/theme/github");
            deliveryExceptionEditor.setReadOnly(true);
            deliveryExceptionEditor.session.setMode("ace/mode/text");
            deliveryExceptionEditor.setOptions({
                maxLines: 2000
            })
            deliveryExceptionEditor.setValue(bodyData.deliveryException, 1);
        }
    }

    this.showOrHide = function (component) {
        if (component.is(":visible")) {
            component.hide();
        } else {
            component.show();
        }
    }

    this.makeMessagesClickable = function(){
         $(".clickable").bind("click", function(event){

                function calculateHeight(message ){
                    function isEmptyObject(obj) {
                          for(var prop in obj) {
                              if(obj.hasOwnProperty(prop))
                                  return false;
                          }
                          return true;
                    }
                    return isEmptyObject(message.body) ? 300 : 800
                 }

                var idOfClickedElement = $(this).attr('id')
                var message = messagesMap[idOfClickedElement]
                var dialog = $('<div id="' + idOfClickedElement + '-popup" title="'+ message.description+ '">'+ conversationMessagesRenderer.messageHtml(message, "dialog" )+'</div>')
                dialog.find('ol').show();
                dialog.appendTo($('body'));
                dialog.dialog({
                    maxWidth:1400,
                    maxHeight: 1000,
                    width: 1400,
                    height: calculateHeight(message.body),
                    close: function( event, ui ) {
                        $(this).remove();
                    }
                });
                dialog.dialog( "option", "maxWidth" );
                $(".ui-front").zIndex(120);
                theOuterObj.showMessage(idOfClickedElement, "dialog");
            });
     };

    this.makeScreenshotsClickable = function(featureToBeShown, featureConversationsByScenarios ){
      var that = this;

      var handleScreenshotClick = function (event, target) {
        var self = target || this,
            theImageIndex = $("#" + self.id).data("screenshotId"),
            theScenarioId = htmlIdManager.scenarioId(self.id);

        $.when(
          $.get(namingUtil.screenshotHtmlFile(featureToBeShown, theScenarioId , theImageIndex))
        ).done( function (htmlSource) {
          var screenshot = namingUtil.findScreenshot(featureConversationsByScenarios, theScenarioId, theImageIndex),
              htmlToBeShown = "<div id='screenshotPopupTest' title='" + screenshot.title + "'>" +
                              "<p>Url:" + screenshot.url + "</p>" +
                              "<ul>" +
                               "<li><a href='#imageTab'>Image</a></li>" +
                               "<li><a href='#htmlTab'>Html</a></li>" +
                               "</ul>" +
                               "<div id='imageTab'><img style='width: 100%' src='" + namingUtil.croppedScreenshotImage(featureToBeShown, theScenarioId ,theImageIndex )+ "'/></div>" +
                               "<div id='htmlTab'><textarea rows='600' cols='200'>" +  $('<div/>').text(htmlSource).html() + "</textarea></div>"  +
                               "</div>";

          var dialog = $(htmlToBeShown),
              buttons = "<div class='previous-screenshot'>PREVIOUS</div><div class='next-screenshot'>NEXT</div>";

          w2popup.open({
              title   : screenshot.title,
              body    : dialog[0].outerHTML,
              buttons : buttons,
              width: '600px',
              height: '600px',
              modal: true,
              speed: 0.3,
              showMax: true,
              keyboard: true,
              style: ''
          });

          $('#w2ui-popup').css("width","70%");
          $('#w2ui-popup').css("left","15%");

          $(".ui-front").zIndex(120);
          $( "#screenshotPopupTest" ).tabs({
             collapsible: true
          });

          $(".next-screenshot").bind("click", function(event) {
            var elem = $('#' + $(self).attr('data-next-screenshot'))[0];
            if (elem) {
              $('.ui-dialog.ui-widget').remove();
              $('#screenshotPopupTest').remove();
              handleScreenshotClick(event, elem);
            }
          });

          $(".previous-screenshot").bind("click", function(event) {
            var elem = $('#' + $(self).attr('data-previous-screenshot'))[0];
            if (elem) {
              $('.ui-dialog.ui-widget').remove();
              $('#screenshotPopupTest').remove();
              handleScreenshotClick(event, elem);
            }
          });

        });
      };

       $(".clickableScreenshot").bind("click", handleScreenshotClick);
       $( "#screenshotPopupTest" ).tabs({
          collapsible: true
       });
    };

     this.makeConversationTitlesClickable =function(){
         $(".conversation-title").bind("click", function(event){
               var messageId = $(this).attr('id').replace("-title","")
               theOuterObj.showMessage(messageId, "");
         });
     }
}