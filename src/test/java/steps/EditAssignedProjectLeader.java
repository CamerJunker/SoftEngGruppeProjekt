package steps;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Project;
import projectmanagement.User;

public class EditAssignedProjectLeader {

    private Project project;
    private User employee;
    private ErrorMessageHolder errorMessageHolder;
    private ArrayList<User> users;

    public EditAssignedProjectLeader(ErrorMessageHolder errorMessageHolder){
        this.project = new Project("Project1");
        this.users = new ArrayList<>();
        this.errorMessageHolder = errorMessageHolder;
    }


    private void checkEmployeeIsProjectLeader() throws Exception {
    if (!this.project.getProjectLeader().getName().equals(this.employee.getName())) {
        throw new Exception("Employee is not the project leader");
    }
}

    @Given("an employee with the initials {string}")
    public void anEmployeeWithTheInitials(String string) {
        this.employee = new User(string);
        users.add(this.employee);
    }
    
    @Given("there exists an employee with the initials {string}")
    public void thereExistsAnEmployeeWithTheInitials(String initials) {
        User checkedEmployee = new User(initials);
        this.users.add(checkedEmployee);
        assertTrue(this.users.contains(checkedEmployee));
    }
    
    @Given("the project has a project leader with the initials {string}")
    public void theProjectHasAProjectLeaderWithTheInitials(String string) {
        User leader = new User(string);
        this.project.setProjectLeader(leader);
        boolean correctInitials = false;
        if(this.project.getProjectLeader()!= null && this.project.getProjectLeader().getName().equals(string)){
            correctInitials = true;
        }

        assertTrue(correctInitials,"The project leader does not have the correct initials");
    }
    
    @When("the employee edits the assigned project leader to {string}")
    public void theEmployeeEditsTheAssignedProjectLeaderTo(String initials) {
        try {
            checkEmployeeIsProjectLeader();
            this.project.setProjectLeader(new User(initials));
        } catch (Exception e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee removes the assigned project leader")
    public void theEmployeeRemovesTheAssignedProjectLeader() {
        try {
            checkEmployeeIsProjectLeader();
            this.project.removeProjectLeader();
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
        assertNotNull(this.project.getProjectLeader());
        assertEquals(expectedInitials, this.project.getProjectLeader().getName());
    }
}