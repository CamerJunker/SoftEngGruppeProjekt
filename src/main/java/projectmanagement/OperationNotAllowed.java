package projectmanagement;
// Ruth Andersen
public class OperationNotAllowed extends Exception{
    public OperationNotAllowed(String errormessage){
        super(errormessage);
    }
}
