package projectmanagement;

import projectmanagement.Member;
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
            System.out.println("4: Register vacation days");

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
                                System.out.println("5: Select a project leader");
                                System.out.println("6: Edit project");
                                System.out.println("7: Delete project");
                                System.out.println("0: Go back");
                                String pmOptions = scanner.nextLine();
                                if (pmOptions.equals("1")) {
                                    System.out.println("Enter activity name");
                                    String activityName = scanner.nextLine();
                                    try {
                                        System.out.println("Enter budgeted time");
                                        int budgetedTime = readInt(scanner);
                                        System.out.println("Enter start day");
                                        int startDay = readInt(scanner);
                                        System.out.println("Enter start month");
                                        int startMonth = readInt(scanner);
                                        System.out.println("Enter start year");
                                        int startYear = readInt(scanner);
                                        System.out.println("Enter end day");
                                        int endDay = readInt(scanner);
                                        System.out.println("Enter end month");
                                        int endMonth = readInt(scanner);
                                        System.out.println("Enter end year");
                                        int endYear = readInt(scanner);
                                        Date startDate = new Date(startDay, startMonth, startYear);
                                        Date endDate = new Date(endDay, endMonth, endYear);
                                        project.createActivity(activityName,budgetedTime,startDate,endDate,true);
                                        System.out.println(activityName + " has been created");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    } else if (pmOptions.equals("2")) {
                                    System.out.println("Enter the activity name");
                                    String activityName = scanner.nextLine();
                                    float hoursSpent;
                                    try {
                                        System.out.println("Enter hours spent in decimal");
                                        hoursSpent = readFloat(scanner);
                                    } catch (OperationNotAllowed e) {
                                        System.out.println(e.getMessage());
                                        continue;
                                    }

                                    boolean activityExists = false;
                                    for (Activity activity : project.getActivityList()) {
                                        if (activity.getName().equalsIgnoreCase(activityName)) {
                                            try {
                                                project.registerTime(activity, hoursSpent, app.currentUser);
                                                System.out.println("Time has been registered");
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
                                } else if (pmOptions.equals("3")) {
                                    System.out.println("Enter activity name");
                                    String activityName = scanner.nextLine();
                                    float hours;
                                    try {
                                        System.out.println("Enter time to be removed");
                                        hours = readFloat(scanner);
                                    } catch (OperationNotAllowed e) {
                                        System.out.println(e.getMessage());
                                        continue;
                                    }
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
                                } else if (pmOptions.equals("0")) {
                                    projectMenu = false;
                                } else {
                                    System.out.println(pmOptions + " does not exist as an option");
                                }
                            } else {
                                System.out.println(project.getProjectLeader().getName() + " is the current project leader");
                                boolean isCurrentUserProjectLeader;
                                if(project.getProjectLeader().getName().equalsIgnoreCase(app.currentUser.getName())){
                                    isCurrentUserProjectLeader = true;
                                } else {
                                    isCurrentUserProjectLeader = false;
                                }
                                System.out.println("Choose your next action:");
                                System.out.println("1: Register activity time");
                                System.out.println("2: Remove registered activity time");
                                if(isCurrentUserProjectLeader){
                                System.out.println("3: Assign user to an activity");
                                System.out.println("4: Create new activity");
                                System.out.println("5: Register finished activity");
                                System.out.println("6: Select a new project leader");
                                System.out.println("7: Edit project");
                                System.out.println("8: Delete project");
                                System.out.println("9: Get report");
                                }
                                System.out.println("0: Main Menu");

                                String pmOption = scanner.nextLine();

                                switch(pmOption){
                                    case "1": {
                                        System.out.println("Enter the activity name");
                                        String activityName = scanner.nextLine();
                                        float hoursSpent;
                                        try {
                                            System.out.println("Enter hours spent in decimal");
                                            hoursSpent = readFloat(scanner);
                                        } catch (OperationNotAllowed e) {
                                            System.out.println(e.getMessage());
                                            break;
                                        }

                                        boolean activityExists = false;
                                        for(Activity activity : project.getActivityList()){
                                            if(activity.getName().equalsIgnoreCase(activityName)){
                                                try {
                                                    project.registerTime(activity, hoursSpent, app.currentUser);
                                                    System.out.println("Time has been registered");
                                                } catch (Exception e) {
                                                    System.out.println(e.getMessage());
                                                }
                                                activityExists = true;
                                                break;
                                            }
                                        }

                                        if(!activityExists){
                                            System.out.println("Activity does not exist");
                                        }
                                        break;
                                    }

                                    case "2": {
                                        System.out.println("Enter activity name");
                                        String activityName = scanner.nextLine();
                                        float hours;
                                        try {
                                            System.out.println("Enter time to be removed");
                                            hours = readFloat(scanner);
                                        } catch (OperationNotAllowed e) {
                                            System.out.println(e.getMessage());
                                            break;
                                        }

                                        boolean activityExists = false;
                                        for(Activity activity : project.getActivityList()){
                                            if(activity.getName().equalsIgnoreCase(activityName)){
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

                                        if(!activityExists){
                                            System.out.println("Activity does not exist");
                                        }
                                        break;
                                    }

                                    case "3": {
                                        if(isCurrentUserProjectLeader){
                                            System.out.println("Enter activity name");
                                            String activityName = scanner.nextLine();
                                            System.out.println("Enter initials of user to be assigned");
                                            String initials = scanner.nextLine();

                                            Activity foundActivity = null;

                                            for(Activity activity : project.getActivityList()){
                                                if(activity.getName().equalsIgnoreCase(activityName)){
                                                    foundActivity = activity;
                                                    break;
                                                }
                                            }

                                            if(foundActivity == null){
                                                System.out.println("Activity does not exist");
                                            } else {
                                                boolean memberExists = false;

                                                for(Member mem : project.getRegisteredMembers()){
                                                    if(mem.getUser().getName().equalsIgnoreCase(initials)){
                                                        try {
                                                            foundActivity.assignUser(mem);
                                                            System.out.println(initials + " has been assigned to " + activityName);
                                                        } catch (Exception e) {
                                                            System.out.println(e.getMessage());
                                                        }
                                                        memberExists = true;
                                                        break;
                                                    }
                                                }

                                                if(!memberExists){
                                                    System.out.println("User is not registered on this project");
                                                }
                                            }
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "4": {
                                        if(isCurrentUserProjectLeader){
                                            System.out.println("Enter activity name");
                                            String activityName = scanner.nextLine();
                                            try {
                                                System.out.println("Enter budgeted time");
                                                int budgetedTime = readInt(scanner);
                                                System.out.println("Enter start day");
                                                int startDay = readInt(scanner);
                                                System.out.println("Enter start month");
                                                int startMonth = readInt(scanner);
                                                System.out.println("Enter start year");
                                                int startYear = readInt(scanner);
                                                System.out.println("Enter end day");
                                                int endDay = readInt(scanner);
                                                System.out.println("Enter end month");
                                                int endMonth = readInt(scanner);
                                                System.out.println("Enter end year");
                                                int endYear = readInt(scanner);

                                                Date startDate = new Date(startDay, startMonth, startYear);
                                                Date endDate = new Date(endDay, endMonth, endYear);

                                                project.createActivity(activityName,budgetedTime,startDate,endDate,true);
                                                System.out.println(activityName + " has been created");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "5": {
                                        if(isCurrentUserProjectLeader){
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
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "6": {
                                        if(isCurrentUserProjectLeader){
                                            System.out.println("Enter the initials of the new project leader");
                                            String userInitials = scanner.nextLine();
                                            try {
                                                app.setProjectLeader(project.getName(), userInitials);
                                                System.out.println(userInitials + " is now project leader");
                                                projectHasLeader = true;
                                            } catch (OperationNotAllowed e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "7": {
                                        if(isCurrentUserProjectLeader){
                                            System.out.println("Enter the new name for the project");
                                            String newName = scanner.nextLine();

                                            try {
                                                app.editProjectName(project.getName(), newName);
                                                project = app.getProject(newName);
                                                System.out.println("Project name changed to " + newName);
                                            } catch (OperationNotAllowed e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "8": {
                                        if(isCurrentUserProjectLeader){
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
                                        } else {
                                            System.out.println(pmOption + " does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "9": {
                                        if(isCurrentUserProjectLeader){
                                            Report report = project.generateProjectReport(app.currentUser);

                                            System.out.println("Hours used: " + report.getHoursUsed());
                                            System.out.println("Budgeted time: " + report.getBudgedtedTime());

                                            if(report.getHoursUsed() > report.getBudgedtedTime()){
                                                System.out.println("The project is over budget by " + (report.getHoursUsed() - report.getBudgedtedTime()) + " hours");
                                            } else if(report.getHoursUsed() < report.getBudgedtedTime()){
                                                System.out.println("The project is under budget by " + (report.getBudgedtedTime() - report.getHoursUsed()) + " hours");
                                            } else {
                                                System.out.println("The project is exactly on budget");
                                            }
                                        } else {
                                            System.out.println(pmOption+" does not exist as an option");
                                        }
                                        break;
                                    }

                                    case "0": {
                                        projectMenu = false;
                                        break;
                                    }

                                    default: {
                                        System.out.println(pmOption + " does not exist as an option");
                                        break;
                                    }
                                }
                                
                            }
                        }
                    } else {
                        System.out.println("Project does not exist");
                    }
                }

            } else if(projectOptions.equals("4")){
                try {
                    System.out.println("Enter vacation day");
                    int vacationDay = readInt(scanner);
                    System.out.println("Enter vacation month");
                    int vacationMonth = readInt(scanner);
                    System.out.println("Enter vacation year");
                    int vacationYear = readInt(scanner);

                    Date vacationDate = new Date(vacationDay,vacationMonth,vacationYear);
                    app.currentUser.addVacationDate(vacationDate);
                    System.out.println("Vacation day has been registered");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
        String normalizedInitials = normalizeName(initials);
        if (searchUser(normalizedInitials)){
            this.UserLoggedInFlag = true;
            this.currentUser = this.getUser(normalizedInitials);
        } else {
            throw new OperationNotAllowed("User does not exist");
        }
    }

    // Check if user exists in preexisting list of users
    public Boolean searchUser(String UserInitials){
        String normalizedInitials = normalizeName(UserInitials);
        if (normalizedInitials == null) {
            return false;
        }

        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equalsIgnoreCase(normalizedInitials)){
                return true;
            }
        }
        return false;
    }

    // Get User as object, search by initials
    private User getUser(String UserInitials){
        String normalizedInitials = normalizeName(UserInitials);
        if (normalizedInitials == null) {
            return null;
        }

        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equalsIgnoreCase(normalizedInitials)){
                return searchUser;
            }
        }
        return null;
    }


    
    public void NewProject(String projectname) throws OperationNotAllowed{
        String normalizedProjectName = normalizeName(projectname);
        if (normalizedProjectName == null) {
            throw new OperationNotAllowed("Project name is required");
        }

        if (!this.searchProject(normalizedProjectName)){
            Project newProject = new Project(normalizedProjectName);
            this.ListOfProjects.add(newProject);
        } else {
            throw new OperationNotAllowed("Project name already exists");
        }
    }

    private Project getProject(String projectname) {
        String normalizedProjectName = normalizeName(projectname);
        if (normalizedProjectName == null) {
            return null;
        }

        for (Project project : this.ListOfProjects) {
            String searchProjectName = project.getName();
            if (searchProjectName.equalsIgnoreCase(normalizedProjectName)) {
                return project;
            }
        }
        return null;
    }

    public Boolean checkProjectLeader(String projectname, String username) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project == null) {
            throw new OperationNotAllowed("Project does not exist");
        }

        User user = this.getUser(username);
        if (user == null) {
            return false;
        }

        // If there is not already a project leader
        if (!this.projectHasProjectLeader(projectname)){
            return false;
        }

        User projectLeader = project.getProjectLeader();
        if (projectLeader.equals(user)){
            return true;
        }
        return false;
    }

    public void deleteProject(String projectname) throws OperationNotAllowed {
        if(!this.searchProject(projectname)){                               // 1
            throw new OperationNotAllowed("Project does not exist");                                                        // 2
        }

        if (!this.projectHasProjectLeader(projectname) || 
        isCurrentUserProjectLeader(projectname)){                                                                   // 3
            Project project = this.getProject(projectname);                                                  // 4
            this.ListOfProjects.remove(project);                                                      // 5
        } else if (!isCurrentUserProjectLeader(projectname)){                                                               // 6
            throw new OperationNotAllowed("Employee is not the project leader");                                               // 7
        }
    }

    public Boolean searchProject(String projectname) {
        String normalizedProjectName = normalizeName(projectname);
        if (normalizedProjectName == null) {
            return false;
        }

        for (Project project : this.ListOfProjects){
            String searchProjectName = project.getName();

            if (searchProjectName.equalsIgnoreCase(normalizedProjectName)){
                return true;
            }
        }
        return false;
    }

    public void editProjectName(String oldName, String newName) throws OperationNotAllowed {
        String normalizedOldName = normalizeName(oldName);
        String normalizedNewName = normalizeName(newName);
        if (normalizedNewName == null) {
            throw new OperationNotAllowed("Project name is required");
        }

        // If project exists and the new name does not already exist
        if (this.searchProject(normalizedOldName) && !this.searchProject(normalizedNewName)){
            Project project = this.getProject(normalizedOldName);

            // If project has no project leader
            if (!this.projectHasProjectLeader(project.getName())){
                project.editProjectName(normalizedNewName);

            // If current user is project leader
            } else if (isCurrentUserProjectLeader(project.getName())) {
                project.editProjectName(normalizedNewName);
            } else {
                throw new OperationNotAllowed("Employee is not the project leader");
            }

        } else {
            if (!this.searchProject(normalizedOldName)){
                throw new OperationNotAllowed("Project does not exist");
            }

            if (this.searchProject(normalizedNewName)) {
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
        if (project == null) {
            throw new OperationNotAllowed("Project does not exist");
        }

        User chosenUser = this.getUser(newProjectLeaderName);

        if (chosenUser == null){
            throw new OperationNotAllowed("Employee does not exist");
        }

        // If project does not have a project leader or current user is project leader
        if((!this.projectHasProjectLeader(projectname) || isCurrentUserProjectLeader(projectname))) {
            project.setProjectLeader(chosenUser);
        } else {

            // If the project has a leader and current user is not project leader
            if (this.projectHasProjectLeader(projectname) && !isCurrentUserProjectLeader(projectname)){
                throw new OperationNotAllowed("Project already has a project leader");
            }
        }
    }

    public void removeProjectLeader(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project == null) {
            throw new OperationNotAllowed("Project does not exist");
        }

        // If the project has a project leader and current user is project leader
        if (this.projectHasProjectLeader(projectname) && isCurrentUserProjectLeader(projectname)){
            project.removeProjectLeader();
        } else {
            if (!this.projectHasProjectLeader(projectname)){
                throw new OperationNotAllowed("Cannot remove project leader when no project leader is assigned");
            }

            if (!isCurrentUserProjectLeader(projectname)){
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

    private boolean isCurrentUserProjectLeader(String projectname) throws OperationNotAllowed {
        return this.currentUser != null && this.checkProjectLeader(projectname, this.currentUser.getName());
    }

    private static int readInt(Scanner scanner) throws OperationNotAllowed {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new OperationNotAllowed("Invalid number");
        }
    }

    private static float readFloat(Scanner scanner) throws OperationNotAllowed {
        String input = scanner.nextLine().trim();
        try {
            float value = Float.parseFloat(input);
            if (!Float.isFinite(value)) {
                throw new NumberFormatException("Non-finite number");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new OperationNotAllowed("Invalid number");
        }
    }

    private static String normalizeName(String name) {
        if (name == null) {
            return null;
        }

        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return null;
        }
        return trimmedName;
    }
}



