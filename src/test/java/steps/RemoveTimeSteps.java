package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Activity;
import projectmanagement.Project;
import projectmanagement.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RemoveTimeSteps {

    private Project project;
    private User user;
    private Activity activity;
    private Exception thrownException;

    public RemoveTimeSteps() {
        this.project = new Project("TestProject");
    }

    @Given("an employee with initials {string}")
    public void an_employee_with_initials(String initials) {
        this.user = new User(initials);
        this.project.assignUser(user);
    }

    @Given("an activity {string} exists in project with serial number {string}")
    public void an_activity_exists_in_project_with_serial_number(String name, String serial) {
        // Serial not usable with current API
        this.activity = project.createActivity(name, 50, 1, 10, 2026, 2026, true);
    }

    @Given("the employee has registered {int} hours on the activity {string}")
    public void the_employee_has_registered_hours_on_the_activity(Integer hours, String activityName) {
        this.project.registerTime(this.activity, hours, this.user);
    }

    @When("the employee removes {int} hours from the activity {string}")
    public void the_employee_removes_hours_from_the_activity(Integer hours, String activityName) {
        try {
            this.project.removeActivityTime(this.activity, hours.floatValue());
        } catch (Exception e) {
            this.thrownException = e;
        }
    }

    @Then("the system returns the error {string}")
    public void the_system_returns_the_error(String message) {
        if (this.thrownException == null) {
            fail("Expected exception but none was thrown");
        }
        assertEquals(message, this.thrownException.getMessage());
    }
}