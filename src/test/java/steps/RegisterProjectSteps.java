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
    private ProjectInfoHolder projectInfoHolder;
    private EmployeeInfoHolder employeeInfoHolder;

    public RegisterProjectSteps(Main app, ErrorMessageHolder errorMessageHolder, ProjectInfoHolder projectInfoHolder, EmployeeInfoHolder employeeInfoHolder){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
        this.projectInfoHolder = projectInfoHolder;
        this.employeeInfoHolder = employeeInfoHolder;
    }

    @Given("the employee is logged in")
    public void theEmployeeIsLoggedIn() {
        this.employeeInfoHolder.setName("HUBA");
        try {
            this.app.loginUser(this.employeeInfoHolder.getName());
            assertTrue(this.app.CheckUserLoggedIn());
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
    }

    @When("the employee registers a project with the name {string}")
    public void theEmployeeRegistersAProjectWithTheName(String string) {
        try {
            this.app.NewProject(string);
            this.projectInfoHolder.setProjectName(string);
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
    }

    @Then("the project is registered to the project list")
    public void theProjectIsRegisteredToTheProjectList() {
        // Check that the name of the project is in the list of projects
        assertTrue(this.app.searchProject(this.projectInfoHolder.getProjectName()));
    }

    @Given("there exists a project with the name {string}")
    public void thereExistsAProjectWithTheName(String string) {
        Boolean flag = false;
        try {
            this.projectInfoHolder.setProjectName(string);
            if (!this.app.searchProject(string)){
                this.app.NewProject(this.projectInfoHolder.getProjectName());
            }
            
            flag = true;
        } catch (OperationNotAllowed exception) {
            this.errorMessageHolder.setErrorMessage(exception.getMessage());
        }
        assertTrue(flag, "Failed to create project in step definition. Exception: " + this.errorMessageHolder.getErrorMessage());
    }

    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String string) {
        assertTrue(this.errorMessageHolder.getErrorMessage().equals(string), "Received instead: " + this.errorMessageHolder.getErrorMessage());
    }
}