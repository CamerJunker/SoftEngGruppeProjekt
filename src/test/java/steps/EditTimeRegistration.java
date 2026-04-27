package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.mk_latn.No;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(this.user.getName().equalsIgnoreCase(userName));
    }
    
    @Given("the activity {string} exists")
    public void theActivityExists(String activtyName) {
        ArrayList<Activity> activities = this.project.getActivityList();
        
        boolean found = false;
        
        for (Activity activityTemp : activities) {
            if (activityTemp.getName().equals(activtyName)) {
                found = true;
            }
        }
        
        assertTrue(found);
    }
    
    @Given("the employee {string} has registered {int} hours to the activity {string}")
    public void theEmployeeHasRegisteredHoursToTheActivity(String initials, Integer hours, String activityName) {
        assertTrue(this.user.getName().equals(initials));
        assertTrue(this.activity.getName().equals(activityName));
        
        this.project.registerTime(this.activity, hours, this.user);
        
        try {
            assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == hours);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
        
    }
    
    @When("the employee {string} changes the registered time on the activity {string} to {int} hours")
    public void theEmployeeChangesTheRegisteredTimeOnTheActivityToHours(String initials, String activityName, Integer hours) {
        assertTrue(this.user.getName().equals(initials));
        assertTrue(this.activity.getName().equals(activityName));
        
        float hoursAlreadyOn = 0.0f;
        // Set time to zero so we can set it to the new time
        try {
            hoursAlreadyOn = this.project.getRegisteredActivityTimeForUser(this.user, this.activity);
        }        
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }

        try {
            this.project.removeActivityTime(this.user, this.activity, hoursAlreadyOn);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
        
        // Check that all time is removed
        try {
            assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == 0.0);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
        
        // Set it to the new time
        this.project.registerTime(this.activity, hours, this.user);
        
        try {
            assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == hours);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
    }
    
    @Then("the time registered for the activity {string} should be {int} hours")
    public void theTimeRegisteredForTheActivityShouldBeHours(String activityName, Integer hours) {
        assertTrue(this.activity.getName().equals(activityName));
        
        try {
            assertTrue(this.project.getRegisteredActivityTimeForUser(this.user, this.activity) == hours);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
    }
    
    @Given("the employee {string} has no time registered at activity {string}")
    public void theEmployeeHasNoTimeRegisteredAtActivity(String initials, String activityName) {
        assertTrue(this.user.getName().equals(initials));
        assertTrue(this.activity.getName().equals(activityName));
        
        String exceptionString = "";

        try {
            this.project.getRegisteredActivityTimeForUser(this.user, this.activity);
        }
        catch (Exception e) {
            exceptionString = e.getMessage();
        }

        assertEquals("No activity found", exceptionString);
    }
    
    @When("employee {string} tries to edit the time registration")
    public void employeeTriesToEditTheTimeRegistration(String initials) {
        assertTrue(this.user.getName().equals(initials));
        try {
            this.project.removeActivityTime(this.user, this.activity, 4);
        }
        catch (Exception e) {
            this.holder.setErrorMessage(e.getMessage());
        }
        
    }

    @Then("the system should show an error message")
    public void theSystemShouldShowAnErrorMessage() {
        System.out.println("This is the message:");
        System.out.println(this.holder.getErrorMessage());
        assertTrue(!this.holder.getErrorMessage().isBlank());
    }
}
