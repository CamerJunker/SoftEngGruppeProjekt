package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import projectmanagement.Project;
import projectmanagement.User;
import projectmanagement.Activity;


public class EditTimeRegistration {
    private Project project;
    private User user;
    private Activity activity;
    private ErrorMessageHolder holder;

    public EditTimeRegistration(ErrorMessageHolder holder) {
        this.holder = holder;
        this.project = new Project("Project1");
        this.user = new User("HUBA");

        this.project.assignUser(this.user);
        this.activity = this.project.createActivity("activityname", 5, 1, 2, 2026, 2026, true);
    }

    @Given("the employee {string} exists")
    public void theEmployeeExists(String userName) {
        assertTrue(this.user.getName() == userName);
    }
    
    @Given("the activity {string} exists")
    public void theActivityExists(String activtyName) {
        ArrayList<Activity> activities = this.project.getActivityList();
        
        boolean found = false;
        
        for (Activity activity : activities) {
            if (activity.getName() == activtyName) {
                found = true;
            }
        }
        
        assertTrue(found);
    }
    
    @Given("the employee {string} has registered {int} hours to the activity {string}")
    public void theEmployeeHasRegisteredHoursToTheActivity(String initials, Integer hours, String activityName) {
        assertTrue(this.user.getName() == initials);

        assertTrue(this.activity.getName() == activityName);
        
        this.project.registerTime(this.activity, hours, this.user);
        
        assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == hours);
        
    }
    
    @When("the employee {string} changes the registered time on the activity {string} to {int} hours")
    public void theEmployeeChangesTheRegisteredTimeOnTheActivityToHours(String initials, String activityName, Integer hours) {
        assertTrue(this.user.getName() == initials);
        
        assertTrue(this.activity.getName() == activityName);
        
        try {
            this.project.removeActivityTime(this.user, this.activity, hours);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
        
        
        
    }
    
    @Then("the time registered for the activity {string} should be {int} hours")
    public void theTimeRegisteredForTheActivityShouldBeHours(String activityName, Integer hours) {
        assertTrue(this.activity.getName() == activityName);
        
        assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == hours);
    }

    @Given("the employee {string} has no time registered at activity {string}")
    public void theEmployeeHasNoTimeRegisteredAtActivity(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("employee {string} tries to edit the time registration")
    public void employeeTriesToEditTheTimeRegistration(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system should show an error message")
    public void theSystemShouldShowAnErrorMessage() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
