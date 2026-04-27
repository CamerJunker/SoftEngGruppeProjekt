Feature: Edit a project
	Description: Edit a project from the project list
	Actors: Project manager

Scenario: Delete a project successfully
	Given the employee is logged in
	And there exists a project with the name "Project1"
	And the employee is the project manager of the project
	When the employee deletes the project
	Then the project is removed from the project list

Scenario: Delete a project without being the project manager
	Given the employee is logged in
	And there exists a project with the name "Project2"
	And the employee is not the project manager of the project
	When the employee deletes the project
	Then the error message "Employee is not the project manager" is given
	And the project still exists in the project list

Scenario: Edit project name successfully
	Given the employee is logged in
	And there exists a project with the name "Project2"
	And there exists a project with the name "Project1"
	And the employee is the project manager of the project
	When the employee edits the project name to "Project2"
	Then the error message "Duplicate project name" is given

Scenario: Edit project name without being the project manager
	Given the employee is logged in
	And there exists a project with the name "Project1"
	And the project has a project manager
	And the employee is not the project manager of the project
	When the employee edits the project name to "Project2"
	Then the error message "Employee is not the project manager" is given
	And the projects name is "Project1"

Scenario: Edit project name when there is no project manager
	Given the employee is logged in
	And there exists a project with the name "Project"
	And the project does not have a project manager
	And the employee edits the project name to "Project2"
	Then the projects name is "Project2"
