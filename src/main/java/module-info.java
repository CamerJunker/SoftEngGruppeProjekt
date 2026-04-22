module projectmanagement {
    requires transitive javafx.controls;
    requires javafx.fxml;
 
    opens dtu.example.ui to javafx.fxml; // Gives access to fxml files
    exports dtu.example.ui; // Exports the class inheriting from javafx.application.Application
    
    // Exports the class with the main code, otherwise the maven test will give an error saying that projectmanagement does not export to unnamed module [hasname].
    exports projectmanagement;
}