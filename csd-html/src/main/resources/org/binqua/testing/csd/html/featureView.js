function FeatureView(loader ,
                     layout,
                     namingUtil,
                     csdUtil ,
                     searchResultHighlighter,
                     artifactLoader,
                     messagesSupport,
                     conversationRenderer,
                     screenshotThumbnailNavigator,
                     featureHtmlReportBuilder) {

    var oldFeatureIdDisplayed;

    this.addScenarioEvents = function () {
      $('.js-step-toggle').click(this.handleOpenSteps);
    };

    this.handleOpenSteps = function (event) {
      var container,
          isSearchObject;

      if (!event.currentTarget) {
        container = event.object.searchResult.idFound;

        if (!container) {
          container = event.object.selectedStep.stepId;
        }

        container = $('#' + container).parent();

        isSearchObject = true;

      } else {
        container = $(event.currentTarget.parentElement);
      }

      if (container.hasClass('open') && !isSearchObject) {
        container.removeClass('open');
      } else {
        container.addClass('open');
      }

    };

    this.showFeature = function (configuration) {
          layout.show('main');
          layout.hide('left');

          function isInitializedByFeatureId () {

                function validConfiguration(){
                   return configuration.object && configuration.object.id
                }

                if (oldFeatureIdDisplayed !== undefined && validConfiguration()){
                    var alreadyInitialised = oldFeatureIdDisplayed == configuration.object.id
                    if (alreadyInitialised){ return true }
                    oldFeatureIdDisplayed = configuration.object.id
                    return alreadyInitialised
                }
                if (oldFeatureIdDisplayed === undefined && validConfiguration()){
                    oldFeatureIdDisplayed = configuration.object.id
                    return false
                }
                return false
            }

            searchResultHighlighter.deselectedSearchResult()

            if (isInitializedByFeatureId()){
                if (searchResultHighlighter.aSearchResultHasBeenSelected(configuration)){
                    searchResultHighlighter.showSelectedSearchResult(configuration)
                    return
                }
                return
            }

            var featureToBeShown =  configuration.object.id;

            layout.lock('main', 'Loading ........', true);

            var featureConversationsByScenarios = artifactLoader.featureConversationFile(featureToBeShown)

            $.each(featureConversationsByScenarios, function( index, singleScenarioConversation ) {
                  messagesSupport.recordConversation(singleScenarioConversation);
            })

            layout.unlock('main')

            var featureIndexHtml = featureHtmlReportBuilder.build(featureToBeShown, featureConversationsByScenarios)

            layout.content('main', featureIndexHtml)

            featureHtmlReportBuilder.attachScreenshots(featureConversationsByScenarios)

            $('.conversations-body').hide();
            $('.conversation-request-attributes').hide();
            $('.conversation-response-attributes').hide();
            $('.conversations-sequence-diagram-body').hide();

            $('.conversations-screenshots-title').click(function () {
                $(this).next('.conversations-screenshots-body').parent().find('.conversations-screenshots-title').toggleClass('active');
                messagesSupport.showOrHide($(this).next('.conversations-screenshots-body'));
            });

            $('.conversations-messages-title').click(function () {
                messagesSupport.showOrHide($(this).next('.conversations-body'))
            });

            $('.conversation-request-title').click(function () {
                messagesSupport.showOrHide($(this).next('.conversation-request-attributes'));
            });

            $('.conversation-response-title').click(function () {
                messagesSupport.showOrHide($(this).next('.conversation-response-attributes'));
            });

            $('.conversations-sequence-diagram-title').click(function () {
               messagesSupport.showOrHide($(this).next('.conversations-sequence-diagram-body'));
            });

            messagesSupport.makeConversationTitlesClickable();

            messagesSupport.makeMessagesClickable();

            screenshotThumbnailNavigator.addOnClickForScreenshotNavigation()

            $('.conversations-screenshots-body').hide();

            messagesSupport.makeScreenshotsClickable(featureToBeShown, featureConversationsByScenarios);

            layout.show('top', true);
            layout.get('top').toolbar.items[0].text = csdUtil.showAllFeaturesText();
            layout.get('top').toolbar.items[0].disabled = false;
            layout.get('top').toolbar.refresh();

            this.addScenarioEvents();

            if (searchResultHighlighter.aSearchResultHasBeenSelected(configuration)){
                searchResultHighlighter.showSelectedSearchResult(configuration)
                return
            }

            $('.spin').spin('hide');

    }

}

