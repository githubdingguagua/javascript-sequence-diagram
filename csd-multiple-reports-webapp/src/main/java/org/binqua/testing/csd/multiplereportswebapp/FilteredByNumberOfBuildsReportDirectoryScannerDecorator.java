package org.binqua.testing.csd.multiplereportswebapp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FilteredByNumberOfBuildsReportDirectoryScannerDecorator implements ReportDirectoryScanner {

    private ReportDirectoryScanner reportDirectoryScanner;

    public FilteredByNumberOfBuildsReportDirectoryScannerDecorator(ReportDirectoryScanner reportDirectoryScanner) {
        this.reportDirectoryScanner = reportDirectoryScanner;
    }

    @Override
    public BuildsUrl scan() {

        final BuildsUrl originalBuildsUrl = reportDirectoryScanner.scan();

        Map<String, List<TestReportInfo>> mapGroupedByBuildPrettyName = new TreeMap<>(originalBuildsUrl.testReportInfoList().stream().collect(Collectors.groupingBy(TestReportInfo::prettyName)));

        final Map<String, Integer> toPrettyNameNumberOfBuildsToShowMap = toPrettyNameNumberOfBuildsToShowMap(mapGroupedByBuildPrettyName);

        Map<String, List<TestReportInfo>> mapGroupedByBuildPrettyNameFiltered = new HashMap<>();

        mapGroupedByBuildPrettyName.forEach(
            (prettyName, testReportInfos) -> {
                final List<TestReportInfo>
                    filteredTestReportInfoList =
                    testReportInfos.stream().sorted(descendingBuildNumber()).collect(Collectors.toList()).subList(0, Math.min(testReportInfos.size(), toPrettyNameNumberOfBuildsToShowMap.get(prettyName)));
                mapGroupedByBuildPrettyNameFiltered.put(prettyName, filteredTestReportInfoList);
            });

        final List<TestReportInfo> allTestReportInfos = mapGroupedByBuildPrettyNameFiltered.entrySet().stream().flatMap(x -> x.getValue().stream()).collect(Collectors.toList());

        return new BuildsUrl(allTestReportInfos);

    }

    private Comparator<TestReportInfo> descendingBuildNumber() {
        return (o1, o2) -> o1.number() > o2.number() ? -1 : 1;
    }

    private Map<String, Integer> toPrettyNameNumberOfBuildsToShowMap(Map<String, List<TestReportInfo>> mapGroupedByPrettyNameUrls) {
        return toMap(toList(mapGroupedByPrettyNameUrls));
    }

    private Map<String, Integer> toMap(List<PrettyNameNumberOfBuildsToShow> prettyNameNumberOfBuildsToShows) {
        return prettyNameNumberOfBuildsToShows
            .stream()
            .collect(Collectors.toMap(PrettyNameNumberOfBuildsToShow::prettyName, PrettyNameNumberOfBuildsToShow::numberOfBuilds));
    }

    private List<PrettyNameNumberOfBuildsToShow> toList(Map<String, List<TestReportInfo>> mapGroupedByPrettyNameUrls) {
        return mapGroupedByPrettyNameUrls.keySet().stream().map(
            buildPrettyName -> new PrettyNameNumberOfBuildsToShow(buildPrettyName, mapGroupedByPrettyNameUrls.get(buildPrettyName).get(0).numberOfBuildsToShow())).collect(Collectors.toList());
    }

    private class PrettyNameNumberOfBuildsToShow {

        private String prettyName;
        private int numberOfBuildsToShow;

        public PrettyNameNumberOfBuildsToShow(String prettyName, int numberOfBuildsToShow) {
            this.prettyName = prettyName;
            this.numberOfBuildsToShow = numberOfBuildsToShow;
        }

        public String prettyName() {
            return prettyName;
        }

        public int numberOfBuilds() {
            return numberOfBuildsToShow;
        }
    }
}
