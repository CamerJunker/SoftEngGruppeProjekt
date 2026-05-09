 Feature: Assign employee to activity

   Scenario: Project leader assigns an employee to an activity
     Given a project "projectname" exists
     And the project leader "PLDR" exists
     And the activity "activityname" exists for this project
     And the employee "huba" exists
     When the project leader assigns the employee "huba" to the activity "activityname"
     Then the employee "huba" should be assigned to the activity "activityname"

   Scenario: Assign employee who is already assigned
     Given the activity "already_assigned_activity_name" exists for this project
     And the employee "huba" is already assigned to the activity "already_assigned_activity_name"
     When the project leader assigns the employee "huba" to the activity "already_assigned_activity_name"
     Then the system should show an error message

   Scenario: Assign employee to non existing activity
     Given the employee "huba" exists
     When the project leader assigns the employee "huba" to a non existing activity
     Then the system should show an error message
