package projectmanagement;

import java.util.ArrayList;

public class Member {
    private User user;
    private ArrayList<ActivityTime> activityTimes;

    Member(User user) {
        this.user = user;
        this.activityTimes = new ArrayList<>();
    }

    public User getUser() {
        return this.user;
    }

    public ArrayList<ActivityTime> getActivityTimes() {
        return this.activityTimes;
    }

    public void recordActivityTime(Activity activity, float hours) {
        
        boolean foundActivity = false;
        
        // Search through all activities and update the time for the activity if it exists
        for (ActivityTime activityTime : this.activityTimes) {
            if (activityTime.getActivity() == activity) {   
                activityTime.updateTime(hours);
                foundActivity = true;
            }
        }
        
        // If the acitity has not been recorded previously
        if (!foundActivity) {
            ActivityTime activityTime = new ActivityTime(activity, hours);
            this.activityTimes.add(activityTime);
        }

    }

    public void removeActivityTime(Activity activity, float hours) {

        boolean foundActivity = false;

        for (ActivityTime activityTime : this.activityTimes) {
            if (activityTime.getActivity() == activity) {
                foundActivity = true;

                float currentHours = activityTime.getHours();

                // Check if remove value is higher than the logged time before removing 
                if (hours > currentHours) {
                    System.out.println("Cannot remove more hours than registered");
                    return;
                }

                activityTime.updateTime(-hours);
            }
        }

        if (!foundActivity) {
            System.out.println("No existing time logged on activity");
        }
    }
}
