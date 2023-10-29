package com.recipe.bdd.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class IngredientStepDefs {

    private final static String BASE_URI = "http://localhost/restaurant/api";

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
        validatableResponse = requestSpecification().contentType(MediaType.APPLICATION_JSON).when().get("/ingredients").then().log().ifValidationFails();
    }

    @Then("the successful response status code {int} is returned")
    public void theSuccessfulResponseStatusCodeIsReturned(int statusCode) {
        validatableResponse.assertThat().statusCode(statusCode);
    }

    @And("the ingredients listed is not empty")
    public void theIngredientListIsNotEmpty() {
        validatableResponse.assertThat().body("size()", greaterThan(0));
    }

    @When("the customer request to create an ingredient")
    public void theCustomerRequestToCreateAnIngredient() {
        String requestBody = "{\n  \"name\": \"Ginger\",\n  \"recipes\": [\n    {\n      \"name\": \"EggRice\",\n      \"instruction\": \"Cook\",\n      \"variant\": \"Vegetarian\",\n      \"servingCount\": 5\n    }\n  ]\n}";
        validatableResponse = requestSpecification().contentType(MediaType.APPLICATION_JSON).body(requestBody).when().post("/ingredients").then().log().ifValidationFails();
    }

    @When("the customer request the ingredient for the id {int}")
    public void theCustomerRequestTheIngredientForTheId(int ingredientId) {
        validatableResponse = requestSpecification().contentType(MediaType.APPLICATION_JSON).when().get("/ingredients/{ingredientId}", ingredientId).then().log().ifError();
    }

    @And("the ingredient returned has the name {string}")
    public void theIngredientReturnedHasTheName(String ingredientName) {
        validatableResponse.assertThat().body("name", equalTo(ingredientName));
    }

    @When("the customer request to list all the ingredients in the descending order on the {string} attribute with {int} as page size")
    public void theCustomerRequestToListAllTheIngredientsInTheDescendingOrderOnTheAttributeWithAsPageSize(String attributeName, int pageSize) {
        validatableResponse = requestSpecification().contentType(MediaType.APPLICATION_JSON).when().get("/ingredients/page?pageNo=0&pageSize={pageSize}&sortBy=DESC&attribute={attributeName}", pageSize, attributeName).then().log().ifStatusCodeIsEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @And("the ingredients listed are in the paginated order with size {int}")
    public void theIngredientsListedAreInThePaginatedOrderWithSize(int pageSize) {
        validatableResponse.assertThat().body("size()", equalTo(pageSize));
    }
}
