package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Activity;
import projectmanagement.Date;
import projectmanagement.Project;
import projectmanagement.Report;
import projectmanagement.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectReportingSteps {
    private Project project;
    private User user1;
    private Report report;


    public ProjectReportingSteps() {
        this.project = new Project("TestProject");
        this.user1 = new User("USER");

        this.project.assignUser(user1);
        Activity activity = project.createActivity("Activity1", 50, new Date(1, 1, 2026), new Date(8, 3, 2026), true);
        this.project.registerTime(activity, 5, user1);

        this.project.setProjectLeader(this.user1);
    }

    @Given("a project contains activities and time registrations")
    public void aProjectContainsActivitiesAndTimeRegistrations() {
        assertTrue(!this.project.getActivityList().isEmpty());

        assertTrue(this.project.userHasActivities(this.user1));
    }
    @When("the project manager generates a report")
    public void theProjectManagerGeneratesAReport() {
        User projectManager = this.project.getProjectLeader();

        this.report = this.project.generateProjectReport(projectManager);
    }

    @Then("the system should display total time spent")
    public void theSystemShouldDisplayTotalTimeSpent() {
        assertTrue(this.report.getHoursUsed() > 0);
    }
    
    @Then("remaining budgeted time")
    public void remainingBudgetedTime() {
        assertTrue(this.report.getBudgedtedTime() - this.report.getHoursUsed() > 0);
    }
}
