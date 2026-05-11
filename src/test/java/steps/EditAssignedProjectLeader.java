package steps;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;

public class EditAssignedProjectLeader {

    private Main app;
    private ErrorMessageHolder errorMessageHolder;
    private ProjectInfoHolder projectInfoHolder;
    private EmployeeInfoHolder employeeInfoHolder;

    public EditAssignedProjectLeader(Main app, ErrorMessageHolder errorMessageHolder, ProjectInfoHolder projectInfoHolder, EmployeeInfoHolder employeeInfoHolder){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
        this.projectInfoHolder = projectInfoHolder;
        this.employeeInfoHolder = employeeInfoHolder;
    }

    @Given("an employee with the initials {string}")
    public void anEmployeeWithTheInitials(String initials) {
        this.employeeInfoHolder.setName(initials);
        try {
            this.app.loginUser(initials);
        } catch (OperationNotAllowed e) {
        }
    }
    
    @Given("there exists an employee with the initials {string}")
    public void thereExistsAnEmployeeWithTheInitials(String initials) {
    }
    
    @Given("the project has a project leader with the initials {string}")
    public void theProjectHasAProjectLeaderWithTheInitials(String leaderInitials) {
        try {
            if (!this.app.searchProject("Project1")) {
                this.app.NewProject("Project1");
            }
            this.projectInfoHolder.setProjectName("Project1");
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), leaderInitials);
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    
    @When("the employee edits the assigned project leader to {string}")
    public void theEmployeeEditsTheAssignedProjectLeaderTo(String initials) {
        try {
            this.app.editProjectLeader(this.projectInfoHolder.getProjectName(), initials);
        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee removes the assigned project leader")
    public void theEmployeeRemovesTheAssignedProjectLeader() {
        try {
            this.app.removeProjectLeader(this.projectInfoHolder.getProjectName());
        } catch (Exception e){
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    
    @Then("the error {string} is given")
    public void theErrorIsGiven(String expectedError) {
        assertEquals(expectedError, this.errorMessageHolder.getErrorMessage());
    }

    @Then("the project has {string} as project leader")
    public void theProjectHasAsProjectLeader(String expectedInitials) {
        try {
            assertTrue(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(), expectedInitials));
        } catch (OperationNotAllowed e) {
            fail("Failed to verify project leader: " + e.getMessage());
        }
    }
}
