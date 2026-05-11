package steps;
// Ruth Andersen

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import projectmanagement.Main;
import projectmanagement.OperationNotAllowed;

public class UnitDeleteProjectTest {

    private Main main = new Main();

    @Test
    public void beginTest(){
        System.out.println("BEGINNING UNIT TEST: deleteProject WhiteBoxTest\n");
    }

    @Test
    public void TestA(){
        System.out.println("[UNIT TEST] -> [deleteProject WhiteBoxTest]: Input Set A");

        String projectName = "ProjectTest";
        
        assertFalse(this.main.searchProject(projectName), "Test failed: " + projectName + " already exists in ListOfProjects.");

        // Login user
        try {
            this.main.loginUser("huba");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }

        // Test if program throws exception
        try {
            this.main.deleteProject(projectName);
        } catch (OperationNotAllowed e) {
            if (e.getMessage().equals("Project does not exist")){
                System.out.println("\tTest passed:\n\tException Thrown: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestB(){
        System.out.println("[UNIT TEST] -> [deleteProject WhiteBoxTest]: Input Set B");
        String projectName = "Project1";

        // Given user is logged in
        try {
        this.main.loginUser("huba");
        } catch (OperationNotAllowed e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Create project
        try {
            this.main.NewProject(projectName);
        } catch (Exception e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Assign another user as project leader
        try {            
            this.main.setProjectLeader(projectName, "ANNA");
        } catch (Exception e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Try to delete project
        try {
            this.main.deleteProject(projectName);
        } catch (Exception e) {
            if (e.getMessage().equals("Employee is not the project leader") && this.main.searchProject(projectName)){
                System.out.println("\tTest passed:\n\tException thrown: " + e.getMessage() + " and " + projectName + " still exists in ListOfProjects");
            } else {
                System.out.println("\tTest failed:\n\t" + e.getMessage());
            }
        }
    }

    @Test
    public void TestC(){
        System.out.println("[UNIT TEST] -> [deleteProject WhiteBoxTest]: Input Set C");

        String projectName = "Project2";

        // User login
        try {
        this.main.loginUser("huba");
        } catch (OperationNotAllowed e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Create project
        try {
            this.main.NewProject(projectName);
        } catch (Exception e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Make current user the project leader
        try {            
            this.main.setProjectLeader(projectName, "huba");
        } catch (Exception e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Attempt to delete project
        boolean exceptionThrown = false;
        try {
            this.main.deleteProject(projectName);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        // If no exception was thrown and projectName no longer exists in ListOfProjects
        if (!exceptionThrown && !this.main.searchProject(projectName)) {
            System.out.println("\tTest passed:\n\t" + projectName + " was deleted");
        }
    }

    @Test
    public void TestD(){
        System.out.println("[UNIT TEST] -> [deleteProject WhiteBoxTest]: Input Set D");

        String projectName = "Project3";

        // User login
        try {
        this.main.loginUser("huba");
        } catch (OperationNotAllowed e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Create project
        try {
            this.main.NewProject(projectName);
        } catch (Exception e) {
            System.out.println("\tTest failed:\n\t" + e.getMessage());
        }

        // Attempt to delete project
        boolean exceptionThrown = false;
        try {
            this.main.deleteProject(projectName);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        // If no exception was thrown and projectName no longer exists in ListOfProjects
        if (!exceptionThrown && !this.main.searchProject(projectName)) {
            System.out.println("\tTest passed:\n\t" + projectName + " was deleted");
        }
    }
}
