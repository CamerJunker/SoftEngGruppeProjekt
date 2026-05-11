package steps;
// Ruth Andersen

public class EmployeeInfoHolder {
    private String name = "";

    public EmployeeInfoHolder(){

    }

    public void setName(String employeeName){
        this.name = employeeName;
    }

    public String getName(){
        return this.name;
    }
}
