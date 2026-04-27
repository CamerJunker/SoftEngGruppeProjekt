package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Activity;
import projectmanagement.Project;
import projectmanagement.User;

public class RegisterTimeSteps {

    private Project project;
    private User user;
    private Activity activity;
    private Exception thrownException;

    public RegisterTimeSteps() {
        this.project = new Project("TestProject");
    }

    @Given("an employee with initials {string}")
    public void anEmployeeWithInitials(String initials) {
        this.user = new User(initials);
        this.project.assignUser(user);
    }

    @Given("an activity {string} exists in project with serial number {string}")
    public void anActivityExistsInProjectWithSerialNumber(String name, String serial) {
        this.activity = project.createActivity(name, 50, 1, 10, 2026, 2026, true);
    }

    private float expectedHours;

    @When("the employee registers {int} hours on the activity {string}")
    public void theEmployeeRegistersHoursOnTheActivity(Integer hours, String activityName) {
        expectedHours = hours.floatValue();
        try {
            if (activity == null || !activity.getName().equals(activityName)) {
                throw new Exception("Activity does not exist");
            }
            project.registerTime(activity, expectedHours, user);
        } catch (Exception e) {
            this.thrownException = e;
        }
    }

    @Then("the system logs the time entry")
    public void theSystemLogsTheTimeEntry() {
        try {
            float loggedHours = project.getRegisteredActivityTimeForUser(user, activity);
            assertEquals(expectedHours, loggedHours);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Then("the system returns the error {string}")
    public void theSystemReturnsTheError(String message) {
        if (thrownException == null) {
            fail("Expected exception but none was thrown");
        }
        assertEquals(message, thrownException.getMessage());
    }
}
