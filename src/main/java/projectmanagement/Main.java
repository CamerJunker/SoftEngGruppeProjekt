package projectmanagement;

import java.util.ArrayList;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();
    private ArrayList<Project> ListOfProjects = new ArrayList<>();

    public Main(){
        // Users must be made before, as there should not be a function to add a user.
        String[] ListOfInitials = {"HUBA"};
        for (String name : ListOfInitials){
            User newUser = new User(name);
            this.ListOfUsers.add(newUser);
        }
    }

    public Boolean CheckUserLoggedIn(){
        return UserLoggedInFlag;
    }

    public void loginUser(String initials) throws OperationNotAllowed {
        if (searchUser(initials)){
            this.UserLoggedInFlag = true;
            this.currentUser = this.getUser(initials); 
        } else {
            throw new OperationNotAllowed("User does not exist");
        }
    }

    // Check if user exist in preexisting list of users
    private Boolean searchUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equals(UserInitials)){
                return true;
            }
        }
        return false;
    }

    // Get User as object, search by initials
    private User getUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equals(UserInitials)){
                return searchUser;
            }
        }
        return null;
    }

    public void NewProject(String projectname) throws OperationNotAllowed{
        if (!this.searchProject(projectname)){
            Project newProject = new Project(projectname);
        this.ListOfProjects.add(newProject);
        } else {
            throw new OperationNotAllowed("Project name already exists");
        }
    }

    public Boolean searchProject(String projectname) {
        for (Project project : this.ListOfProjects){
            String searchProjectName = project.getName();

            if (searchProjectName.equals(projectname)){
                return true;
            }
        }
        return false;
    }
}



