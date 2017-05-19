function BodyIndexer(namingUtil, featureList) {

    this.index = lunr(function () {
        this.field('featureTitle', {boost: 10})
        this.field('scenarioTitles' , {boost: 5})
        this.field('scenarioSteps')
        this.ref('id')
    })

    this.featureIdAndTitleList = []

    this.scenarioIdAndTitleList = []

    var that = this

    function loadJsonFileSynch(fileToBeLoaded, featureId) {
        $.ajax({
            url: fileToBeLoaded,
            async: false,
            dataType: 'json',
            success: function (featureReport) {

                that.featureIdAndTitleList.push({id:featureId, title:featureReport.title})

                var scenariosInAFeature = $.map( featureReport.scenarios , function( scenario ) {return {id:scenario.id, title:scenario.title}})

                $.each(scenariosInAFeature, function( index, scenario ) { that.scenarioIdAndTitleList.push(scenario) })

                var scenarioTitlesList = $.map( scenariosInAFeature, function( scenarioInAFeature ) {return scenarioInAFeature.title});

                var scenarioStepsList = $.map( featureReport.scenarios , function( scenario ) {return scenario.steps});

                that.index.add({
                    id: featureId,
                    featureTitle: featureReport.title,
                    scenarioTitles: scenarioTitlesList.join(" , "),
                    scenarioSteps: $.map(scenarioStepsList , function( step ) { return step.text }).join(" , "),
                })
            }
        });
    }

    this.init = function () {
        $.each(featureList, function( index, feature ) {
            loadJsonFileSynch(namingUtil.featureReport(feature.id), feature.id)
        })
    }

    this.searchFeatureIdsWith = function (toBeFound) {
        return $.map(this.index.search(toBeFound), function (elementFound){ return elementFound.ref})
    }

    this.searchFeatureIdsWithout = function (toBeFound) {
        var unfilteredFeatureIds = $.map(featureList, function(elementFound){ return elementFound.id})

        var featureIdsContainingWords = this.searchFeatureIdsWith(toBeFound)

        return $.grep(unfilteredFeatureIds, function(n){return $.inArray(n, featureIdsContainingWords) == -1; });
    }

    this.featureNameById = function (featureId) {
        return $.grep(this.featureIdAndTitleList, function( featureIdAndTitlePair ) { return featureIdAndTitlePair.id == featureId})[0].title
        throw "featureId " + featureId + " not found in " + JSON.stringify(this.featureIdAndTitleList)
    }

    this.scenarioNameById = function (scenarioId) {
        return $.grep(this.scenarioIdAndTitleList, function( scenarioIdAndTitlePair ) { return scenarioIdAndTitlePair.id == scenarioId})[0].title
        throw "scenarioId " + scenarioId + " not found in " + JSON.stringify(this.scenarioIdAndTitleList);
    }

}

