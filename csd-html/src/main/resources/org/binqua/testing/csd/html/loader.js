function Loader(testsResultSummary) {

     this.loadJsonFileSynch =  function (fileToBeLoaded , callback) {
        var result = null;
        $.ajax({
            url: fileToBeLoaded,
            async: false,
            dataType: 'json',
            success: function (contentLoaded) {
               result = contentLoaded
            }
        });
        return result
     },

     this.loadTextFileSynch = function (fileToBeLoaded) {
         var result = null;
         $.ajax({
             url: fileToBeLoaded,
             async: false,
             dataType: 'html',
             success: function (contentLoaded) {
                result = contentLoaded
             }
         });
         return result
      }
}

