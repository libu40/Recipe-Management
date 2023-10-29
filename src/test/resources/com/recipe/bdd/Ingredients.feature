Feature: Perform the BDD test on ingredients services.

  Scenario: List all the ingredients
    When the customer request to fetch all the ingredients
    Then the successful response status code 200 is returned
    And the ingredients listed is not empty

  Scenario: Create an ingredient
    When the customer request to create an ingredient
    Then the successful response status code 201 is returned

  Scenario: Get ingredient by id
    When the customer request the ingredient for the id 1
    Then the successful response status code 200 is returned
    And the ingredient returned has the name "Flour"

  Scenario: List all the ingredients in a paginated sorted order
  When the customer request to list all the ingredients in the descending order on the "name" attribute with 2 as page size
    Then the successful response status code 200 is returned
    And the ingredients listed are in the paginated order with size 2
