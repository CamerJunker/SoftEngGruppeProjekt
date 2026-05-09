package steps;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Activity;
import projectmanagement.Date;
import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;
import projectmanagement.Project;

public class EditActivity {
    private Main app;
    private ProjectInfoHolder projectInfoHolder;
    private ActivityInfoHolder activityInfoHolder;
    private Activity activity;

    public EditActivity(Main app, ProjectInfoHolder projectInfoHolder, ActivityInfoHolder activityInfoHolder) {
        this.app = app;
        this.projectInfoHolder = projectInfoHolder;
        this.activityInfoHolder = activityInfoHolder;
    }

    @Given("the project exists")
    public void theProjectExists() {
        try {
        this.app.NewProject("TestProject");
        } catch (OperationNotAllowed e) {
        // already exists
        }

        this.projectInfoHolder.setProjectName("TestProject");
    }

    @Given("the activity exists")
    public void theActivityExists() {
        Project project = this.app.getProject(this.projectInfoHolder.getProjectName());

        assertTrue(project != null, "Project was not found");

        Date startDate = new Date(1, 5, 2026);
        Date endDate = new Date(10, 5, 2026);

        this.activity = project.createActivity(
            "Activity1",
            5.0f,
            startDate,
            endDate,
            true
        );

        assertTrue(this.activity != null);
    }
    @When("the project leader changes the estimated hours to {double} hours")
    public void theProjectLeaderChangesTheEstimatedHoursToHours(Double newHours) {
        this.activity.setBudgetedTime(newHours.floatValue());
    }

    @Then("the activity estimated hours should be {double} hours")
    public void theActivityEstimatedHoursShouldBeHours(Double expectedHours) {
        assertEquals(expectedHours.floatValue(), this.activity.getBudgetedTime());
    }
}
