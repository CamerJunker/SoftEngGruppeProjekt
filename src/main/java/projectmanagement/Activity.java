package projectmanagement;
import java.util.ArrayList;

public class Activity {
    private String name;
    private float budgetedTime;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private ArrayList<Member> assignedUsers;

    public Activity(String name, float budgetedTime, Date startDate, Date endDate, boolean status) {
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
    public float getBudgetedTime() {
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

    public void setBudgetedTime(float budgetedTime) {
        this.budgetedTime = budgetedTime;
    }

    public ArrayList<Member> getAssignedUsers(){
        return this.assignedUsers;
    }

    public void assignUser(Member newMember) throws Exception{
        if (newMember == null || newMember.getUser() == null) { // 1
            throw new Exception("Employee does not exist"); // 2
        }

        for (Member member : assignedUsers){ // 3
            if(member.getUser().equals(newMember.getUser())){ // 4
                throw new Exception("Employee already assigned"); // 5
            }
        }
        assignedUsers.add(newMember); // 6
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
