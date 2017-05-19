package org.binqua.testing.csd.cucumberreports;

import com.google.common.base.Charsets;

import org.junit.Test;

import org.binqua.testing.csd.cucumberreports.model.Argument;
import org.binqua.testing.csd.cucumberreports.model.CsdDataTable;
import org.binqua.testing.csd.cucumberreports.model.CsdTableRow;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.cucumberreports.model.Match;
import org.binqua.testing.csd.cucumberreports.model.Result;
import org.binqua.testing.csd.cucumberreports.model.Scenario;
import org.binqua.testing.csd.cucumberreports.model.Status;
import org.binqua.testing.csd.cucumberreports.model.Step;
import org.binqua.testing.csd.cucumberreports.model.Tag;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MustacheReportPrinterTest {

    @Test
    public void shouldBeAbleToGenerateAReportFromAFeature() throws IOException {
        Feature feature = buildFeature();
        Path tempDirectory = Files.createTempDirectory("mustache-report-test");

        File featureReportDirectory = new File(tempDirectory.toFile(), "featureId");
        String expectedIndexHtmlContent = getResourceFile("expected-report.html");

        new MustacheReportPrinter().print(feature, featureReportDirectory);

        File actualReportFile = new File(featureReportDirectory, "/index.html");

        assertThat("file " + actualReportFile.getAbsolutePath() + " should exist", actualReportFile.exists(), is(true));

        String actualIndexHtmlContent = Files.lines(actualReportFile.toPath(), Charsets.UTF_8).collect(Collectors.joining("\n"));
        assertThat(actualIndexHtmlContent, is(expectedIndexHtmlContent));
    }

    private Feature buildFeature() {
        List<Step> firstScenarioSteps = newArrayList(
            new Step("step-1",new Match("ATMScenario.I_have_a_new_credit_card()", newArrayList()), 8, "Given ", "I have a new credit card", new Result(107447000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
            new Step("step-2",new Match("ATMScenario.I_confirm_my_pin_number()", newArrayList()), 9, "When ", "I confirm my pin number", new Result(16000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
            new Step("step-3",new Match("ATMScenario.the_card_should_be_activated()", newArrayList()), 10, "Then ", "the card should be activated", new Result(14000L, Status.FAILED, "some error message"), newArrayList(), CsdDataTable.empty())
        );

        List<Step> secondScenarioSteps = newArrayList(
                new Step("step-4", new Match("ATMScenario.createAccount(int)", newArrayList(new Argument("100", 23))), 14, "Given ", "the account balance is 100", new Result(996000L, Status.PASSED, null),
                        newArrayList("\u003cdiv\u003esome other text\u003c/div\u003e", "<div>wooops</div>"), CsdDataTable.empty()),
                new Step("step-5", new Match("ATMScenario.createCreditCard()", newArrayList()), 15, "And ", "the card is valid", new Result(30000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
                new Step("step-6", new Match("ATMScenario.createATM(int)", newArrayList(new Argument("100", 21))), 16, "And ", "the machine contains 100", new Result(58000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
                new Step("step-7", new Match("ATMScenario.requestMoney(int)", newArrayList(new Argument("10", 28))), 17, "When ", "the Account Holder requests 10", new Result(42000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
                new Step("step-8", new Match("ATMScenario.checkMoney(int)", newArrayList(new Argument("10", 24))), 18, "Then ", "the ATM should dispense 10", new Result(3220000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
                new Step("step-9", new Match("ATMScenario.checkBalance(int)", newArrayList(new Argument("90", 30))), 19, "And ", "the account balance should be 90", new Result(65000L, Status.PASSED, null), newArrayList(), CsdDataTable.empty()),
                new Step("step-10", new Match("20", newArrayList()), 20, "And ", "the card should be returned", new Result(20000L, Status.PASSED, null), newArrayList(),
                        CsdDataTable.with(
                                asList(
                                        CsdTableRow.withColumns(asList("column1", "column2")),
                                        CsdTableRow.withColumns(asList("value1", "value2"))
                                )))
        );

        List<Scenario> scenarios = newArrayList(
            new Scenario(
                "scenario-1",
                "",
                "Activate Credit Card",
                "Background",
                7,
                firstScenarioSteps,
                "background",
                newArrayList(
                    new Tag("@SetUpAndCleanUp", 12),
                    new Tag("@IE-1234", 13)
                )
            ),

            new Scenario(
                "scenario-2",
                "",
                "Account has \u0027sufficient funds\u0027",
                "Scenario",
                24,
                secondScenarioSteps,
                "scenario",
                newArrayList(
                    new Tag("@fast", 12),
                    new Tag("@super", 1),
                    new Tag("@checkout", 12)
                )
            )
        );

        return new Feature(
            "feature-1",
            newArrayList(new Tag("@super", 1)),
            "As a Account Holder\nI want to withdraw cash from an ATM\nSo that I can get money when the bank is closed",
            "Account Holder withdraws cash",
            "Feature",
            2,
            scenarios,
            "com/example/ATM.feature"
        );
    }

    @SuppressWarnings("ConstantConditions")
    private String getResourceFile(String name) {
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(name).toURI());

            return Files.lines(path, Charsets.UTF_8).collect(Collectors.joining("\n"));
        } catch (URISyntaxException |IOException|NullPointerException e) {
            throw new IllegalStateException("Failed to find resource " + name, e);
        }
    }
}