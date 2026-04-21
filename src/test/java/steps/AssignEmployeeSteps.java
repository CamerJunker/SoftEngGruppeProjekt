import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.lu.a;
import projectmanagement.Project;
import projectmanagement.User;

class AssignEmployeeSteps {

    private ArrayList<Project> projectLists = new ArrayList<>();
    private ArrayList<User> projectLeaders = new ArrayList<>();

        @Given("the project {string} exists")
        public void theProjectExists(String projectName) {
            Project newProject = new Project(projectName);
            projectLists.add(newProject);
        }

        @Given("the project leader {string} exists")
        public void theProjectLeaderExists(String leaderName) {
            User newLeader = new User();
            projectLeaders.add(newLeader);
        }

        @Given("the activity {string} exists")
        public void theActivityExists(String string) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @Given("the employee {string} exists")
        public void theEmployeeExists(String string) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @When("the project leader assigns the employee {string} to the activity {string}")
        public void theProjectLeaderAssignsTheEmployeeToTheActivity(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @Then("the employee should be assigned to the activity {string}")
        public void theEmployeeShouldBeAssignedToTheActivity(String string) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @Given("the employee {string} is already assigned to the activity {string}")
        public void theEmployeeIsAlreadyAssignedToTheActivity(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @Then("the system should show an error message")
        public void theSystemShouldShowAnErrorMessage() {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }

        @When("the project leader assigns the employee {string} to a non existing activity")
        public void theProjectLeaderAssignsTheEmployeeToANonExistingActivity(String string) {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
    }
}
