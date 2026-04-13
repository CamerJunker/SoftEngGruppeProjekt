
# Feature: Register a project manager
# 	Description: Register a project manager to project
# 	Actors: Employee

# Scenario: Register a project manager successfully
# 	Given the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project does not have a project manager
# 	And there exists an employee with the initials “HUBA”
# 	When the employee registers an employee with initials “HUBA” as project manager
# 	Then the project has “HUBA” as project manager

# Scenario: Register a non-existent employee as project manager
# 	Given the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project does not have a project manager
# 	And there does not exist an employee with the initials “HUBA”
# 	When the employee registers an employee with initials “HUBA” as project manager
# 	Then the error message “Employee does not exist” is given
# 	And the project does not have a project manager

# Scenario: Register a project manager to a project with existing project manager
# 	Given the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project has a project manager with the initials “ANDA”
# 	When the employee registers an employee with the initials “HUBA” as project manager
# 	Then the error message “Project already has a project manager” is given
# 	And the project has “ANDA” as project manager
