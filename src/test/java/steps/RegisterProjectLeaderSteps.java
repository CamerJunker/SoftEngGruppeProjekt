package steps;
// Ruth Andersen

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;

public class RegisterProjectLeaderSteps {
    
    private EmployeeInfoHolder employeeInfoHolder;
    private ProjectInfoHolder projectInfoHolder;
    private ErrorMessageHolder errorMessageHolder;
    private Main app;

    public RegisterProjectLeaderSteps(EmployeeInfoHolder employeeInfoHolder, ProjectInfoHolder projectInfoHolder, ErrorMessageHolder errorMessageHolder, Main app){
        this.employeeInfoHolder = employeeInfoHolder;
        this.projectInfoHolder = projectInfoHolder;
        this.errorMessageHolder = errorMessageHolder;
        this.app = app;
    }
    
    @When("the employee registers an employee with initials {string} as project leader")
    public void theEmployeeRegistersAnEmployeeWithInitialsAsProjectLeader(String initials) {
        try {
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), initials);
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has employee with initials {string} as project leader")
    public void theProjectHasEmployeeWithInitialsAsProjectLeader(String initials) {
        Boolean flag = false;
        try {
            assertTrue(this.app.checkProjectLeader(this.projectInfoHolder.getProjectName(), initials));
            flag = true;
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(flag, "Exception received: " + this.errorMessageHolder.getErrorMessage());
    }

    @Given("there does not exist an employee with the initials {string}")
    public void thereDoesNotExistAnEmployeeWithTheInitials(String username) {
        assertFalse(this.app.searchUser(username));
    }

    @Given("the project has {string} as  project leader")
    public void theProjectHasAsProjectLeader(String initials) {
        try {
            this.app.setProjectLeader(this.projectInfoHolder.getProjectName(), initials);
        } catch (OperationNotAllowed e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
            assertTrue(false, "Exception received: " + this.errorMessageHolder.getErrorMessage());
        }
    }

}
