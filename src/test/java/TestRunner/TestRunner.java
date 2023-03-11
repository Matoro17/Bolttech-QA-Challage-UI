package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"Features/bolttech.feature"},
        glue = "StepDefinitions/StepDefinitions"
)
public class TestRunner { }