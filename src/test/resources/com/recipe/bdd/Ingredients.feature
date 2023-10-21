Feature: Perform the integration test on ingredients services.

  Scenario: List all the ingredients
    When the customer request to fetch all the ingredients
    Then the successful response status code 200 is returned
    And the ingredients listed is not empty
