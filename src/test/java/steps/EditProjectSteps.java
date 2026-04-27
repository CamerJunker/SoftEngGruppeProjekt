package steps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;

public class EditProjectSteps {
    private Main app;
    private ErrorMessageHolder errorMessageHolder;
    private ProjectInfoHolder projectInfoHolder;
    private EmployeeInfoHolder employeeInfoHolder;

    public EditProjectSteps(Main app, ErrorMessageHolder errorMessageHolder, ProjectInfoHolder projectInfoHolder, EmployeeInfoHolder employeeInfoHolder){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
        this.projectInfoHolder = projectInfoHolder;
        this.employeeInfoHolder = employeeInfoHolder;
    }

    @Given("the employee is the project manager of the project")
    public void theEmployeeIsTheProjectManagerOfTheProject() {
        // Check if current user, currently logged in, is the projectleader
        try {
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), this.employeeInfoHolder.getName());
            assertTrue(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(), this.employeeInfoHolder.getName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee deletes the project")
    public void theEmployeeDeletesTheProject() {
        try {
            this.app.deleteProject(this.projectInfoHolder.getProjectName());
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project is removed from the project list")
    public void theProjectIsRemovedFromTheProjectList() {
        assertFalse(this.app.searchProject(this.projectInfoHolder.getProjectName()));
    }

    @Given("the employee is not the project manager of the project")
    public void theEmployeeIsNotTheProjectManagerOfTheProject() {
        try {
            assertFalse(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(),this.employeeInfoHolder.getName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project still exists in the project list")
    public void theProjectStillExistsInTheProjectList() {
        assertTrue(this.app.searchProject(this.projectInfoHolder.getProjectName()));
    }

    @When("the employee edits the project name to {string}")
    public void theEmployeeEditsTheProjectNameTo(String newName) {
        try {
            this.app.editProjectName(this.projectInfoHolder.getProjectName(), newName);
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("the project has a project manager")
    public void theProjectHasAProjectManager() {
        try {
            // Make an existing employee the project manager, the initials are not the same as current user, so we use a string "ANNA"
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), "ANNA");
            assertTrue(this.app.projectHasProjectLeader(this.projectInfoHolder.getProjectName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the projects name is {string}")
    public void theProjectsNameIs(String projectName) {
        assertTrue(this.app.searchProject(projectName));
    }

    @Given("the project does not have a project manager")
    public void theProjectDoesNotHaveAProjectManager() {
        try {
            assertFalse(this.app.projectHasProjectLeader(this.projectInfoHolder.getProjectName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
}
