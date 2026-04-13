package projectmanagement;

import java.util.ArrayList;

import projectmanagement.Date;
import projectmanagement.Activity;


public class Project {
    private String name;
    private String serialNumber;
    private ArrayList<User> registeredUsers;
    private User projectLeader;
    private ArrayList<Activity> activityList;

    public String getName() {
        return this.name;
    }
    public String getSerialNumber() {
        return this.serialNumber;
    }
    public ArrayList<User> getRegisteredUsers() {
        return this.registeredUsers;
    }
    public User getProjectLeader() {
        return this.projectLeader;
    }
    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }

    public void createActivity(String name, int budgetedTime, int startWeek, int endWeek, int startYear, int endYear, boolean status) {
        Activity activity = new Activity(name, budgetedTime, startWeek, endWeek, startYear, endYear, status);
        activityList.add(activity);
    }

    public void assignUser(User user) {
        registeredUsers.add(user);
    }

    public void setProjectLeader(User user) {
        projectLeader = user;
    }

    
}

