package com.recipe.bdd.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

public class IngredientStepDefs {

    @LocalServerPort
    private String port;

    private ResponseEntity<String> responseEntity;

    @When("the customer request to fetch all the ingredients")
    public void theCustomerRequestToFetchAllTheIngredients() {
        System.out.println("Check we land here");
    }

    @Then("the successful response status code {int} is returned")
    public void theSuccessfulResponseStatusCodeIsReturned(int statusCode) {
        System.out.println("Check we land here");
    }

    @And("the ingredients list is not empty")
    public void theIngredientListIsNotEmpty() {
        System.out.println("Check we land here");
    }
}
