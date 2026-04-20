package projectmanagement;

import java.util.ArrayList;

import projectmanagement.Date;

public class User {
    private String name;
    private ArrayList<Date> vacationDays;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Date> getVacationDates() {
        return this.vacationDays;
    }
}


// class ProjectLeader extends User {
// 
// }

