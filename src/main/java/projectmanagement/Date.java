package projectmanagement;

public class Date {
    public int day;
    public int month;
    public int year;

    public Date() {
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public boolean isBetween(Date startDate, Date endDate) {
        if(this.year < startDate.year || this.year > endDate.year){
            return false;
        }

        if(this.year == startDate.year && this.month < startDate.month){
            return false;
        }

        if(this.year == endDate.year && this.month > endDate.month){
            return false;
        }

        if(this.year == startDate.year && this.month == startDate.month && this.day < startDate.day){
            return false;
        }

        if(this.year == endDate.year && this.month == endDate.month && this.day > endDate.day){
            return false;
        }

        return true;
    }
}