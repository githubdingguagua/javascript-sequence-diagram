var bodySearchResultHighlighter = new BodySearchResultHighlighter()

QUnit.test("handle recognised when configuration is a body configuration", function( assert ) {

    assert.equal(bodySearchResultHighlighter.handle({object:{searchResult:{idFound:'feature-2'}}}), true);
    assert.equal(bodySearchResultHighlighter.handle({object:{searchResult:{idFound:'scenario-2'}}}), true);
    assert.equal(bodySearchResultHighlighter.handle({object:{searchResult:{idFound:'step-2'}}}), true);

});

QUnit.test("handle recognised when configuration is not a body configuration", function( assert ) {

   assert.equal(bodySearchResultHighlighter.handle({object:{searchResult:{featureId:'1'}}}), false);
   assert.equal(bodySearchResultHighlighter.handle({}), false);

});