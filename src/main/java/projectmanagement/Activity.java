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
        String normalizedName = normalizeName(name);
        if (normalizedName == null) {
            throw new IllegalArgumentException("Activity name is required");
        }
        if (budgetedTime < 0) {
            throw new IllegalArgumentException("Budgeted time cannot be negative");
        }
        if (startDate == null || endDate == null || !startDate.isValid() || !endDate.isValid()) {
            throw new IllegalArgumentException("Activity dates must be valid");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Activity start date cannot be after end date");
        }

        this.name = normalizedName;
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
        if (newMember == null || newMember.getUser() == null) {
            throw new Exception("Employee does not exist");
        }

        for (Member member : assignedUsers){
            if(member.getUser().equals(newMember.getUser())){
                throw new Exception("Employee already assigned");
            }
        }
        assignedUsers.add(newMember);
    }

    private static String normalizeName(String name) {
        if (name == null) {
            return null;
        }

        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return null;
        }
        return trimmedName;
    }
}
