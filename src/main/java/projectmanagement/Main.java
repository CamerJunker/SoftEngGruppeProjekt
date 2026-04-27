package projectmanagement;

import java.util.ArrayList;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();
    private ArrayList<Project> ListOfProjects = new ArrayList<>();

    public Main(){
        // Users must be made before, as there should not be a function to add a user.
        String[] ListOfInitials = {"HUBA", "ANNA"};
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

    // Check if user exists in preexisting list of users
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

    private Project getProject(String projectname) {
        for (Project project : this.ListOfProjects) {
            String searchProjectName = project.getName();
            if (searchProjectName.equals(projectname)) {
                return project;
            }
        }
        return null;
    }

    public Boolean checkProjectLeader(String projectname, String username) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        User user = this.getUser(username);
        User projectLeader = project.getProjectLeader();

        // If there is not already a project leader
        if (!this.projectHasProjectLeader(projectname)){
            return false;
        }

        if (projectLeader.equals(user)){
            return true;
        }
        return false;
    }

    public void deleteProject(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project != null && this.checkProjectLeader(projectname, this.currentUser.getName())){
            this.ListOfProjects.remove(project);
        } else {
            if (project == null){
                throw new OperationNotAllowed("Project does not exist");
            }

            if (!this.checkProjectLeader(projectname, this.currentUser.getName())){
                throw new OperationNotAllowed("Employee is not the project manager");
            }
        }
    }

    public void editProjectName(String oldName, String newName) throws OperationNotAllowed {
        // If project exists and the new name does not already exist
        if (this.searchProject(oldName) && !this.searchProject(newName)){
            Project project = this.getProject(oldName);

            // If project has no project leader
            if (!this.projectHasProjectLeader(project.getName())){
                project.editProjectName(newName);

            // If current user is project leader
            } else if (this.checkProjectLeader(project.getName(), this.currentUser.getName())) {
                project.editProjectName(newName);
            } else {
                throw new OperationNotAllowed("Employee is not the project manager");
            }

        } else {
            if (!this.searchProject(oldName)){
                throw new OperationNotAllowed("Project does not exist");
            }

            if (this.searchProject(newName)) {
                throw new OperationNotAllowed("Duplicate project name");
            }
        }
    }

    public Boolean projectHasProjectLeader(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project == null){
            throw new OperationNotAllowed("Project does not exist");
        }

        if (project.hasProjectLeader()){
            return true;
        }
        return false;
    }

    public void setProjectLeader(String projectname, String newProjectLeaderName) throws OperationNotAllowed{
        Project project = this.getProject(projectname);
        User chosenUser = this.getUser(newProjectLeaderName);

        // If project does not have a project leader or current user is project leader
        if(!this.projectHasProjectLeader(projectname) || this.checkProjectLeader(projectname, this.currentUser.getName())) {
            project.setProjectLeader(chosenUser);
        }
    }

    public void removeProjectLeader(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        // If the project has a project leader and current user is project leader
        if (this.projectHasProjectLeader(projectname) && this.checkProjectLeader(projectname, this.currentUser.getName())){
            project.removeProjectLeader();
        } else {
            if (!this.projectHasProjectLeader(projectname)){
                throw new OperationNotAllowed("Cannot remove project leader when no project leader is assigned");
            }

            if (!this.checkProjectLeader(projectname, this.currentUser.getName())){
                throw new OperationNotAllowed("Employee is not the project manager");
            }
        }
    }

    public String getProjectLeaderName(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if(project != null && this.projectHasProjectLeader(projectname)){
            User projectleader = project.getProjectLeader();
            return projectleader.getName();
        } else {
            return "";
        }
    }
}



