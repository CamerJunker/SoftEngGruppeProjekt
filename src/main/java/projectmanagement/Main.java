package projectmanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();
    private ArrayList<Project> ListOfProjects = new ArrayList<>();


    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Main app = new Main();
        System.out.println("Intern SoftwareHuset program");
        while (true) {
            if (!app.CheckUserLoggedIn()) {
                System.out.println("LOGIN MENU");
                System.out.println("Type in your initials to login");

                String initials = scanner.nextLine();
                try{
                    app.loginUser(initials);
                    System.out.println(app.currentUser.getName() + " logged in");
                    
                } catch (OperationNotAllowed e){
                    System.out.println(e.getMessage());
                    break;
                }
            } else {
                System.out.println("MAIN MENU");
                System.out.println("Choose your next action:");
                System.out.println("1: See Projects");
                System.out.println("2: Make a new project");
                System.out.println("3: Select a project");
                String projectOptions = scanner.nextLine();
                if(projectOptions.equals("1")){
                    if (app.ListOfProjects.isEmpty()) {
                        System.out.println("There is currently no projects ongoing");   
                    } else {
                        for(Project projects : app.ListOfProjects){
                            System.out.println(projects.getName());
                        }
                    }
                    
                } else if(projectOptions.equals("2")){
                    System.out.println("Enter the name of your new project");
                    String newProjectName = scanner.nextLine();
                    Project newProject = new Project(newProjectName);
                    app.ListOfProjects.add(newProject);
                } else if(projectOptions.equals("3")){
                        if(app.ListOfProjects.isEmpty()){
                            System.out.println("There is no active projects");
                        } else {
                        System.out.println("Type in the name of the project you wish to select (case sensitive)");
                        String projectName = scanner.nextLine();
                        boolean projectExists = false;
                        for(Project project : app.ListOfProjects){
                            if(project.getName().equals(projectName));
                            projectExists = true;
                        }
                        if (projectExists) {
                            System.out.println(projectName + "Has been selected");
                            Project project = app.getProject(projectName);
                            boolean projectHasLeader = project.hasProjectLeader();
                            boolean projectMenu = true;
                            while (projectMenu) {
                                if (!projectHasLeader) {
                                    System.out.println("This Project currently does not have a project leader");
                                    System.out.println("Choose your next action:");
                                    System.out.println("1: Create new activity");
                                    System.out.println("2: Register activity time");
                                    System.out.println("3: Remove registered activity time");
                                    System.out.println("4: Register finished activity");
                                    System.out.println("5: Edit project");
                                    System.out.println("6: Delete project");
                                    String pmOptions = scanner.nextLine(); 
                                    if (pmOptions.equals("1")) {
                                        System.out.println("Enter activity name");
                                        String activityName = scanner.nextLine();
                                        System.out.println("Enter budgeted time");
                                        int budgetedTime = scanner.nextInt();
                                        System.out.println("Enter start week");
                                        int startWeek = scanner.nextInt();
                                        System.out.println("Enter end week");
                                        int endWeek = scanner.nextInt();
                                        System.out.println("Enter year start");
                                        int startYear = scanner.nextInt();
                                        System.out.println("Enter year end");
                                        int endYear = scanner.nextInt();
                                        project.createActivity(activityName,budgetedTime,startWeek,endWeek,startYear,endYear,true);
                                    } else if (pmOptions.equals("2")) {
                                        System.out.println("Enter the activity name");
                                        String activityName = scanner.nextLine();
                                        System.out.println("Enter hours spent (in decimal)");
                                        float hoursSpent = scanner.nextFloat();

                                        for(Activity activity : project.getActivityList()){
                                            if(activity.getName().equals(activityName)){
                                                project.registerTime(activity, hoursSpent, app.currentUser);
                                            } else {
                                                System.out.println("Activity does not exist");
                                            }
                                        }
                                    } else if (pmOptions.equals("3")){
                                        System.out.println("Enter activity name");
                                        String activityName = scanner.nextLine();
                                        System.out.println("Enter time to be removed");
                                        Float hours = scanner.nextFloat();

                                        for(Activity activity : project.getActivityList()){
                                            if(activity.getName().equals(activityName)){
                                                try{
                                                project.removeActivityTime(app.currentUser,activity,hours);
                                                } catch (Exception e){
                                                     System.out.println(e.getMessage());
                                                }
                                            } 
                                        }
                                    } else if (pmOptions.equals("4")){
                                        System.out.println("Enter the activity name");
                                        String activityName = scanner.nextLine();
                                        System.out.println("Enter hours spent (in decimal)");
                                        float hoursSpent = scanner.nextFloat();

                                        for(Activity activity : project.getActivityList()){
                                            if(activity.getName().equals(activityName)){
                                                project.acti
                                            } else {
                                                System.out.println("Activity does not exist");
                                            }
                                        }
                                } else {
                                
                                }                     
                            }
                        }
                    }
                } else {
                    System.out.println(projectOptions + " Does not exist as an option");
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
    private Boolean searchUser(String UserInitials){
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



