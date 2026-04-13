# Feature: Edit assigned project manager
# 	Description: Edit a projects assigned project manager
# 	Actors: Project manager

# Scenario: Edit a projects project manager successfully
# 	Given an employee with the initials “ANDA”
# 	And the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project has a project manager with the initials “ANDA”
# 	And there exists an employee with the initials “HUBA”
# 	When the employee edits the assigned project manager to “HUBA”
# 	Then the project has “HUBA” as project manager

# Scenario: Edit a projects project manager without being the project manager
# 	Given an employee with the initials “HUBA”
# 	And the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project has a project manager with the initials “ANDA”
# 	And there exists an employee with the initials “HUBA”
# 	When the employee edits the assigned project manager to “HUBA”
# 	Then the error “Employee is not the project manager” is given
# 	And the project has “ANDA” as project manager

# Scenario: Remove a projects project manager successfully
# 	Given an employee with the initials “ANDA”
# 	And the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project has a project manager with the initials “ANDA”
# 	When the employee removes the assigned project manager
# 	Then the project does not have a project manager

# Scenario: Remove a projects project manager without being the project manager
# 	Given an employee with the initials “ANDA”
# 	And the employee is logged in
# 	And there exists a project with the name “Project1”
# 	And the project has a project manager with the initials “HUBA”
# 	When the employee removes the assigned project manager
# 	Then the error “Employee is not the project manager” is given
# 	And the project has “HUBA” as project manager
