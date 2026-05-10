Feature: Register activity
    Description: Register activity to a project
    Actors: Project leader (if no project leader then employee)

Scenario: Register activities for a project
    Given the employee is logged in
    And the project "TestProject" exists in the list of projects
    And the activity information is given
    When the project leader registers a new activity in a project
    Then the activities are registered to the project