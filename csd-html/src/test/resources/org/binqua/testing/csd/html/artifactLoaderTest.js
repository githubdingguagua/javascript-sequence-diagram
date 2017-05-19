QUnit.test("featureConversationFile returns the right file content", function( assert ) {

   var loader = {
        loadJsonFileSynch: function (fileToBeLoaded){}
   }

   var namingUtil = {
       featureConversationFile: function (featureToBeShown){}
   }

   var namingUtilStub = sinon.stub(namingUtil, "featureConversationFile").withArgs("feature-1").returns("aJsonFile");

   var loaderStub = sinon.stub(loader, "loadJsonFileSynch").withArgs("aJsonFile").returns("aJsonFileContent");

   assert.equal(new ArtifactLoader(loader, namingUtil).featureConversationFile("feature-1"), "aJsonFileContent")

});

QUnit.test("featureIndexFile returns the right file content", function( assert ) {

   var loader = {
        loadTextFileSynch: function (fileToBeLoaded){}
   }

   var namingUtil = {
       featureIndexFile: function (featureToBeShown){}
   }

   var namingUtilStub = sinon.stub(namingUtil, "featureIndexFile").withArgs("feature-1").returns("aTextFile");

   var loaderStub = sinon.stub(loader, "loadTextFileSynch").withArgs("aTextFile").returns("theTextFileContent");

   assert.equal(new ArtifactLoader(loader, namingUtil).featureIndexFile("feature-1"), "theTextFileContent")

});
