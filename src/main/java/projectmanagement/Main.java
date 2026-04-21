package projectmanagement;

import java.util.ArrayList;

public class Main {
    private Boolean UserLoggedInFlag = false;
    private User currentUser;
    private ArrayList<User> ListOfUsers = new ArrayList<>();

    public void loginUser(String initials){
        if (searchUser(initials)){
            this.UserLoggedInFlag = true;
            this.currentUser = this.getUser(initials); 
        }
    }

    // Check if user exist in preexisting list of users
    private Boolean searchUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equals(UserInitials)){
                return true;
            }
        }
        return false;
    }

    // Get User as object, search by initials
    private User getUser(String UserInitials){
        for (User searchUser : this.ListOfUsers){
            String searchUserInitials = searchUser.getName();
            if (searchUserInitials.equals(UserInitials)){
                return searchUser;
            }
        }
        return null;
    }
}



