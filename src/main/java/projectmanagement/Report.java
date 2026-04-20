package projectmanagement;

public class Report {
    private float hoursUsed;
    private float budgedtedTime;

    public Report(float hoursUsed, float budgetedTime) {
        this.hoursUsed = hoursUsed;
        this.budgedtedTime = budgetedTime;
    }

    public float getBudgedtedTime() {
        return budgedtedTime;
    }
    public float getHoursUsed() {
        return hoursUsed;
    }
}
