package projectmanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();
    private ArrayList<Project> ListOfProjects = new ArrayList<>();

// Abdellah El Ghazzaz & Ruth Andersen
static String showMainMenu(Scanner scanner){
    System.out.println("MAIN MENU");
    System.out.println("Choose your next action:");
    System.out.println("1: See Projects");
    System.out.println("2: Make a new project");
    System.out.println("3: Select a project");
    System.out.println("4: Register vacation days");
    System.out.println("5: Show vacation days");
    System.out.println("6: Join project");

    String projectOptions = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    return projectOptions;
}

// Abdellah El Ghazzaz & Ruth Andersen
// Main menu options
static void seeProjectsOption(Main app){
    if (app.ListOfProjects.isEmpty()) {
        System.out.println("There are currently no projects ongoing");
    } else {
        for (Project project : app.ListOfProjects) {
            System.out.println(project.getName());
        }
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void makeNewProjectOption(Scanner scanner, Main app){
    System.out.println("Enter the name of your new project");
    String newProjectName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    try {
        app.NewProject(newProjectName);
        System.out.println(newProjectName + " has been created");
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void selectAProjectOption(Scanner scanner, Main app){
    if (app.ListOfProjects.isEmpty()) {
        System.out.print("\033[H\033[2J");
        System.out.println("There are no active projects");
    } else {
        System.out.println("Type in the name of the project you wish to select");
        String projectName = scanner.nextLine();

        if (app.searchProject(projectName)) {
            Project project = app.getProject(projectName);
            System.out.println(project.getName() + " has been selected");

            // Project menu opens
            showProjectMenu(scanner, app, project);

        } else {
            System.out.println("Project does not exist");
        }
    }
}

// Abdellah El Ghazzaz & Alexander Van Steenwijk
static void joinProjectOption(Scanner scanner, Main app){
    if (app.ListOfProjects.isEmpty()) {
        System.out.print("\033[H\033[2J");
        System.out.println("There are no active projects");
        return;
    }

    System.out.println("Enter the name of the project you want to join");
    String projectName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    try {
        app.assignUserToProject(projectName, app.currentUser.getName());
        Project project = app.getProject(projectName);
        System.out.println(app.currentUser.getName() + " has been assigned to " + project.getName());
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
// No project leader project menu options
static void noProjectLeaderCreateNewActivityOption(Scanner scanner, Project project){
    System.out.println("Enter activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    try {
        System.out.println("Enter budgeted time (in hours)");
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
    
}

// Abdellah El Ghazzaz & Ruth Andersen
static void noProjectLeaderRegisterActivityTimeOption(Scanner scanner, Main app, Project project){
    System.out.println("Enter the activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    
    if (!project.activityExists(activityName)) {
        System.out.println("Activity does not exist");
        return;
    }
    float hoursSpent = 0.0f;
    try {
        System.out.println("Enter hours spent in decimal");
        hoursSpent = readFloat(scanner);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
        return;
    }

    Activity activity = project.findActivityByName(activityName);
    
    try {
        project.registerTime(activity, hoursSpent, app.currentUser);
        System.out.println("Time has been registered");
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void noProjectLeaderRemoveRegisteredActivityTimeOption(Scanner scanner, Main app, Project project) {
    System.out.println("Enter activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    if(!project.activityExists(activityName)){
        System.out.println("Activity does not exist");
        return;
    }

    Activity activity = project.findActivityByName(activityName);

    float hours = 0.0f;
    try {
        System.out.println("Enter time to be removed");
        hours = readFloat(scanner);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
        return;
    }

    try {
        project.removeActivityTime(app.currentUser, activity, hours);
        System.out.println("Time has been removed");
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void noProjectLeaderRegisterFinishedActivityOption(Scanner scanner, Project project){
    System.out.println("Enter the activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    if(project.activityExists(activityName)){
        Activity activity = project.findActivityByName(activityName);
        activity.setStatus(false);
        System.out.println(activity.getName() + " is now inactive");
    } else {
        System.out.println("Activity does not exist");
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static Boolean noProjectLeaderSelectAProjectLeaderOption(Scanner scanner, Main app, Project project, Boolean projectHasLeader){
    System.out.println("Enter the initials of the new project leader");
    String userInitials = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    try {
        app.setProjectLeader(project.getName(), userInitials);
        System.out.println(userInitials + " is now project leader");
        projectHasLeader = true;
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
    return projectHasLeader;
}

// Abdellah El Ghazzaz & Ruth Andersen
static Project noProjectLeaderEditProjectOption(Scanner scanner, Main app, Project project){
    System.out.println("Enter the new name for the project");
    String newName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    try {
        app.editProjectName(project.getName(), newName);
        project = app.getProject(newName);
        System.out.println("Project name changed to " + newName);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
    return project;
}

// Abdellah El Ghazzaz & Ruth Andersen
static Boolean noProjectLeaderDeleteProjectOption(Scanner scanner, Main app, Project project, Boolean projectMenu){
    System.out.println("Deleting the project is an irreversible action");
    System.out.println("Enter Y if you wish to delete");
    String decision = scanner.nextLine();
    System.out.print("\033[H\033[2J");
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
    return projectMenu;
}

// Abdellah El Ghazzaz & Alexander Van Steenwijk
static void assignUserToProjectOption(Scanner scanner, Main app, Project project){
    System.out.println("Enter initials of user to assign to this project");
    String initials = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    try {
        app.assignUserToProject(project.getName(), initials);
        System.out.println(initials + " has been assigned to " + project.getName());
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
// no project leader project menu
static Boolean[] noProjectLeaderProjectMenu(Scanner scanner, Main app, Project project, Boolean projectMenu, Boolean projectHasLeader){
    System.out.println("This project currently does not have a project leader");
    System.out.println("Choose your next action:");
    System.out.println("1: Create new activity");
    System.out.println("2: Register activity time");
    System.out.println("3: Remove registered activity time");
    System.out.println("4: Register finished activity");
    System.out.println("5: Select a project leader");
    System.out.println("6: Edit project");
    System.out.println("7: Delete project");
    System.out.println("8: Assign user to project");
    System.out.println("0: Go back");
    String pmOptions = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    
    if (pmOptions.equals("1")) {
        // no project leader create new activity option
        noProjectLeaderCreateNewActivityOption(scanner, project);

    } else if (pmOptions.equals("2")) {
        // no project leader register activity time option
        noProjectLeaderRegisterActivityTimeOption(scanner, app, project);

    } else if (pmOptions.equals("3")) {
        // no project leader remove registered activity time option
        noProjectLeaderRemoveRegisteredActivityTimeOption(scanner, app, project);
        
    } else if (pmOptions.equals("4")) {
        // no project leader register finished activity
        noProjectLeaderRegisterFinishedActivityOption(scanner, project);

    } else if (pmOptions.equals("5")) {
        // no project leader select a project leader option
        projectHasLeader = noProjectLeaderSelectAProjectLeaderOption(scanner, app, project, projectHasLeader);

    } else if (pmOptions.equals("6")) {
        // no project leader Edit project option
        project = noProjectLeaderEditProjectOption(scanner, app, project);

    } else if (pmOptions.equals("7")) {

        // no project leader delete project option
        projectMenu = noProjectLeaderDeleteProjectOption(scanner, app, project, projectMenu);

    } else if (pmOptions.equals("8")) {
        // no project leader assign user to project option
        assignUserToProjectOption(scanner, app, project);
        
    } else if (pmOptions.equals("0")) {
        projectMenu = false;
    } else {
        System.out.println(pmOptions + " does not exist as an option");
    }

    // Package boolean options to return
    Boolean[] projectBooleans = {projectHasLeader, projectMenu};
    return projectBooleans;
}

// Abdellah El Ghazzaz & Ruth Andersen
// Project leader project menu options
static void projectLeaderRemoveRegisteredTimeOption(Scanner scanner, Main app, Project project){
    System.out.println("Enter activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    
    Boolean activityExists = false;
    if(!project.activityExists(activityName)){
        System.out.println("Activity does not exist");
        return;
    } else {
        activityExists = true;
    }

    if (activityExists){
        float hours = 0.0f;
        try {
            System.out.println("Enter time to be removed");
            hours = readFloat(scanner);
        } catch (OperationNotAllowed e) {
            System.out.println(e.getMessage());
            return;
        }

        Activity activity = project.findActivityByName(activityName);

        try {
            project.removeActivityTime(app.currentUser, activity, hours);
            System.out.println("Time has been removed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void projectLeaderAssignUserToAnActivityOption(Scanner scanner, Project project, String pmOption){
    System.out.println("Enter activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    Boolean activityExists = false;

    if(!project.activityExists(activityName)){
        System.out.println("Activity does not exist");
    } else {
        activityExists = true;
    }

    if (activityExists) {
        System.out.println("Enter initials of user to be assigned");
        String initials = scanner.nextLine();

        Activity foundActivity = project.findActivityByName(activityName);

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
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void projectLeaderCreateNewActivityOption(Scanner scanner, Project project, String pmOption){
    System.out.println("Enter activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
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
}

// Abdellah El Ghazzaz & Ruth Andersen
static void projectLeaderRegisterFinishedActivityOption(Scanner scanner, Project project, String pmOption){
    System.out.println("Enter the activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
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
}

// Abdellah El Ghazzaz & Ruth Andersen
static Boolean projectLeaderSelectANewProjectLeaderOption(Scanner scanner, Main app, Project project, String pmOption, Boolean projectHasLeader){
        System.out.println("Enter the initials of the new project leader");
        String userInitials = scanner.nextLine();
        System.out.print("\033[H\033[2J");
        try {
            app.setProjectLeader(project.getName(), userInitials);
            System.out.println(userInitials + " is now project leader");
            projectHasLeader = true;
        } catch (OperationNotAllowed e) {
            System.out.println(e.getMessage());
        }
    return projectHasLeader;
}

// Abdellah El Ghazzaz & Ruth Andersen
static Project projectLeaderEditProjectOption(Scanner scanner, Main app, Project project, String pmOption){
    System.out.println("Enter the new name for the project");
    String newName = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    try {
        app.editProjectName(project.getName(), newName);
        project = app.getProject(newName);
        System.out.println("Project name changed to " + newName);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
    return project;
}

// Abdellah El Ghazzaz & Ruth Andersen
static Boolean projectLeaderDeleteProjectOption(Scanner scanner, Main app, Project project, String pmOption, Boolean projectMenu){
    System.out.println("Deleting the project is an irreversible action");
    System.out.println("Enter Y if you wish to delete");
    String decision = scanner.nextLine();
    System.out.print("\033[H\033[2J");
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
    return projectMenu;
}

// Abdellah El Ghazzaz & Ruth Andersen
static void projectLeaderGetReportOption(Main app, Project project, String pmOption){
    Report report;
    try {
        report = project.generateProjectReport(app.currentUser);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
        return;
    }

    System.out.println("Hours used: " + report.getHoursUsed());
    System.out.println("Budgeted time: " + report.getBudgedtedTime());

    if(report.getHoursUsed() > report.getBudgedtedTime()){
        System.out.println("The project is over budget by " + (report.getHoursUsed() - report.getBudgedtedTime()) + " hours");
    } else if(report.getHoursUsed() < report.getBudgedtedTime()){
        System.out.println("The project is under budget by " + (report.getBudgedtedTime() - report.getHoursUsed()) + " hours");
    } else {
        System.out.println("The project is exactly on budget");
    }
}

// Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
// Project leader project menu
static Boolean[] projectLeaderProjectMenu(Scanner scanner, Main app, Project project, Boolean projectMenu, Boolean projectHasLeader){
    System.out.println(project.getProjectLeader().getName() + " is the current project leader");
    System.out.println("Choose your next action:");
    System.out.println("1: Register activity time");
    System.out.println("2: Remove registered activity time");
    System.out.println("3: Assign user to an activity");
    System.out.println("4: Create new activity");
    System.out.println("5: Register finished activity");
    System.out.println("6: Select a new project leader");
    System.out.println("7: Edit project");
    System.out.println("8: Delete project");
    System.out.println("9: Get report");
    System.out.println("10: Assign user to project");
    System.out.println("0: Main Menu");

    String pmOption = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    switch(pmOption){
        case "1": {
            // project leader register time option
            projectLeaderRegisterTimeOption(scanner, project, app);
            break;
        } case "2": {
            // project leader remove registered time option
            projectLeaderRemoveRegisteredTimeOption(scanner, app, project);
            break;
        } case "3": {
            // project leader assign user to an activity option
            projectLeaderAssignUserToAnActivityOption(scanner, project, pmOption);
            break;
        } case "4": {
            // project leader create new activity option
            projectLeaderCreateNewActivityOption(scanner, project, pmOption);
            break;
        } case "5": {
            // project leader Register Finished activity option
            projectLeaderRegisterFinishedActivityOption(scanner, project, pmOption);
            break;
        } case "6": {
            // project leader select a new project leader option
            projectHasLeader = projectLeaderSelectANewProjectLeaderOption(scanner, app, project, pmOption, projectHasLeader);
            break;
        } case "7": {
            // project leader Edit Project option
            project = projectLeaderEditProjectOption(scanner, app, project, pmOption);
            break;
        } case "8": {
            // project leader delete project option
            projectMenu = projectLeaderDeleteProjectOption(scanner, app, project, pmOption, projectMenu);
            break;
        } case "9": {
            // project leader get report option
            projectLeaderGetReportOption(app, project, pmOption);
            break;
        } case "10": {
            // project leader assign user to project option
            assignUserToProjectOption(scanner, app, project);
            break;
        } case "0": {
            projectMenu = false;
            break;
        } default: {
            System.out.println(pmOption + " does not exist as an option");
            break;
        }
    }
    Boolean[] projectBooleans = {projectHasLeader, projectMenu};
    return projectBooleans;
}

// Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
static Boolean[] projectMemberProjectMenu(Scanner scanner, Main app, Project project, Boolean projectMenu, Boolean projectHasLeader){
    System.out.println("Project leader: " + project.getProjectLeader().getName());
    System.out.println("Choose your next action:");
    System.out.println("1: Register activity time");
    System.out.println("2: Remove registered activity time");
    System.out.println("0: Main Menu");

    String pmOption = scanner.nextLine();
    System.out.print("\033[H\033[2J");

    switch(pmOption){
        case "1": {
            noProjectLeaderRegisterActivityTimeOption(scanner, app, project);
            break;
        } case "2": {
            noProjectLeaderRemoveRegisteredActivityTimeOption(scanner, app, project);
            break;
        } case "0": {
            projectMenu = false;
            break;
        } default: {
            System.out.println(pmOption + " does not exist as an option");
            break;
        }
    }
    Boolean[] projectBooleans = {projectHasLeader, projectMenu};
    return projectBooleans;
}

// Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
// Project menu
static void showProjectMenu(Scanner scanner, Main app, Project project){
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
            // No project leader project menu shows
            Boolean[] projectBooleans = noProjectLeaderProjectMenu(scanner, app, project, projectMenu, projectHasLeader);
            projectHasLeader = projectBooleans[0];
            projectMenu = projectBooleans[1];

        } else {
            try {
                if (app.isCurrentUserProjectLeader(project.getName())) {
                    // Project leader project menu shows only if current user is project leader
                    Boolean[] projectBooleans = projectLeaderProjectMenu(scanner, app, project, projectMenu, projectHasLeader);
                    projectHasLeader = projectBooleans[0];
                    projectMenu = projectBooleans[1];
                } else {
                    // Regular project menu shows when another employee is project leader
                    Boolean[] projectBooleans = projectMemberProjectMenu(scanner, app, project, projectMenu, projectHasLeader);
                    projectHasLeader = projectBooleans[0];
                    projectMenu = projectBooleans[1];
                }
            } catch (OperationNotAllowed e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void registerVacationDaysOption(Scanner scanner, Main app){
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
}

// Abdellah El Ghazzaz & Ruth Andersen
static void projectLeaderRegisterTimeOption(Scanner scanner, Project project, Main app){
    System.out.println("Enter the activity name");
    String activityName = scanner.nextLine();
    System.out.print("\033[H\033[2J");
    float hoursSpent = 0.0f;
    Boolean ExceptionThrown = false;
    try {
        System.out.println("Enter hours spent in decimal");
        hoursSpent = readFloat(scanner);
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
        ExceptionThrown = true;
    }

    if(!ExceptionThrown){
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
        return;
    }        
}

// Ruth Andersen
static void showVacationDaysOption(Main app){
    try {
        System.out.println(app.currentUser.toStringVacationDays());
    } catch (OperationNotAllowed e) {
        System.out.println(e.getMessage());
    }
}

// Abdellah El Ghazzaz & Ruth Andersen
static void terminalInterface(){
    Scanner scanner = new Scanner(System.in);
    Main app = new Main();

    System.out.print("\033[H\033[2J");
    System.out.println("Intern SoftwareHuset program");

    while (true) {
        if (!app.CheckUserLoggedIn()) {
            System.out.println("LOGIN MENU");
            System.out.println("Type in your initials to login");

            String initials = scanner.nextLine();
            System.out.print("\033[H\033[2J");

            try {
                app.loginUser(initials);
                System.out.println(app.currentUser.getName() + " logged in");
            } catch (OperationNotAllowed e) {
                System.out.println(e.getMessage());
                break;
            }

        } else {
            
            // Show main menu, return option chosen
            String projectOptions = showMainMenu(scanner);
            
            if (projectOptions.equals("1")) {
                // See projects option
                seeProjectsOption(app);

            } else if (projectOptions.equals("2")) {
                // Make new project
                makeNewProjectOption(scanner, app);

            } else if (projectOptions.equals("3")) {
                // Select a project option
                selectAProjectOption(scanner, app);

            } else if(projectOptions.equals("4")){
                // Register vacation days option
                registerVacationDaysOption(scanner, app);
            
            }else if(projectOptions.equals("5")){
                // Show vacation days option
                showVacationDaysOption(app);
            } else if(projectOptions.equals("6")){
                // Join project option
                joinProjectOption(scanner, app);
            } else {
                System.out.println(projectOptions + " does not exist as an option");
            }
        }
    }
    scanner.close();
}

// Abdellah El Ghazzaz & Ruth Andersen
public static void main(String[] args) {
    terminalInterface();
}
    // Abdellah El Ghazzaz & Ruth Andersen
    public Main(){
        // Users must be made before, as there should not be a function to add a user.
        String[] ListOfInitials = {"HUBA", "ANDA", "ANNA"};
        for (String name : ListOfInitials){
            User newUser = new User(name);
            this.ListOfUsers.add(newUser);
        }
    }

    // Ruth Andersen
    public Boolean CheckUserLoggedIn(){
        return UserLoggedInFlag;
    }

    // Abdellah El Ghazzaz & Ruth Andersen
    public void loginUser(String initials) throws OperationNotAllowed {
        String normalizedInitials = normalizeName(initials);
        if (searchUser(normalizedInitials)){
            this.UserLoggedInFlag = true;
            this.currentUser = this.getUser(normalizedInitials);
        } else {
            throw new OperationNotAllowed("User does not exist");
        }
    }

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
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

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
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


    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
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

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
    public Project getProject(String projectname) {
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

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
    public void assignUserToProject(String projectname, String userInitials) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project == null) {
            throw new OperationNotAllowed("Project does not exist");
        }

        User user = this.getUser(userInitials);
        if (user == null) {
            throw new OperationNotAllowed("Employee does not exist");
        }

        if (project.findMemberByUser(user) != null) {
            throw new OperationNotAllowed("User is already registered on this project");
        }

        project.assignUser(user);
    }

    // Ruth Andersen & Alexander Van Steenwijk
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

    // Alexander Van Steenwijk & Ruth Andersen
    public void deleteProject(String projectname) throws OperationNotAllowed {
        if(!this.searchProject(projectname)){                               // 1
            throw new OperationNotAllowed("Project does not exist");                                                        // 2
        }

        if (!this.projectHasProjectLeader(projectname) || 
        isCurrentUserProjectLeader(projectname)){                                                                   // 3
            Project project = this.getProject(projectname);                                                  // 4
            this.ListOfProjects.remove(project);                                                      // 5
        } else if (!isCurrentUserProjectLeader(projectname)){                                                                   // 6
            throw new OperationNotAllowed("Employee is not the project leader");                                               // 7
        }
    }

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
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

    // Abdellah El Ghazzaz & Ruth Andersen & Alexander Van Steenwijk
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

    // Ruth Andersen
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

    // Alexander Van Steenwijk & Ruth Andersen
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

    // Alexander Van Steenwijk
    public void editProjectLeader(String projectname, String newProjectLeaderName) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if (project == null) {
            throw new OperationNotAllowed("Project does not exist");
        }

        User chosenUser = this.getUser(newProjectLeaderName);
        if (chosenUser == null) {
            throw new OperationNotAllowed("Employee does not exist");
        }

        if (!this.projectHasProjectLeader(projectname)) {
            throw new OperationNotAllowed("Cannot edit project leader when no project leader is assigned");
        }

        if (!isCurrentUserProjectLeader(projectname)) {
            throw new OperationNotAllowed("Employee is not the project leader");
        }

        project.setProjectLeader(chosenUser);
    }

    // Alexander Van Steenwijk & Ruth Andersen
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
                throw new OperationNotAllowed("Employee is not the project leader");
            }
        }
    }
    
    // Ruth Andersen
    public String getCurrentUserName(){
        return this.currentUser.getName();
    }

    // Ruth Andersen & Alexander Van Steenwijk
    public String getProjectLeaderName(String projectname) throws OperationNotAllowed {
        Project project = this.getProject(projectname);
        if(project != null && this.projectHasProjectLeader(projectname)){
            User projectleader = project.getProjectLeader();
            return projectleader.getName();
        } else {
            return "";
        }
    }

    // Ruth Andersen & Alexander Van Steenwijk
    private boolean isCurrentUserProjectLeader(String projectname) throws OperationNotAllowed {
        return this.currentUser != null && this.checkProjectLeader(projectname, this.currentUser.getName());
    }

    // Alexander Van Steenwijk
    private static int readInt(Scanner scanner) throws OperationNotAllowed {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new OperationNotAllowed("Invalid number");
        }
    }

    // Alexander Van Steenwijk
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

    // Alexander Van Steenwijk
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
