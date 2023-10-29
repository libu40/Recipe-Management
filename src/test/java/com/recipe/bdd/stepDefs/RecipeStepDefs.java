package com.recipe.bdd.stepDefs;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class RecipeStepDefs {

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


}
