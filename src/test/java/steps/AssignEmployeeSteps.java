package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import projectmanagement.Activity;
import projectmanagement.Project;
import projectmanagement.User;
import projectmanagement.Member;

public class AssignEmployeeSteps {

    private ArrayList<Project> projectLists = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    private Project currentProject;
    private Activity currentActivity;

    private ErrorMessageHolder errorMessageHolder;

    public AssignEmployeeSteps(ErrorMessageHolder errorMessageHolder){
        this.errorMessageHolder = errorMessageHolder;
    }

    private User findUser(String name) throws Exception{
        for(User user : users){
            if(user.getName().equals(name)){
                return user;
            } 
        }
        throw new Exception("User does not exist");
    }

    @Given("the project {string} exists")
    public void theProjectExists(String projectName) {
        currentProject = new Project(projectName);
        projectLists.add(currentProject);
    }

    @Given("the project leader {string} exists")
    public void theProjectLeaderExists(String leaderName) {
        User leader = new User(leaderName);
        users.add(leader);
        currentProject.setProjectLeader(leader);
    }

    @Given("the activity {string} exists")
    public void theActivityExists(String activityName) {
        currentActivity = currentProject.createActivity(activityName, 10, 1, 2, 2026, 2026, true);
    }

    @Given("the employee {string} exists")
    public void theEmployeeExists(String name) {
        User employee = new User(name);
        users.add(employee);
        currentProject.assignUser(employee);
    }

    @When("the project leader assigns the employee {string} to the activity {string}")
    public void theProjectLeaderAssignsTheEmployeeToTheActivity(String name, String activityName) {
        try {
            User employee = findUser(name);
            Member member = currentProject.findMemberByUser(employee);

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
        User employee = findUser(name);
        Member member = currentProject.findMemberByUser(employee);
        currentActivity.assignUser(member);
    }

    @Then("the system should show an error message")
    public void theSystemShouldShowAnErrorMessage() {
        assertTrue(this.errorMessageHolder.getErrorMessage() != null);
    }

    @When("the project leader assigns the employee {string} to a non existing activity")
    public void theProjectLeaderAssignsTheEmployeeToANonExistingActivity(String name) {
        try {
            User employee = findUser(name);
            Member member = currentProject.findMemberByUser(employee);

            Activity nonExistingActivity = null;
            nonExistingActivity.assignUser(member);

        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
}