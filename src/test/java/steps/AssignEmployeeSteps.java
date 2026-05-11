package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import projectmanagement.Activity;
import projectmanagement.Date;
import projectmanagement.Project;
import projectmanagement.User;
import projectmanagement.Member;

public class AssignEmployeeSteps {

    private Project currentProject;
    private Activity currentActivity;
    private User employee;
    private User projectLeader;

    private ErrorMessageHolder errorMessageHolder;

    public AssignEmployeeSteps(ErrorMessageHolder errorMessageHolder){
        this.errorMessageHolder = errorMessageHolder;
        this.currentProject = new Project("DefaultProject");
        this.projectLeader = new User("PLDR");
        this.currentProject.setProjectLeader(this.projectLeader);
    }

    @Given("a project {string} exists")
    public void theProjectExists(String projectName) {
        this.currentProject = new Project(projectName);
    }

    @Given("the project leader {string} exists")
    public void theProjectLeaderExists(String leaderName) {
        this.projectLeader = new User(leaderName);
        this.currentProject.setProjectLeader(this.projectLeader);
    }

    @Given("the activity {string} exists for this project")
    public void theActivityExistsForThisProject(String activityName) { 
        this.currentActivity = this.currentProject.createActivity(activityName, 10, new Date(1, 1, 2026), new Date(14, 1, 2026), true);
    }

    @Given("the employee {string} is already assigned to the activity {string}")
    public void theEmployeeIsAlreadyAssignedToTheActivity(String employeeName, String activityName) {
        this.employee = new User(employeeName);
        this.currentProject.assignUser(this.employee);
        
        try {
            Member member = this.currentProject.findMemberByUser(this.employee);
            if (this.currentActivity != null && this.currentActivity.getName().equals(activityName)) {
                this.currentActivity.assignUser(member);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @When("the project leader assigns the employee {string} to the activity {string}")
    public void theProjectLeaderAssignsTheEmployeeToTheActivity(String name, String activityName) {
        try {   
            this.employee = new User(name);
            this.currentProject.assignUser(this.employee);
            Member member = this.currentProject.findMemberByUser(this.employee);

            if (this.currentActivity != null && this.currentActivity.getName().equals(activityName)) {
                this.currentActivity.assignUser(member);
            } else {
                throw new Exception("Activity does not exist");
            }
        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("the project leader assigns the employee {string} to a non existing activity")
    public void theProjectLeaderAssignsTheEmployeeToANonExistingActivity(String name) {
        try {
            this.employee = new User(name);
            this.currentProject.assignUser(this.employee);
            Member member = this.currentProject.findMemberByUser(this.employee);

            Activity nonExistingActivity = null;
            if (nonExistingActivity == null) {
                throw new Exception("Activity does not exist");
            }
            
            nonExistingActivity.assignUser(member);

        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the employee {string} should be assigned to the activity {string}")
    public void theEmployeeShouldBeAssignedToTheActivity(String employeeName, String activityName) {
        boolean isAssigned = false;

        if (this.currentActivity != null && this.currentActivity.getName().equals(activityName)) {
            for (Member member : this.currentActivity.getAssignedUsers()) {
                if (member.getUser().getName().equals(employeeName)) {
                    isAssigned = true;
                    break;
                }
            }
        }

        assertTrue(isAssigned);
    }
}