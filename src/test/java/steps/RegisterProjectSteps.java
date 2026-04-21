package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterProjectSteps {
    @Given("the employee is logged in")
    public void theEmployeeIsLoggedIn() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Given("there exists a project with the name “Project1”")
    public void thereExistsAProjectWithTheNameProject1() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Given("the project does not have a project manager")
    public void theProjectDoesNotHaveAProjectManager() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Given("there exists an employee with the initials “HUBA”")
    public void thereExistsAnEmployeeWithTheInitialsHUBA() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @When("the employee registers an employee with initials “HUBA” as project manager")
    public void theEmployeeRegistersAnEmployeeWithInitialsHUBAAsProjectManager() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Then("the project has “HUBA” as project manager")
    public void theProjectHasHUBAAsProjectManager() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @Given("an employee with initials {string}")
    public void anEmployeeWithInitials(String string) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Given("an activity {string} exists in project with serial number {string}")
    public void anActivityExistsInProjectWithSerialNumber(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Given("the employee has registered {int} hours on the activity {string}")
    public void theEmployeeHasRegisteredHoursOnTheActivity(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @When("the employee removes {int} hours from the activity {string}")
    public void theEmployeeRemovesHoursFromTheActivity(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @Then("the system returns the error {string}")
    public void theSystemReturnsTheError(String string) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @When("the employee registers a project with the name “Project1”")
    public void theEmployeeRegistersAProjectWithTheNameProject1() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @Then("the project is registered to the project list")
    public void theProjectIsRegisteredToTheProjectList() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
}