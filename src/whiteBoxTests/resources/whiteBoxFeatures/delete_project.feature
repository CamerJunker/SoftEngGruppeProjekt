Feature: Whitebox test of deleteProject
    Description: Testing method deleteProject
    Actors: Employee

Scenario: ListOfProjects does not contain a project with the name projectname
    Given a project with the name "ProjectTest"
    And ListOfProjects does not contain a project with the name "ProjectTest"
    And the employee is logged in
    When the employee deletes project with the name "ProjectTest"
    Then an exception with the message "Employee is not the project leader" is thrown