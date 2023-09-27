package com.recipe.bdd.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/recipe/bdd/", plugin = {"pretty", "html:target\\cucumber"}, glue = {"com.recipe.bdd"})
public class CucumberIntegrationTest {
}
