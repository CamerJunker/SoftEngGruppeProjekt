# Feature: Register a project
# 	Description: Register project to the project list
# 	Actors: Employee

# Scenario: Register a project successfully
# 	Given the employee is logged in
# 	When the employee registers a project with the name "Project1"
# 	Then the project is registered to the project list

# Scenario: Register a project with already existing project name
# 	Given the employee is logged in
# 	And there exists a project with the name "Project1"
# 	When the employee registers a project with the name "Project1"
# 	Then the error message "Project name already exists" is given