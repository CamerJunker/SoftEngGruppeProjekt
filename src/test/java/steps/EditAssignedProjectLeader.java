package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Project;
import projectmanagement.User;

public class EditAssignedProjectLeader {

    private User employee;
    private Project project;

    public EditAssignedProjectLeader(){
        this.project = new Project("Project1");
    }

    @Given("an employee with the initials {string}")
    public void anEmployeeWithTheInitials(String initial) {
        this.employee = new User(initial);
        
    }

    @Given("the project has a project leader with the initials {string}")
    public void theProjectHasAProjectLeaderWithTheInitials(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("there exists an employee with the initials {string}")
    public void thereExistsAnEmployeeWithTheInitials(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the employee edits the assigned project leader to {string}")
    public void theEmployeeEditsTheAssignedProjectLeaderTo(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the project has {string} as project leader")
    public void theProjectHasAsProjectLeader(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the error {string} is given")
    public void theErrorIsGiven(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the employee removes the assigned project leader")
    public void theEmployeeRemovesTheAssignedProjectLeader() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the project does not have a project leader")
    public void theProjectDoesNotHaveAProjectLeader() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}