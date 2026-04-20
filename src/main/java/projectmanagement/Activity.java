package projectmanagement;

public class Activity {
    private String name;
    private int budgetedTime;
    private int startWeek;
    private int endWeek;
    private int startYear;
    private int endYear;
    private boolean status;

    public Activity(String name, int budgetedTime, int startWeek, int endWeek, int startYear, int endYear, boolean status) {
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.startYear = startYear;
        this.endYear = endYear;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }
    public int getBudgetedTime() {
        return this.budgetedTime;
    }
    public int getStartWeek() {
        return this.startWeek;
    }
    public int getEndWeek() {
        return this.endWeek;
    }
    public int getStartYear() {
        return this.startYear;
    }
    public int getEndYear() {
        return this.endYear;
    }
    public boolean getStatus() {
        return this.status;
    }
}