$('#layout').w2layout({
    name: 'layout',
    panels: [
        { type: 'top', content: '' } ,
        { type: 'left', content: ''},
        { type: 'main', content: ''},
        { type: 'bottom', content:'' }
    ]
});

QUnit.test("SearchResultFormatter recognised bodySearchResult and screenshotSearchResult format", function( assert ) {

    var searchResultElementsToBeFormatted = [
            {
                featureId:"feature-0",
                scenarioId:"scenario-0",
                stepId:"0"
            },
            {
                featureId:"feature-0",
                scenarioId:"scenario-0",
                screenshotId:"1"
            }
    ]

    var bodySearchResultFormatter = {
        format: function (toBeFormatted){}
    }

    var screenshotSearchResultFormatter = {
       format: function (toBeFormatted){}
    }

   var bodySearchResultFormatterStub = sinon.stub(bodySearchResultFormatter, "format")
                             .withArgs(0,
                                       {
                                           featureId:"feature-0",
                                           scenarioId:"scenario-0",
                                           stepId:"0"
                                       })
                             .returns("<a></a>");

   var screenshotSearchResultFormatterStub = sinon.stub(screenshotSearchResultFormatter, "format")
                                                  .withArgs(1,
                                                            {
                                                               featureId:"feature-0",
                                                               scenarioId:"scenario-0",
                                                               screenshotId:"1"
                                                            })
                                                  .returns("<b></b>");

   var searchResultFormatter = new SearchResultFormatter(bodySearchResultFormatter, screenshotSearchResultFormatter)

   assert.equal(searchResultFormatter.format(searchResultElementsToBeFormatted) ,
                                                              "<ul>" +
                                                              "<a></a>" +
                                                              "<b></b>" +
                                                              "</ul>"
   )

});

QUnit.test("SearchResultFormatter recognised bodySearchResult with no stepId format", function( assert ) {

    var searchResultElementsToBeFormatted = [
            {
                featureId:"feature-0",
                scenarioId:"scenario-0",
                stepId:""
            }
    ]

    var bodySearchResultFormatter = {
        format: function (toBeFormatted){}
    }

   var bodySearchResultFormatterStub = sinon.stub(bodySearchResultFormatter, "format")
                             .withArgs(0,
                                       {
                                           featureId:"feature-0",
                                           scenarioId:"scenario-0",
                                           stepId:""
                                       })
                             .returns("<a></a>");

   var searchResultFormatter = new SearchResultFormatter(bodySearchResultFormatter)

   assert.equal(searchResultFormatter.format(searchResultElementsToBeFormatted) ,
                                                              "<ul>" +
                                                              "<a></a>" +
                                                              "</ul>"
   )

});

QUnit.test("SearchResultFormatter recognised bodySearchResult with no scenarioId format", function( assert ) {

    var searchResultElementsToBeFormatted = [
            {
                featureId:"feature-0",
                scenarioId:"",
                stepId:""
            }
    ]

    var bodySearchResultFormatter = {
        format: function (toBeFormatted){}
    }

   var bodySearchResultFormatterStub = sinon.stub(bodySearchResultFormatter, "format")
                             .withArgs(0,
                                       {
                                           featureId:"feature-0",
                                           scenarioId:"",
                                           stepId:""
                                       })
                             .returns("<a></a>");

   var searchResultFormatter = new SearchResultFormatter(bodySearchResultFormatter)

   assert.equal(searchResultFormatter.format(searchResultElementsToBeFormatted) ,
                                                              "<ul>" +
                                                              "<a></a>" +
                                                              "</ul>"
   )

});

QUnit.test("SearchResultFormatter throws exception if result is not a bodySearchResult or screenshotSearchResult format", function( assert ) {

    var bodySearchResultFormatter = {}

    var screenshotSearchResultFormatter = {}

    var searchResultElementToBeFormatted = { crap:"some crap data cd" }
    var searchResultElementsToBeFormatted = [ searchResultElementToBeFormatted ]

    var searchResultFormatter = new SearchResultFormatter(bodySearchResultFormatter, screenshotSearchResultFormatter)

    assert.throws(
        function() {
            searchResultFormatter.format(searchResultElementsToBeFormatted)
        },
        new Error("One object to be formatted does not contain screenshotId or stepId field: " + JSON.stringify(searchResultElementToBeFormatted)),
        ""
    );

});

QUnit.test("makeIdClickable delegates to bodySearchResultFormatter and screenshotSearchResultFormatter", function( assert ) {

    var bodySearchResultFormatter = {
            makeIdClickable: function (someObject){
        }
    }

    var bodySearchResultFormatterMock = sinon.mock(bodySearchResultFormatter);

    var screenshotSearchResultFormatter = {
            makeIdClickable: function (someObject){
         }
    }

    var screenshotSearchResultFormatterMock = sinon.mock(screenshotSearchResultFormatter);

    var anObject = { someFunction: function (someObject){} }

    var bodySearchResultFormatterExpectation = bodySearchResultFormatterMock.expects("makeIdClickable");

    bodySearchResultFormatterExpectation.withExactArgs(anObject)

    var screenshotSearchResultFormatterExpectation = screenshotSearchResultFormatterMock.expects("makeIdClickable");

    screenshotSearchResultFormatterExpectation.withExactArgs(anObject)


    new SearchResultFormatter(bodySearchResultFormatter, screenshotSearchResultFormatter).makeIdClickable(anObject)


    bodySearchResultFormatterMock.verify()

    screenshotSearchResultFormatterMock.verify()

});
