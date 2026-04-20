Feature: Assign employee to activity

  Scenario: Project leader assigns an employee to an activity
    Given the project "projectname" exists
    And the project leader "projectleader" exists
    And the activity "activityname" exists
    And the employee "huba" exists
    When the project leader assigns the employee "huba" to the activity "activityname"
    Then the employee should be assigned to the activity "activityname"

  Scenario: Assign employee who is already assigned
    Given the activity "already_assigned_activity_name" exists
    And the employee "huba" is already assigned to the activity "already_assigned_activity_name"
   When the project leader assigns the employee "huba" to the activity "already_assigned_activity_name"
    Then the system should show an error message

  Scenario: Assign employee to non existing activity
    Given the employee "huba" exists
    When the project leader assigns the employee "huba" to a non existing activity
    Then the system should show an error message