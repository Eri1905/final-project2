import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Orders {
    OrderStatus orderStatus;
    List<String>order=new ArrayList<>();
    int total=0;

    public void takeOrder() {
        Scanner sc = new Scanner(System.in);
        String answer;
        System.out.println("Hello, can I take your order?");
        do {
            String dish = sc.next();
            order.add(dish);
            System.out.println("Is this the whole order?");
            answer = sc.next();
        } while (answer.equalsIgnoreCase("No"));
        orderStatus = OrderStatus.TAKEN;
    }

    public void calculateTotal(){
        takeOrder();
        String[] words;
        String name;
        int price;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words = line.split(",");
                name=words[0];
                price = Integer.parseInt(words[1]);
                for (String orderItem : order) {
                    if (name.equalsIgnoreCase(orderItem)) {
                        total += price;
                        break;
                    }
                }
                System.out.println(total);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

    }
}
