import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
     public LocalDateTime creationDateTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   public OrderStatus orderStatus;
   public ArrayList<String> wholeOrder = new ArrayList<>();
   public int total = 0;

    public void setCreationDateTimeFromString(String dateTimeString) {
        this.creationDateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
}
