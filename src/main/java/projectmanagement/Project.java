package projectmanagement;

import java.util.ArrayList;

import projectmanagement.Date;
import projectmanagement.Activity;


public class Project {
    private String name;
    private String serialNumber;
    private ArrayList<Member> registeredMembers;
    private User projectLeader;
    private ArrayList<Activity> activityList;


    public Project(String name) {
        this.name = name;

        // TODO
        this.serialNumber
    }

    public String getName() {
        return this.name;
    }
    public String getSerialNumber() {
        return this.serialNumber;
    }
    public ArrayList<Member> getRegisteredMembers() {
        return this.registeredMembers;
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

    // Add user to this project
    public void assignUser(User user) {
        Member member = new Member(user);
        registeredMembers.add(member);
    }

    // Assign the leader user
    public void setProjectLeader(User user) {
        projectLeader = user;
    }

    // Register time done on an actity in hours
    public void registerTime(Activity activity, float hours, User user) {
        Member member = findMemberByUser(user);

        member.recordActivityTime(activity, hours);
    }

    // Find member object from a user object
    private Member findMemberByUser(User user) {
        for (Member member : registeredMembers) {
            if (member.getUser().equals(user)) {
                return member;
            }
        }
        return null; // Not found
    }
}

// Holds user data like registered hours for each activity
class Member {
    private User user;
    private ArrayList<ActivityTime> activityTimes;

    Member(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public ArrayList<ActivityTime> getActivityTimes() {
        return this.activityTimes;
    }

    public void recordActivityTime(Activity activity, float hours) {
        ActivityTime activityTime = new ActivityTime(activity, hours);

        this.activityTimes.add(activityTime);
    }
}

// Stores hours for an activty
class ActivityTime {
    private Activity activity;
    private float hours;

    ActivityTime(Activity activity, float hours) {
        this.activity = activity;
        this.hours = hours;
    }
}
