function ArtifactLoader(loader, namingUtil) {

     this.featureConversationFile =  function (featureToBeShown) {
        return loader.loadJsonFileSynch(namingUtil.featureConversationFile(featureToBeShown));
     },

     this.featureIndexFile =  function (featureToBeShown) {
        return loader.loadTextFileSynch(namingUtil.featureIndexFile(featureToBeShown));
     }

}

