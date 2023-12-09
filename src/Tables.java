import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Tables {
    Order tableOrder;
    int tableNumber;
    TableStatus tableStatus;

    public Tables() {
        this.tableStatus = TableStatus.FREE;
        this.tableOrder = new Order();
    }

    public void takeOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.println("The date and time is: ");
        tableOrder.setCreationDateTimeFromString(sc.nextLine());
       // System.out.println(tableOrder.creationDateTime); беше за да проверя дали е вярно, самото нещо е създадено в order
        String answer;
        do {
            System.out.println("What do you want to order?");
            String dish = sc.next();
            //провери дали присъства в менюто и ако не да изкара текст
            tableOrder.wholeOrder.add(dish);
            System.out.println("Is this the whole order?");
            answer = sc.next();
        } while (answer.equalsIgnoreCase("No"));
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Order is taken!");
        }
        tableOrder.orderStatus = OrderStatus.TAKEN;
    }

    public void editOrder() {

    }

    public void calculateTotal() {
        takeOrder();
        String[] words;
        String name;
        int price;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words = line.split(",");
                name = words[0];
                price = Integer.parseInt(words[1]);
                for (String orderItem : tableOrder.wholeOrder) {
                    if (name.equalsIgnoreCase(orderItem)) {
                        tableOrder.total += price;
                        break;
                    }
                }
                System.out.println(tableOrder.total);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

}
