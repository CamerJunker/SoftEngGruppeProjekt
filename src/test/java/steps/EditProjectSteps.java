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

    @Given("the employee is the project leader of the project")
    public void theEmployeeIsTheProjectLeaderOfTheProject() {
        // Check if current user, currently logged in, is the projectleader
        try {
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), this.employeeInfoHolder.getName());
            assertTrue(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(), this.employeeInfoHolder.getName()), "Could not set current employee as project leader.");
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

    // @Then("the project is removed from the project list")
    // public void theProjectIsRemovedFromTheProjectList() {
    //     assertFalse(this.app.searchProject(this.projectInfoHolder.getProjectName()));
    // }

    @Given("the employee is not the project leader of the project")
    public void theEmployeeIsNotTheProjectLeaderOfTheProject() {
        try {
            assertFalse(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(),this.employeeInfoHolder.getName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    // @Then("the project still exists in the project list")
    // public void theProjectStillExistsInTheProjectList() {
    //     assertTrue(this.app.searchProject(this.projectInfoHolder.getProjectName()));
    // }

    @When("the employee edits the project name to {string}")
    public void theEmployeeEditsTheProjectNameTo(String newName) {
        try {
            this.app.editProjectName(this.projectInfoHolder.getProjectName(), newName);
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("the project has a project leader")
    public void theProjectHasAProjectLeader() {
        try {
            // Make an existing employee the project leader, the initials are not the same as current user, so we use a string "ANNA"
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), "ANNA");
            assertTrue(this.app.projectHasProjectLeader(this.projectInfoHolder.getProjectName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    // @Then("the projects name is {string}")
    // public void theProjectsNameIs(String projectName) {
    //     assertTrue(this.app.searchProject(projectName));
    // }

    @Given("the project does not have a project leader")
    public void theProjectDoesNotHaveAProjectLeader() {
        try {
            if(this.app.projectHasProjectLeader(this.projectInfoHolder.getProjectName())){
                this.app.removeProjectLeader(this.projectInfoHolder.getProjectName());
            }
            // Check that project leader is removed
            assertFalse(this.app.projectHasProjectLeader(this.projectInfoHolder.getProjectName()));
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("there does not exist a project with the name {string}")
    public void thereDoesNotExistAProjectWithTheName(String projectname) {
        // Remove project with this name, if it exists
        try {
            if(this.app.searchProject(projectname)){
                String projectLeaderName = this.app.getProjectLeaderName(projectname);
                this.app.loginUser(projectLeaderName);
                this.app.removeProjectLeader(projectname);
                this.app.loginUser(this.employeeInfoHolder.getName());
            }
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project {string} does not exist")
    public void theProjectDoesNotExist(String string) {
        assertFalse(this.app.searchProject(string));
    }

    @Then("the project {string} exists")
    public void theProjectExists(String string) {
        assertTrue(this.app.searchProject(string));
    }
}
