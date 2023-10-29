Feature: Perform the BDD test on the recipe services.

  Scenario: List all the recipes.
    When the customer request to fetch all the recipes
    Then the successful response status code 200 is returned
    And the recipes listed are not empty
