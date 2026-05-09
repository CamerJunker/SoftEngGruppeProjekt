package projectmanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();
    private ArrayList<Project> ListOfProjects = new ArrayList<>();


public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Main app = new Main();

    System.out.println("Intern SoftwareHuset program");

    while (true) {
        if (!app.CheckUserLoggedIn()) {
            System.out.println("LOGIN MENU");
            System.out.println("Type in your initials to login");

            String initials = scanner.nextLine();

            try {
                app.loginUser(initials);
                System.out.println(app.currentUser.getName() + " logged in");
            } catch (OperationNotAllowed e) {
                System.out.println(e.getMessage());
                break;
            }

        } else {
            System.out.println("MAIN MENU");
            System.out.println("Choose your next action:");
            System.out.println("1: See Projects");
            System.out.println("2: Make a new project");
            System.out.println("3: Select a project");
            // System.out.println("4: Register vacation days");

            String projectOptions = scanner.nextLine();

            if (projectOptions.equals("1")) {
                if (app.ListOfProjects.isEmpty()) {
                    System.out.println("There are currently no projects ongoing");
                } else {
                    for (Project project : app.ListOfProjects) {
                        System.out.println(project.getName());
                    }
                }

            } else if (projectOptions.equals("2")) {
                System.out.println("Enter the name of your new project");
                String newProjectName = scanner.nextLine();

                try {
                    app.NewProject(newProjectName);
                    System.out.println(newProjectName + " has been created");
                } catch (OperationNotAllowed e) {
                    System.out.println(e.getMessage());
                }

            } else if (projectOptions.equals("3")) {
                if (app.ListOfProjects.isEmpty()) {
                    System.out.println("There are no active projects");
                } else {
                    System.out.println("Type in the name of the project you wish to select");
                    String projectName = scanner.nextLine();

                    if (app.searchProject(projectName)) {
                        Project project = app.getProject(projectName);
                        System.out.println(project.getName() + " has been selected");

                        boolean projectMenu = true;

                        while (projectMenu) {
                            boolean projectHasLeader = false;

                            try {
                                projectHasLeader = app.projectHasProjectLeader(project.getName());
                            } catch (OperationNotAllowed e) {
                                System.out.println(e.getMessage());
                                break;
                            }

                            if (!projectHasLeader) {
                                System.out.println("This project currently does not have a project leader");
                                System.out.println("Choose your next action:");
                                System.out.println("1: Create new activity");
                                System.out.println("2: Register activity time");
                                System.out.println("3: Remove registered activity time");
                                System.out.println("4: Register finished activity");
                                System.out.println("5: Select a project manager");
                                System.out.println("6: Edit project");
                                System.out.println("7: Delete project");
                                System.out.println("8: Go back");
                                String pmOptions = scanner.nextLine();
                                if (pmOptions.equals("1")) {
                                    System.out.println("Enter activity name");
                                    String activityName = scanner.nextLine();
                                    System.out.println("Enter budgeted time");
                                    int budgetedTime = scanner.nextInt();
                                    System.out.println("Enter start day");
                                    int startDay = scanner.nextInt();
                                    System.out.println("Enter start month");
                                    int startMonth = scanner.nextInt();
                                    System.out.println("Enter start year");
                                    int startYear = scanner.nextInt();
                                    System.out.println("Enter end day");
                                    int endDay = scanner.nextInt();
                                    System.out.println("Enter end month");
                                    int endMonth = scanner.nextInt();
                                    System.out.println("Enter end year");
                                    int endYear = scanner.nextInt();
                                    scanner.nextLine();
                                    Date startDate = new Date(startDay, startMonth, startYear);
                                    Date endDate = new Date(endDay, endMonth, endYear);
                                    project.createActivity(activityName,budgetedTime,startDate,endDate,true);
                                    System.out.println(activityName + " has been created");
                                } else if (pmOptions.equals("2")) {
                                    System.out.println("Enter the activity name");
                                    String activityName = scanner.nextLine();
                                    System.out.println("Enter hours spent in decimal");
                                    float hoursSpent = scanner.nextFloat();
                                    
                                    boolean activityExists = false;
                                    for (Activity activity : project.getActivityList()) {
                                        if (activity.getName().equalsIgnoreCase(activityName)) {
                                            project.registerTime(activity, hoursSpent, app.currentUser);
                                            activityExists = true;
                                            System.out.println("Time has been registered");
                                            break;
                                        }
                                    }
                                    if (!activityExists) {
                                        System.out.println("Activity does not exist");
                                    }
                                } else if (pmOptions.equals("3")) {
                                    System.out.println("Enter activity name");
                                    String activityName = scanner.nextLine();
                                    System.out.println("Enter time to be removed");
                                    float hours = scanner.nextFloat();
                                    scanner.nextLine();
                                    boolean activityExists = false;
                                    for (Activity activity : project.getActivityList()) {
                                        if (activity.getName().equalsIgnoreCase(activityName)) {
                                            try {
                                                project.removeActivityTime(app.currentUser, activity, hours);
                                                System.out.println("Time has been removed");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            activityExists = true;
                                            break;
                                        }
                                    }
                                    if (!activityExists) {
                                        System.out.println("Activity does not exist");
                                    }
                                } else if (pmOptions.equals("4")) {
                                    System.out.println("Enter the activity name");
                                    String activityName = scanner.nextLine();
                                    boolean activityExists = false;
                                    for (Activity activity : project.getActivityList()) {
                                        if (activity.getName().equalsIgnoreCase(activityName)) {
                                            activity.setStatus(false);
                                            System.out.println(activity.getName() + " is now inactive");
                                            activityExists = true;
                                            break;
                                        }
                                    }

                                    if (!activityExists) {
                                        System.out.println("Activity does not exist");
                                    }

                                } else if (pmOptions.equals("5")) {
                                    System.out.println("Enter the initials of the new project leader");
                                    String userInitials = scanner.nextLine();
                                    try {
                                        app.setProjectLeader(project.getName(), userInitials);
                                        System.out.println(userInitials + " is now project leader");
                                        projectHasLeader = true;
                                    } catch (OperationNotAllowed e) {
                                        System.out.println(e.getMessage());
                                    }

                                } else if (pmOptions.equals("6")) {
                                    System.out.println("Enter the new name for the project");
                                    String newName = scanner.nextLine();

                                    try {
                                        app.editProjectName(project.getName(), newName);
                                        project = app.getProject(newName);
                                        System.out.println("Project name changed to " + newName);
                                    } catch (OperationNotAllowed e) {
                                        System.out.println(e.getMessage());
                                    }

                                } else if (pmOptions.equals("7")) {
                                    System.out.println("Deleting the project is an irreversible action");
                                    System.out.println("Enter Y if you wish to delete");
                                    String decision = scanner.nextLine();
                                    if (decision.equalsIgnoreCase("Y")) {
                                        try {
                                            String deletedProjectName = project.getName();
                                            app.deleteProject(project.getName());
                                            System.out.println(deletedProjectName + " has been deleted");
                                            projectMenu = false;
                                        } catch (OperationNotAllowed e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                } else if (pmOptions.equals("8")) {
                                    projectMenu = false;
                                } else {
                                    System.out.println(pmOptions + " does not exist as an option");
                                }
                            } else {
                                System.out.println(project.getProjectLeader().getName() + "is the current project leader");
                                
                            }
                        }

                    } else {
                        System.out.println("Project does not exist");
                    }
                }

            } else if(projectOptions.equals("4")){
                
            } else {
                System.out.println(projectOptions + " does not exist as an option");
            }
        }
    }
    scanner.close();
}


    public Main(){
        // Users must be made before, as there should not be a function to add a user.
        String[] ListOfInitials = {"HUBA", "ANDA", "ANNA"};
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
    public Boolean searchUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equalsIgnoreCase(UserInitials)){
                return true;
            }
        }
        return false;
    }

    // Get User as object, search by initials
    private User getUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equalsIgnoreCase(UserInitials)){
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
        if (this.searchProject(projectname) && 
        (!this.projectHasProjectLeader(projectname)) || 
        this.checkProjectLeader(projectname, this.currentUser.getName())){                              // 1
            Project project = this.getProject(projectname);                                             // 2
            this.ListOfProjects.remove(project);                                                        // 3
        } else {
            if (!this.searchProject(projectname)){                                                      // 4
                throw new OperationNotAllowed("Project does not exist");                  // 5
            }

            if (!this.checkProjectLeader(projectname, this.currentUser.getName())){                     // 6
                throw new OperationNotAllowed("Employee is not the project leader");      // 7
            }
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
                throw new OperationNotAllowed("Employee is not the project leader");
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

        if (chosenUser == null){
            throw new OperationNotAllowed("Employee does not exist");
        }

        // If project does not have a project leader or current user is project leader
        if((!this.projectHasProjectLeader(projectname) || this.checkProjectLeader(projectname, this.currentUser.getName()))) {
            project.setProjectLeader(chosenUser);
        } else {

            // If the project has a leader and current user is not project leader
            if (this.projectHasProjectLeader(projectname) && !this.checkProjectLeader(projectname, this.currentUser.getName())){
                throw new OperationNotAllowed("Project already has a project leader");
            }
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



