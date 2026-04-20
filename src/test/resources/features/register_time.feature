Feature: Register time
    As an employee
    I want to register time on activities
    So that my work is tracked

    Scenario: Employee registers time on an existing activity
        Given an employee with initials "huba"
        And an activity "Programming" exists in project with serial number "26011"
        When the employee registers 2 hours on the activity "Programming"
        Then the system logs the time entry

    Scenario: Employee registers time on a non-existing activity
        Given an employee with initials "huba"
        When the employee registers 2 hours on the activity "Work"
        Then the system returns the error "Activity does not exist"