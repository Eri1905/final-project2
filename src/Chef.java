import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Chef {
    Scanner sc=new Scanner(System.in);
    Order order;
    private void changeOrderStatus() {
        if (order.orderStatus.equals(OrderStatus.TAKEN)) {
            System.out.println("Please enter the status you want to change the order to: Preparing(1); Ready(2): ");
            try {
                int statusNums = sc.nextInt();
                switch (statusNums) {
                    case 1:
                        order.orderStatus = OrderStatus.PREPARING;
                    case 2:
                        order.orderStatus = OrderStatus.READY;
                }
            } catch (Exception ex) {
                System.out.println("Invalid input!");
            }
        } else {
            System.out.println("The order is already in another status!");
        }
    }
    private void seeActiveOrders(){
        String filePath = "src/activeOrders.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void work(){
        System.out.println("Please enter the number of the task you want to do: See all active orders(1); Change status of an order(2): ");
        int numbersChef=sc.nextInt();
        switch (numbersChef){
            case 1: seeActiveOrders();
            case 2: changeOrderStatus();
        }
    }
}
