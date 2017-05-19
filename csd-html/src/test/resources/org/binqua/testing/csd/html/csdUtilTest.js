QUnit.test("given page has been loaded showing sequence diagram without using an anchor url, then calculateReloadPageLinkHref and calculateReloadPageLinkText work", function () {

        var informationAboutTheActualLoadedPage = {
            host: "localhost",
            hostname: "localhost",
            href: "http://localhost:8080/index.html?sd",
            pathname: "index.html",
            search: "?sd"
        }

        var csdUtil = new CsdUtil(informationAboutTheActualLoadedPage);

        equal(csdUtil.calculateReloadPageLinkHref(), "http://localhost:8080/index.html");
        equal(csdUtil.calculateReloadPageLinkText(), "reload page without sequence diagram (much faster)");

});

QUnit.test("given page has been loaded showing sequence diagram and using an anchor url, then calculateReloadPageLinkHref and calculateReloadPageLinkText work", function () {

    var informationAboutTheActualLoadedPage = {
        host: "localhost",
        hostname: "localhost",
        href: "http://localhost:8080/index.html?sd#screenshots-label_scenario-2_step-2",
        pathname: "index.html",
        search: "?sd"
    }

    var csdUtil = new CsdUtil(informationAboutTheActualLoadedPage);

    equal(csdUtil.calculateReloadPageLinkHref(), "http://localhost:8080/index.html");
    equal(csdUtil.calculateReloadPageLinkText(), "reload page without sequence diagram (much faster)");

});

QUnit.test("given page has been loaded without showing sequence diagram then calculateReloadPageLinkHref and calculateReloadPageLinkText work", function () {

    var informationAboutTheActualLoadedPage = {
        host: "localhost",
        hostname: "localhost",
        href: "http://localhost:8080/index.html",
        pathname: "index.html",
        search: ""
    }

    var csdUtil = new CsdUtil(informationAboutTheActualLoadedPage);

    equal(csdUtil.calculateReloadPageLinkHref(), "http://localhost:8080/index.html?sd");
    equal(csdUtil.calculateReloadPageLinkText(), "reload page with sequence diagram (much slower)");

});

QUnit.test("given page has been loaded without showing sequence diagram and with an anchor # in the url, then calculateReloadPageLinkHref and calculateReloadPageLinkText work", function () {

    var informationAboutTheActualLoadedPage = {
        host: "localhost",
        hostname: "localhost",
        href: "http://localhost:8080/index.html#screenshots-label_scenario-2_step-1",
        pathname: "index.html",
        search: ""
    }

    var csdUtil = new CsdUtil(informationAboutTheActualLoadedPage);

    equal(csdUtil.calculateReloadPageLinkHref(), "http://localhost:8080/index.html?sd");
    equal(csdUtil.calculateReloadPageLinkText(), "reload page with sequence diagram (much slower)");

});

QUnit.test("configuration is read correctly", function () {

    var csdUtil = new CsdUtil({}, {
                                    "configuration":{
                                    "accessibleFromMultipleReportsPage": false,
                                    "multipleReportsHomeUrl": "http://blabla",
                                    "sequenceDiagramSupportDisabled": false,
                                    "title": "Agent Web Server Tests Report"
                                  }});

    equal(csdUtil.showAllFeaturesText(), "Features list");
    equal(csdUtil.sequenceDiagramSupportDisabled(), false);
    equal(csdUtil.accessibleFromMultipleReportsPage(), false);
    equal(csdUtil.multipleProjectsReportText(), "All Tests Reports");
    equal(csdUtil.multipleReportsHomeUrl(), "http://blabla");
    equal(csdUtil.pageTitle(), "Agent Web Server Tests Report");

});

