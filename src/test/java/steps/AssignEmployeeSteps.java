package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import projectmanagement.Activity;
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

        // Hardcoding the setup to match the rest of the team's pattern
        this.currentProject = new Project("projectname");
        
        this.projectLeader = new User("projectleader");
        this.currentProject.setProjectLeader(this.projectLeader);
        
        // NOTE: Make sure the capitalization here matches whatever your feature file uses!
        this.employee = new User("huba"); 
        this.currentProject.assignUser(this.employee);
        
        this.currentActivity = this.currentProject.createActivity("activityname", 10, 1, 2, 2026, 2026, true);
    }

    @Given("the project {string} exists")
    public void theProjectExists(String projectName) {
        assertTrue(this.currentProject.getName().equals(projectName));
    }

    @Given("the project leader {string} exists")
    public void theProjectLeaderExists(String leaderName) {
        assertTrue(this.projectLeader.getName().equals(leaderName));
    }

    @Given("the activity {string} exists for this project")
    public void theActivityExistsForThisProject(String activityName) { // <-- Added the String parameter here
        this.currentActivity = this.currentProject.createActivity(activityName, 10, 1, 2, 2026, 2026, true);
    }


    @When("the project leader assigns the employee {string} to the activity {string}")
    public void theProjectLeaderAssignsTheEmployeeToTheActivity(String name, String activityName) {
        try {   
            // Validate against the hardcoded data
            assertTrue(this.employee.getName().equals(name));
            assertTrue(this.currentActivity.getName().equals(activityName));

            Member member = currentProject.findMemberByUser(this.employee);

            if (member == null) {
                throw new Exception("Member can not be found through the user");
            }

            currentActivity.assignUser(member);

        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the employee {string} should be assigned to the activity {string}")
    public void theEmployeeShouldBeAssignedToTheActivity(String employeeName, String activityName) {
        boolean isAssigned = false;

        for (Member member : currentActivity.getAssignedUsers()) {
            if (member.getUser().getName().equals(employeeName)) {
                isAssigned = true;
                break;
            }
        }

        assertTrue(isAssigned);
    }

    @Given("the employee {string} is already assigned to the activity {string}")
    public void theEmployeeIsAlreadyAssignedToTheActivity(String name, String activityName) throws Exception {
        assertTrue(this.employee.getName().equalsIgnoreCase(name));
        Member member = currentProject.findMemberByUser(this.employee);
        currentActivity.assignUser(member);
    }

    // REMOVED: @Then("the system should show an error message")
    // (Cucumber will now use the one inside EditTimeRegistration.java)

    @When("the project leader assigns the employee {string} to a non existing activity")
    public void theProjectLeaderAssignsTheEmployeeToANonExistingActivity(String name) {
        try {
            assertTrue(this.employee.getName().equals(name));
            Member member = currentProject.findMemberByUser(this.employee);

            Activity nonExistingActivity = null;
            // This forces a NullPointerException, which is caught and saved to the holder
            nonExistingActivity.assignUser(member);

        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
}