package projectmanagement;
import java.util.ArrayList;

public class Activity {
    private String name;
    private int budgetedTime;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private ArrayList<Member> assignedUsers;

    public Activity(String name, int budgetedTime, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

        assignedUsers = new ArrayList<Member>();
    }

    public String getName() {
        return this.name;
    }
    public int getBudgetedTime() {
        return this.budgetedTime;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    public boolean getStatus() {
        return this.status;
    }
    public void setStatus(boolean status) {
        this.status = status;
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
