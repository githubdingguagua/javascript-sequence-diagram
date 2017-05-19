function CsdUtil(actualPageLocation, featuresMenu) {

    const sequenceDiagramIdentifier = "sd"

    const sequenceDiagramIdentifierQueryPart = "?sd"

    this.showAllFeaturesText = function(){
        return 'Features list';
    }

    this.pageTitle = function(){
        return featuresMenu.configuration.title;
    }

    this.sequenceDiagramSupportDisabled = function(){
        return featuresMenu.configuration.sequenceDiagramSupportDisabled;
    }

    this.accessibleFromMultipleReportsPage = function(){
        return featuresMenu.configuration.accessibleFromMultipleReportsPage;
    }

    this.multipleProjectsReportText = function(){
        return "All Tests Reports";
    }

    this.multipleReportsHomeUrl = function(){
        return featuresMenu.configuration.multipleReportsHomeUrl;
    }

    this.hasPageBeenLoadedToShowSequenceDiagram = function() {
        var sPageURL = actualPageLocation.search.substring(1);
        return sPageURL.indexOf(sequenceDiagramIdentifier) > -1
    }

    this.calculateReloadPageLinkText = function() {
        var withOrWithout = "with sequence diagram (much slower)"
        if (this.hasPageBeenLoadedToShowSequenceDiagram()){
            withOrWithout = "without sequence diagram (much faster)"
        }
        return "reload page " + withOrWithout
    }

    this.calculateReloadPageLinkHref = function() {
        function removeAnchor(input){
            return input.replace(/#.*$/,"")
        }
        if (this.hasPageBeenLoadedToShowSequenceDiagram()){
            return removeAnchor(actualPageLocation.href).replace(sequenceDiagramIdentifierQueryPart,"")
        }
        return removeAnchor(actualPageLocation.href) + sequenceDiagramIdentifierQueryPart
    }

    this.format = function (xml) {
          var formatted = '';
          var reg = /(>)(<)(\/*)/g;
          xml = xml.replace(reg, '$1\r\n$2$3');
          var pad = 0;
          jQuery.each(xml.split('\r\n'), function(index, node) {
              var indent = 0;
              if (node.match( /.+<\/\w[^>]*>$/ )) {
                  indent = 0;
              } else if (node.match( /^<\/\w/ )) {
                  if (pad != 0) {
                      pad -= 1;
                  }
              } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
                  indent = 1;
              } else {
                  indent = 0;
              }

              var padding = '';
              for (var i = 0; i < pad; i++) {
                  padding += '  ';
              }

              formatted += padding + node + '\r\n';
              pad += indent;
          });

          return formatted;
    }
}
