package com.recipe.bdd.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class IngredientStepDefs {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private String port;

    private ValidatableResponse validatableResponse;

    private void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = Integer.parseInt(port);
    }

    protected RequestSpecification requestSpecification() {
        configureRestAssured();
        return given();
    }

    @When("the customer request to fetch all the ingredients")
    public void theCustomerRequestToFetchAllTheIngredients() {
        System.out.println("Check we land here");
    }

    @Then("the successful response status code {int} is returned")
    public void theSuccessfulResponseStatusCodeIsReturned(int statusCode) {
        System.out.println("Check we land here");
    }

    @And("the ingredients listed is not empty")
    public void theIngredientListIsNotEmpty() {
        System.out.println("Check we land here");
    }
}
