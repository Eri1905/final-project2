import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Chef {
    Scanner sc=new Scanner(System.in);
    Order order;
    boolean keepGoing = true;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String PURPLE_BOLD = "\033[1;35m";
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
       do{
           System.out.println("Please enter the number of the task you want to do: See all active orders(1); Change status of an order(2): ");
        int numbersChef=sc.nextInt();
        switch (numbersChef){
            case 1: seeActiveOrders(); break;
            case 2: changeOrderStatus(); break;
        }
         keepGoing = goOn();
    }
       while(keepGoing == true);
    }
    private boolean goOn() {
        System.out.println("Do you want to keep going?");
        String answerGoOn = sc.next();
        if (answerGoOn.equalsIgnoreCase("no")) {
            keepGoing = false;
            System.out.println(PURPLE_BOLD + "     .\n" +
                    "    . .\n" +
                    "      ...\n" +
                    "   \\~~~~~/\n" +
                    "    \\   /\n" +
                    " G O O D B Y E" +
                    "      \n" +
                    "      |\n" +
                    "      |\n" +
                    "     ---" + ANSI_RESET);
        }
        return keepGoing;
    }
}
