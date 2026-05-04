# Feature: Register a project leader
# 	Description: Register a project leader to project
# 	Actors: Employee

# Scenario: Register a project leader successfully
# 	Given the employee is logged in
# 	And there exists a project with the name "Project1"
# 	And the project does not have a project leader
# 	And there exists an employee with the initials "HUBA"
# 	When the employee registers an employee with initials "HUBA" as project leader
# 	Then the project has "HUBA" as project leader

# Scenario: Register a non-existent employee as project leader
# 	Given the employee is logged in
# 	And there exists a project with the name "Project1"
# 	And the project does not have a project leader
# 	And there does not exist an employee with the initials "HUBA"
# 	When the employee registers an employee with initials "HUBA" as project leader
# 	Then the error message "Employee does not exist" is given
# 	And the project does not have a project leader

# Scenario: Register a project leader to a project with existing project leader
# 	Given the employee is logged in
# 	And there exists a project with the name "Project1"
# 	And the project has a project leader with the initials "ANDA"
# 	When the employee registers an employee with the initials "HUBA" as project leader
# 	Then the error message "Project already has a project leader" is given
# 	And the project has "ANDA" as project leader
