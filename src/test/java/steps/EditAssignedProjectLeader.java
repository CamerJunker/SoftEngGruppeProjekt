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

}