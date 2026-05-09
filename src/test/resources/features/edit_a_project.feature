Feature: Edit a project
	Description: Edit a project from the project list
	Actors: Project leader

Scenario: Delete a project successfully
	Given the employee is logged in
	And there exists a project with the name "Project1"
	And the employee is the project leader of the project
	When the employee deletes the project
	And the project "Project1" does not exist

Scenario: Delete a project without being the project leader
	Given the employee is logged in
	And there exists a project with the name "Project32"
	And the employee is not the project leader of the project
	When the employee deletes the project
	Then the error message "Employee is not the project leader" is given
	And the project "Project32" exists

Scenario: Edit project name successfully
	Given the employee is logged in
	And there exists a project with the name "Project3"
	And there does not exist a project with the name "Project4"
	And the employee is the project leader of the project
	When the employee edits the project name to "Project4"
	Then the project "Project3" does not exist
	And the project "Project4" exists

Scenario: Edit project name to existing project name
	Given the employee is logged in
	And there exists a project with the name "Project5"
	And there exists a project with the name "Project6"
	And the employee is the project leader of the project
	When the employee edits the project name to "Project5"
	Then the error message "Duplicate project name" is given
	And the project "Project5" exists
	And the project "Project6" exists

Scenario: Edit project name without being the project leader
	Given the employee is logged in
	And there does not exist a project with the name "Project7"
	And there exists a project with the name "Project8"
	And the project has a project leader
	And the employee is not the project leader of the project
	When the employee edits the project name to "Project7"
	Then the error message "Employee is not the project leader" is given	
	And the project "Project7" does not exist
	And the project "Project8" exists

Scenario: Edit project name when there is no project leader
	Given the employee is logged in
	And there does not exist a project with the name "Project9"
	And there exists a project with the name "Project10"
	And the project does not have a project leader
	And the employee edits the project name to "Project9"
	Then the project "Project9" exists
	And the project "Project10" does not exist