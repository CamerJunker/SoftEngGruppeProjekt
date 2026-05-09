package projectmanagement;

import java.util.ArrayList;

public class Project {
    private String name;
    private String serialNumber;
    private ArrayList<Member> registeredMembers;
    private User projectLeader;
    private ArrayList<Activity> activityList;

    private static SerialNumber serialNumberGenerator = new SerialNumber();

    public Project(String name) {
        this.name = name;
        // generate a serialnumber
        this.serialNumber = serialNumberGenerator.getSerialNumber();

        this.registeredMembers = new ArrayList<>();
        this.activityList = new ArrayList<>();
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
    public boolean hasProjectLeader(){
        if (this.projectLeader == null){
            return false;
        }
        return true;
    }
    public void removeProjectLeader(){
        this.projectLeader = null;
    }

    public float getRegisteredActivityTimeForUser(User user, Activity activity) throws Exception {
        Member member = findMemberByUser(user);

        if(member == null){
            throw new Exception("User not assigned to project");
        }

        try {
            return member.getActivityTime(activity);
        } catch (Exception e) {
            throw new Exception("No activity found");
        }
    }

    public boolean userHasActivities(User user) {
        Member member = findMemberByUser(user);

        if(member == null){
            return false;
        }

        return !member.getActivityTimes().isEmpty();
    }

    public Activity createActivity(String name, int budgetedTime, Date startDate, Date endDate, boolean status) {
        Activity activity = new Activity(name, budgetedTime, startDate, endDate, status);
        activityList.add(activity);
        return activity;
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

    public void editProjectName(String name){
        this.name = name;
    }

    // Register time done on an actity in hours
    public void registerTime(Activity activity, float hours, User user) {
        Member member = findMemberByUser(user);

        if(member == null){
            member = new Member(user);
            registeredMembers.add(member);
        }

        member.recordActivityTime(activity, hours);
    }

    public void removeActivityTime(User user, Activity activity, float hours) throws Exception {
        Member member = findMemberByUser(user);
        if (member == null) {
            throw new Exception("User not assigned to project");
        }
        member.removeActivityTime(activity, hours);
    }

    public Report generateProjectReport(User user) {
        if (projectLeader != user) {
            return null;
        }

        float totalBudgetedTime = 0;
        for (Activity activity : activityList) {
            totalBudgetedTime += activity.getBudgetedTime();
        }

        float totalHoursUsed = 0;
        for (Member member : registeredMembers) {
            for (ActivityTime activityTime : member.getActivityTimes()) {
                totalHoursUsed += activityTime.getHours();
            }
        }

        return new Report(totalHoursUsed, totalBudgetedTime);
    }


    // Find member object from a user object
    public Member findMemberByUser(User user) {
        for (Member member : registeredMembers) {
            if (member.getUser().equals(user)) {
                return member;
            }
        }
        return null; // Not found
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

    public Activity getActivity() {
        return this.activity;
    }
    public float getHours() {
        return this.hours;
    }
    
    public void updateTime(float hours) {
        this.hours += hours;
    }
}
