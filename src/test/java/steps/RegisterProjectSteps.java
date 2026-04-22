package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;

public class RegisterProjectSteps {
    private Main app;
    private ErrorMessageHolder errorMessageHolder;
    private String projectname;

    public RegisterProjectSteps(Main app, ErrorMessageHolder errorMessageHolder){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("the employee is logged in")
    public void theEmployeeIsLoggedIn() {
        try {
            this.app.loginUser("HUBA");
            assertTrue(this.app.CheckUserLoggedIn());
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
    }

    @When("the employee registers a project with the name {string}")
    public void theEmployeeRegistersAProjectWithTheName(String string) {
        try {
            this.app.NewProject(string);
        this.projectname = string;
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
    }

    @Then("the project is registered to the project list")
    public void theProjectIsRegisteredToTheProjectList() {
        // Check that the name of the project is in the list of projects
        assertTrue(this.app.searchProject(this.projectname));
    }

    @Given("there exists a project with the name {string}")
    public void thereExistsAProjectWithTheName(String string) {
        try {
            this.app.NewProject(string);
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
    }

    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String string) {
        assertTrue(this.errorMessageHolder.getErrorMessage().equals(string));
    }
}