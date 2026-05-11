//Enya
package steps;

public class ActivityInfoHolder {
    private String activityName = "";
    private float estimatedHours = 0;
    private int startDay = 0;
    private int endDay = 0;
    private int startMonth = 0;
    private int endMonth = 0;
    private int startYear = 0;
    private int endYear = 0;

    public ActivityInfoHolder() {
    }

    public void setActivityName(String name) {
        this.activityName = name;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setEstimatedHours(float hours) {
        this.estimatedHours = hours;
    }

    public float getEstimatedHours() {
        return this.estimatedHours;
    }

    public void setStartDay(int day) {
        this.startDay = day;
    }

    public int getStartDay() {
        return this.startDay;
    }

    public void setEndDay(int day) {
        this.endDay = day;
    }

    public int getEndDay() {
        return this.endDay;
    }

    public void setStartMonth(int month) {
        this.startMonth = month;
    }

    public int getStartMonth() {
        return this.startMonth;
    }

    public void setEndMonth(int month) {
        this.endMonth = month;
    }

    public int getEndMonth() {
        return this.endMonth;
    }

    public void setStartYear(int year) {
        this.startYear = year;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public void setEndYear(int year) {
        this.endYear = year;
    }

    public int getEndYear() {
        return this.endYear;
    }
}
