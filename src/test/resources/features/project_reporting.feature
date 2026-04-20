Feature: Project reporting
	As a project manager
	I want to generate reports
	So that I can track project progress

	Scenario: Generate project report
		Given a project contains activities and time registrations
		When the project manager generates a report
		Then the system should display total time spent
		And remaining budgeted time