package projectmanagement;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Date {
    public int day;
    public int month;
    public int year;

    public Date(){}

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        validate();
    }

    public boolean isBetween(Date startDate, Date endDate) {
        if (!isValid() || startDate == null || endDate == null || !startDate.isValid() || !endDate.isValid()) {
            return false;
        }

        if (startDate.isAfter(endDate)) {
            return false;
        }

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

    public boolean isValid() {
        try {
            toLocalDate();
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public boolean isAfter(Date other) {
        if (other == null || !this.isValid() || !other.isValid()) {
            return false;
        }
        return this.toLocalDate().isAfter(other.toLocalDate());
    }

    private void validate() {
        toLocalDate();
    }

    private LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }

    public String toString(){
        String dayString = "";
        String monthString = "";
        String yearString = "";

        if((""+this.day).length()<2){
            dayString += "0"+this.day;
        } else {
            dayString += this.day;
        }

        if((""+this.month).length()<2){
            monthString += "0"+this.month;
        } else {
            monthString += this.month;
        }

        if((""+this.year).length()<4){
            int yearLength = (""+this.year).length();
            if (yearLength == 1){
                yearString += "200"+this.year;
            } else if (yearLength == 2){
                yearString += "20"+this.year;
            } else {
                yearString += "2"+this.year;
            }
        } else {
            yearString += this.year;
        }
        return ""+dayString+"-"+monthString+"-"+yearString;
    }

}
