// Alexander van Steenwijk

package projectmanagement;

import java.util.ArrayList;

// Holds user data like registered hours for each activity
public class Member {
    private User user;
    private ArrayList<ActivityTime> activityTimes;

    Member(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Employee does not exist");
        }
        this.user = user;
        this.activityTimes = new ArrayList<>();
    }

    public User getUser() {
        return this.user;
    }

    public ArrayList<ActivityTime> getActivityTimes() {
        return this.activityTimes;
    }

    public float getActivityTime(Activity activity) throws Exception {
        if (activity == null) {
            throw new Exception("No activity found");
        }
        
        boolean foundActivity = false;
        // Search through all activities and update the time for the activity if it exists
        for (ActivityTime activityTime : this.activityTimes) {
            if (activityTime.getActivity() == activity) {   
                return activityTime.getHours();
            }
        }
        
        // If the acitity has not been recorded previously
        if (!foundActivity) {
            throw new Exception("No activity found");
        }
        return 0.0f;
    }

    public void recordActivityTime(Activity activity, float hours) throws Exception {
        if (activity == null) {
            throw new Exception("No activity found");
        }
        validateHours(hours);
        
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

    public void removeActivityTime(Activity activity, float hours) throws Exception{
        if (activity == null) {
            throw new Exception("No existing time logged on activity");
        }
        validateHours(hours);

        boolean foundActivity = false;

        for (ActivityTime activityTime : this.activityTimes) {
            if (activityTime.getActivity() == activity) {
                foundActivity = true;

                float currentHours = activityTime.getHours();

                // Check if remove value is higher than the logged time before removing 
                if (hours > currentHours) {
                    throw new Exception("Cannot remove more hours than registered");
                }
                
                activityTime.updateTime(-hours);
            }
        }
        
        if (!foundActivity) {
            throw new Exception("No existing time logged on activity");
        }
    }

    private void validateHours(float hours) throws Exception {
        if (!Float.isFinite(hours) || hours <= 0) {
            throw new Exception("Hours must be greater than zero");
        }
    }
}
