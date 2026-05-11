Feature: Edit assigned project leader
	Description: Edit a projects assigned project leader
	Actors: Project leader

Scenario: Edit a projects project leader successfully
	Given an employee with the initials "ANDA"
	And the employee is logged in
	And there exists a project with the name "Project1"
	And the project has a project leader with the initials "ANDA"
	And there exists an employee with the initials "HUBA"
	When the employee edits the assigned project leader to "HUBA"
	Then the project has "HUBA" as project leader

Scenario: Edit a projects project leader without being the project leader
	Given an employee with the initials "HUBA"
	And the employee is logged in
	And there exists a project with the name "Project1"
	And the project has a project leader with the initials "ANDA"
	And there exists an employee with the initials "HUBA"
	When the employee edits the assigned project leader to "HUBA"
	Then the error "Employee is not the project leader" is given
	And the project has "ANDA" as project leader

Scenario: Remove a projects project leader successfully
	Given an employee with the initials "ANDA"
	And the employee is logged in
	And there exists a project with the name "Project1"
	And the project has a project leader with the initials "ANDA"
	When the employee removes the assigned project leader
	Then the project does not have a project leader

Scenario: Remove a projects project leader without being the project leader
	Given an employee with the initials "ANDA"
	And the employee is logged in
	And there exists a project with the name "Project1"
	And the project has a project leader with the initials "HUBA"
	When the employee removes the assigned project leader
	Then the error "Employee is not the project leader" is given
	And the project has "HUBA" as project leader