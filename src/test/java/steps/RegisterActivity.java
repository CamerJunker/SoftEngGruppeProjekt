// Enya Lin
package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Activity;

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
import projectmanagement.User;


public class RegisterActivity {
    private Main app;
    private ErrorMessageHolder errorMessageHolder;
    private ProjectInfoHolder projectInfoHolder;
    private EmployeeInfoHolder employeeInfoHolder;
    private ActivityInfoHolder activityInfoHolder;
    private Project project;
    private Activity activity;
    private Exception thrownException;

    public RegisterActivity(Main app, ErrorMessageHolder errorMessageHolder, ProjectInfoHolder projectInfoHolder, EmployeeInfoHolder employeeInfoHolder, ActivityInfoHolder activityInfoHolder){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
        this.projectInfoHolder = projectInfoHolder;
        this.employeeInfoHolder = employeeInfoHolder;
        this.activityInfoHolder = activityInfoHolder;
    }
    
    @Given("the project {string} exists in the list of projects")
    public void theProjectExistsInTheListOfProjects(String string) {
        try {
            this.app.NewProject("TestProject");
            this.projectInfoHolder.setProjectName("TestProject");
        } catch (OperationNotAllowed e) {
            // Project already exists, that's fine
        }
    }

    @Given("the activity information is given")
    public void theActivityInformationIsGiven() {
        this.activityInfoHolder.setActivityName("Activity1");
        this.activityInfoHolder.setEstimatedHours(50.0f);
        this.activityInfoHolder.setStartDay(1);
        this.activityInfoHolder.setEndDay(10);
        this.activityInfoHolder.setStartMonth(5);
        this.activityInfoHolder.setEndMonth(5);
        this.activityInfoHolder.setStartYear(2026);
        this.activityInfoHolder.setEndYear(2026);
}

    @When("the project leader registers a new activity in a project")
    public void the_project_leader_registers_a_new_activity_in_a_project() {
        try {
            Project project = this.app.getProject(this.projectInfoHolder.getProjectName());

            Date startDate = new Date(
                this.activityInfoHolder.getStartDay(),
                this.activityInfoHolder.getStartMonth(),    
                this.activityInfoHolder.getStartYear()
            );

            Date endDate = new Date(
                this.activityInfoHolder.getEndDay(),
                this.activityInfoHolder.getEndMonth(),
                this.activityInfoHolder.getEndYear()
            );

            this.activity = project.createActivity(
                this.activityInfoHolder.getActivityName(),
                this.activityInfoHolder.getEstimatedHours(),
                startDate,
                endDate,
                true
            );

        } catch (Exception e) {
            this.thrownException = e;
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("indicate start and end time of activity.")
    public void indicate_start_and_end_time_of_activity() {
        this.activityInfoHolder.setStartDay(1);
        this.activityInfoHolder.setEndDay(10);
        this.activityInfoHolder.setStartYear(2026);
        this.activityInfoHolder.setEndYear(2026);
    }
    @Then("the activities are registered to the project")
    public void the_activities_are_registered_to_the_project() {
        assertEquals(this.activityInfoHolder.getActivityName(), this.activity.getName());
        assertEquals(this.activityInfoHolder.getEstimatedHours(), this.activity.getBudgetedTime());
        assertEquals(this.activityInfoHolder.getStartDay(), this.activity.getStartDate().day);
        assertEquals(this.activityInfoHolder.getEndDay(), this.activity.getEndDate().day);
        assertEquals(this.activityInfoHolder.getStartMonth(), this.activity.getStartDate().month);
        assertEquals(this.activityInfoHolder.getEndMonth(), this.activity.getEndDate().month);
        assertEquals(this.activityInfoHolder.getStartYear(), this.activity.getStartDate().year);
        assertEquals(this.activityInfoHolder.getEndYear(), this.activity.getEndDate().year);
}
}
