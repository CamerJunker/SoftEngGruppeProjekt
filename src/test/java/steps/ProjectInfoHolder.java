package steps;
// Ruth Andersen

public class ProjectInfoHolder {
    private String ProjectName = "";

    public ProjectInfoHolder(){
    }

    public void setProjectName(String projectname){
        this.ProjectName = projectname;
    }

    public String getProjectName(){
        return this.ProjectName;
    }
}
