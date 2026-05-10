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
        this.name = requireName(name, "Project name is required");
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
        if (user == null) {
            throw new Exception("User not assigned to project");
        }
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
        if (user == null) {
            return false;
        }
        Member member = findMemberByUser(user);

        if(member == null){
            return false;
        }

        return !member.getActivityTimes().isEmpty();
    }

    public Activity createActivity(String name, int budgetedTime, Date startDate, Date endDate, boolean status) {
        Activity existingActivity = findActivityByName(name);
        if (existingActivity != null) {
            return existingActivity;
        }

        Activity activity = new Activity(name, budgetedTime, startDate, endDate, status);
        activityList.add(activity);
        return activity;
    }

    // Add user to this project
    public void assignUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Employee does not exist");
        }
        if (findMemberByUser(user) != null) {
            return;
        }
        Member member = new Member(user);
        registeredMembers.add(member);
    }

    // Assign the leader user
    public void setProjectLeader(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Employee does not exist");
        }
        projectLeader = user;
    }

    public void editProjectName(String name){
        this.name = requireName(name, "Project name is required");
    }

    // Register time done on an actity in hours
    public void registerTime(Activity activity, float hours, User user) throws Exception {
        if (activity == null || !activityList.contains(activity)) {
            throw new Exception("No activity found");
        }
        validateHours(hours);
        if (user == null) {
            throw new Exception("User not assigned to project");
        }

        if(user.hasVacationDateBetween(activity.getStartDate(), activity.getEndDate())){
            throw new Exception("Cannot register time during vacation period");
        }

        Member member = findMemberByUser(user);

        if(member == null){
            member = new Member(user);
            registeredMembers.add(member);
        }

        member.recordActivityTime(activity, hours);
    }

    public void removeActivityTime(User user, Activity activity, float hours) throws Exception {
        if (activity == null || !activityList.contains(activity)) {
            throw new Exception("No existing time logged on activity");
        }
        validateHours(hours);

        Member member = findMemberByUser(user);
        if (member == null) {
            throw new Exception("User not assigned to project");
        }
        member.removeActivityTime(activity, hours);
    }

    public Report generateProjectReport(User user) {
        if (projectLeader == null || user == null || !projectLeader.equals(user)) {
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
        if (user == null) {
            return null;
        }
        for (Member member : registeredMembers) {
            if (member.getUser().equals(user)) {
                return member;
            }
        }
        return null; // Not found
    }

    private Activity findActivityByName(String name) {
        String normalizedName = normalizeName(name);
        if (normalizedName == null) {
            return null;
        }

        for (Activity activity : activityList) {
            if (activity.getName().equalsIgnoreCase(normalizedName)) {
                return activity;
            }
        }
        return null;
    }

    private static void validateHours(float hours) throws Exception {
        if (!Float.isFinite(hours) || hours <= 0) {
            throw new Exception("Hours must be greater than zero");
        }
    }

    private static String requireName(String name, String message) {
        String normalizedName = normalizeName(name);
        if (normalizedName == null) {
            throw new IllegalArgumentException(message);
        }
        return normalizedName;
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


// Stores hours for an activty
class ActivityTime {
    private Activity activity;
    private float hours;

    ActivityTime(Activity activity, float hours) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity is required");
        }
        if (!Float.isFinite(hours) || hours <= 0) {
            throw new IllegalArgumentException("Hours must be greater than zero");
        }
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
        if (!Float.isFinite(hours)) {
            throw new IllegalArgumentException("Hours must be finite");
        }
        this.hours += hours;
    }
}
