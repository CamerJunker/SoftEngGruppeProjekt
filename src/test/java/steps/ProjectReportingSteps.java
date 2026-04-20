package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

import projectmanagement.Activity;
import projectmanagement.Project;
import projectmanagement.User;
import projectmanagement.Report;

public class ProjectReportingSteps {
    private Project project;
    private User user1;


    ProjectReportingSteps() {
        this.project = new Project("TestProject");
        this.user1 = new User("User1");

        this.project.assignUser(user1);
        Activity activity = project.createActivity("Activity1", 50, 1, 10, 2026, 2026, true);
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

        Report report = this.project.generateProjectReport(projectManager);


        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the system should display total time spent")
    public void theSystemShouldDisplayTotalTimeSpent() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("remaining budgeted time")
    public void remainingBudgetedTime() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
