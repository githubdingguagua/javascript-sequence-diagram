function ScreenshotThumbnailNavigator(htmlIdManager) {

    function target(event){
        return event.target || event.srcElement
    }

    function targetDataId(event){
        return  $(target(event)).attr('data-id')
    }

    function scrollToScreenshot (configuration){
        var thumbnailsContainer = $('#' + htmlIdManager.thumbnailsContainerId(configuration.thumbnailsContainerIdentifier));
        thumbnailsContainer.mThumbnailScroller("scrollTo", configuration.screenshotIdentifier);
    }

    this.addOnClickForScreenshotNavigation = function (configuration) {

        $('.js-navigate-first').unbind('click');
        $('.js-navigate-last').unbind('click');
        $('.js-navigate-carousel').unbind('click');

        $('.js-navigate-first').click(function (event) {
            scrollToScreenshot({screenshotIdentifier: "first", thumbnailsContainerIdentifier: this.id});
        });

        $('.js-navigate-last').click(function (event) {
            scrollToScreenshot({screenshotIdentifier: "last", thumbnailsContainerIdentifier: this.id});
        });

        $('.js-navigate-carousel').click(function (event) {
            var titleIdToBeSelected = $(target(event)).parent().find('.js-input-element').val()
            
            if (!$.isNumeric(titleIdToBeSelected)){
                return
            }
            var scenarioAndStepId = targetDataId(event);

            scrollToScreenshot({
              screenshotIdentifier: "#" + htmlIdManager.screenshotImageId(parseInt(titleIdToBeSelected), scenarioAndStepId),
              thumbnailsContainerIdentifier: scenarioAndStepId
           });
        });
   }  

}

