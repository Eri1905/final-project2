import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
     LocalDateTime creationDateTime;
     static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    OrderStatus orderStatus;
    ArrayList<String> wholeOrder = new ArrayList<>();
    int total = 0;

    public void setCreationDateTimeFromString(String dateTimeString) {
        this.creationDateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
//    public LocalDateTime getCreationDateTime() {
//        return creationDateTime;
//    }
//
//    public void setCreationDateTime(LocalDateTime creationDateTime) {
//        this.creationDateTime = creationDateTime;
//    }
}
