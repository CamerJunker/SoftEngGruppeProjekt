#Feature: Edit time registration
#
#    Scenario: Employee edits a time registration
#        Given the employee "huba" exists
#        And the activity "activityname" exists
#        And the employee "huba" has registered 3 hours to the activity "activityname"
#        When the employee "huba" changes the registered time on the activity "activityname" to 6 hours
#        Then the time registered for the activity "activityname" should be 6 hours
#
#    Scenario: Editing a non existing time registration
#        Given the employee "huba" exists
#        And the employee "huba" has no time registered at activity "activityname"
#        When employee "huba" tries to edit the time registration 
#        Then the system should show an error message