package projectmanagement;
import java.util.ArrayList;
import projectmanagement.Project;

public class Activity {
    private String name;
    private int budgetedTime;
    private int startWeek;
    private int endWeek;
    private int startYear;
    private int endYear;
    private boolean status;
    private ArrayList<Member> assignedUsers;

    public Activity(String name, int budgetedTime, int startWeek, int endWeek, int startYear, int endYear, boolean status) {
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.startYear = startYear;
        this.endYear = endYear;
        this.status = status;

        assignedUsers = new ArrayList<Member>();
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

    public ArrayList<Member> getAssignedUsers(){
        return this.assignedUsers;
    }

    public void assignUser(Member newMember) throws Exception{
        for (Member member : assignedUsers){
            if(member.getUser().equals(newMember.getUser())){
                throw new Exception("Employee already assigned");
            }
        }
        assignedUsers.add(newMember);
    }
}