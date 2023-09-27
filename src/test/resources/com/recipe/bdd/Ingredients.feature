Feature: Perform the integration test on ingredients services.

  Scenario: List all ingredients test
    When the customer request to fetch all the ingredients
    Then the successful response status code 200 is returned
    And the ingredients list is not empty
