package projectmanagement;
import java.time.Year;


public class SerialNumber {
    private int serialNumber = 0;
    private int year = Year.now().getValue() % 100;

    public String getSerialNumber(){
        this.serialNumber++;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d",year));
        sb.append(String.format("%03d",serialNumber));
        return sb.toString();
    }  
}
