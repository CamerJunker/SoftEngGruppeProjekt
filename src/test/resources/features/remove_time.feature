Feature: Remove time
    As an employee
    I want to remove registered time
    So that I can correct mistakes

    Scenario: Employee removes time from an activity
        Given an employee with initials "huba"
        And an activity "Programming" exists in project with serial number "26011"
        And the employee has registered 4 hours on the activity "Programming"
        When the employee removes 2 hours from the activity "Programming"
        Then the system logs that "huba" has used 2 hours on the activity "Programming"

    Scenario: Employee tries to remove more time than registered
        Given an employee with initials "huba"
        And an activity "Programming" exists in project with serial number "26011"
        And the employee has registered 4 hours on the activity "Programming"
        When the employee removes 5 hours from the activity "Programming"
        Then the system returns the error "Cannot remove more hours than you registered"