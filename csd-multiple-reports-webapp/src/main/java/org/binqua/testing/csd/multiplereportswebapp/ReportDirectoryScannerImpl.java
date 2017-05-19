package org.binqua.testing.csd.multiplereportswebapp;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

class ReportDirectoryScannerImpl implements ReportDirectoryScanner {

    private static final String NAME_OF_THE_FILE_WITH_JENKINS_BUILD_INFO = "buildInfo.yml";

    private File dirContainingAllReports;
    private SupportNotifier supportNotifier;
    private String reportDirectoryRegexMatcher;

    ReportDirectoryScannerImpl(SupportNotifierFactory supportNotifierFactory,
                               String reportDirectoryRegexMatcher,
                               File dirContainingAllReports) {
        this.supportNotifier = supportNotifierFactory.createNewNotifierFor(ReportDirectoryScannerImpl.class);
        this.reportDirectoryRegexMatcher = reportDirectoryRegexMatcher;
        this.dirContainingAllReports = dirContainingAllReports;
    }

    @Override
    public BuildsUrl scan() {
        supportNotifier.info(startScanningDir());

        final File[] dirContainingAllReports = this.dirContainingAllReports.listFiles((dir, fileName) -> jenkinsBuildReportDirectoryNameFilter(fileName));

        final BuildsUrl buildsUrl = new BuildsUrl(Stream.of(dirContainingAllReports)
                                                      .filter(file -> buildInfoYmlFile(file, NAME_OF_THE_FILE_WITH_JENKINS_BUILD_INFO).exists())
                                                      .map(toBuildInfo())
                                                      .filter(keepNotNullValue())
                                                      .collect(toList())
        );

        supportNotifier.info(scanningDirCompleted());

        return buildsUrl;
    }

    private Predicate<TestReportInfo> keepNotNullValue() {
        return out -> out != null;
    }

    private String scanningDirCompleted() {
        return "Scanning dir completed";
    }

    private String startScanningDir() {
        return format("Start scanning dir %s", dirContainingAllReports.getAbsolutePath());
    }

    private Function<File, TestReportInfo> toBuildInfo() {
        return new Function<File, TestReportInfo>() {
            @Override
            public TestReportInfo apply(File parentFile) {
                return extractValueFromYmlFile(parentFile, NAME_OF_THE_FILE_WITH_JENKINS_BUILD_INFO);
            }

            private TestReportInfo extractValueFromYmlFile(File parentFile, String ymlFileNameToParse) {
                final File ymlFileToBeParsed = buildInfoYmlFile(parentFile, ymlFileNameToParse);
                try {
                    supportNotifier.info(ymlFileFound(ymlFileToBeParsed));
                    final Map ymlContentAsMap = (Map) ymlFileReader(ymlFileToBeParsed).read();
                    final TestReportInfo testReportInfo = new TestReportInfo((String) ymlContentAsMap.get("buildUrl"),
                                                                             parentFile.getName(),
                                                                             (String) ymlContentAsMap.get("buildPrettyName"),
                                                                             Integer.valueOf((String) ymlContentAsMap.get("numberOfBuildsToShow"))
                    );
                    supportNotifier.info(parsedFileMessage(testReportInfo));
                    return testReportInfo;
                } catch (Exception e) {
                    supportNotifier.info(problemParsing(ymlFileToBeParsed), e);
                    return null;
                }
            }

            private String problemParsing(File ymlFileToBeParsed) {
                return format("Problem parsing %s", ymlFileToBeParsed.getAbsolutePath());
            }

            private String ymlFileFound(File ymlFileToBeParsed) {
                return format("Found file %s to be parsed", ymlFileToBeParsed.getAbsolutePath());
            }

            private String parsedFileMessage(TestReportInfo testReportInfo) {
                return format("File parsed successful and found %s", testReportInfo);
            }

            private YamlReader ymlFileReader(File buildInfoFile) {
                try {
                    return new YamlReader(new FileReader(buildInfoFile.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private File buildInfoYmlFile(File file, String child) {
        return new File(file, child);
    }

    private boolean jenkinsBuildReportDirectoryNameFilter(String fileName) {
        return fileName.matches(reportDirectoryRegexMatcher);
    }

}
