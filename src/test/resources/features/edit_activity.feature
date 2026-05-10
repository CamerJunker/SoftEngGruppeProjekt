Feature: Edit activity
    Description: Edit activity to a project
    Actors: Project leader

Scenario: Edit in activity
    Given the employee is logged in
    And the project exists
    And the activity exists
    When the project leader changes the estimated hours to 8.5 hours
    Then the activity estimated hours should be 8.5 hours