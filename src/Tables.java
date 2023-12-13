import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Tables {
    Order tableOrder;
    int tableNumber;
    TableStatus tableStatus;
    String[] words;
    //boolean isProductAvailable=false;
    Scanner sc = new Scanner(System.in);
    Path activeOrders = Paths.get("src/activeOrders.txt");


    public TableStatus getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(TableStatus tableStatus) {
        this.tableStatus = tableStatus;
    }



    public Tables(int tableNumber) {
        this.tableStatus = TableStatus.FREE;
        this.tableOrder = new Order();
        this.tableNumber = tableNumber;
    }

    public void takeOrder() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("The date and time is(dd-MM-yyyy HH:mm:ss): ");
            tableOrder.setCreationDateTimeFromString(sc.nextLine());
            // System.out.println(tableOrder.creationDateTime); беше за да проверя дали е вярно, самото нещо е създадено в order
            String answer;
            showMenu();
            do {
                System.out.println("What do you want to order?");
                String dish = sc.next();
                if (checkForAvailabilityOfProduct(dish)) {
                    tableOrder.wholeOrder.add(dish);
                }
                writeActiveOrdersToFile();
                System.out.println("Is this the whole order?");
                answer = sc.next();
            } while (answer.equalsIgnoreCase("No"));
            if (answer.equalsIgnoreCase("yes")) {
                System.out.println("Order is taken!");
                tableOrder.orderStatus = OrderStatus.TAKEN;
                tableStatus = TableStatus.TAKEN;
            }
        } catch (Exception ex) {
            System.out.println("Invalid input!");
        }
    }

    public void editOrder() {
        if (tableOrder.orderStatus == OrderStatus.TAKEN) {
            System.out.println("Enter the number of the task you want to do: Add to order(1); Remove from order(2)");
            try{int orderEditNum = sc.nextInt();
            switch (orderEditNum) {
                case 1:
                    System.out.println("Enter the item you want to add: ");
                    String addItem = sc.next();
                    boolean isProductAvailable = checkForAvailabilityOfProduct(addItem); //трябва ни, защото иначе прибавя неща не от менюто
                    if (isProductAvailable) {
                        tableOrder.wholeOrder.add(addItem);
                        updateFileWithData();
                        System.out.println("The food/drink is added successfully!");
                        System.out.println(tableOrder.wholeOrder);
                    }
                    break;
                case 2:
                    System.out.println("Enter the item you want to remove: ");
                    String removeItem = sc.next();
                    tableOrder.wholeOrder.remove(removeItem);
                    updateFileWithData();
                    System.out.println("The food/drink is removed successfully!");
                    System.out.println(tableOrder.wholeOrder);
                    break;
            }
            }catch (Exception ex){
                System.out.println("Invalid input!");
            }
        }
    }

    private void showMenu(){
        String filePath = "src/menu.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeStatusOfOrder(){
        if (tableOrder.orderStatus.equals(OrderStatus.TAKEN)){
            System.out.println("Enter the number of the status you want the order to change: Served(1); Preparing(2); Ready(3)");
            int changeStatusNums= sc.nextInt();
            switch (changeStatusNums){
                case 1:
                    tableOrder.orderStatus=OrderStatus.SERVED;
                    break;
                case 2:
                    tableOrder.orderStatus=OrderStatus.PREPARING;
                    break;
                case 3:
                    tableOrder.orderStatus=OrderStatus.READY;
                    break;
            }
        }else {
            System.out.println("The order is already in another status!");
        }
    }

    private boolean checkForAvailabilityOfProduct(String dishes) {
        boolean isProductAvailable = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");

                if (dishes.equalsIgnoreCase(words[0])) {
                    System.out.println("The product is added to the order!");
                    isProductAvailable = true;
                    break;
                }
            }
            if (!isProductAvailable) {
                System.out.println("The menu does not contain this product!");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return isProductAvailable;
    }

    public void calculateTotal() {
       // takeOrder();
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
            }
            System.out.println(tableOrder.total);
            tableOrder.orderStatus=OrderStatus.PAID;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private void updateFileWithData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(activeOrders.toFile(), true))) {
            writer.write("Table " + tableNumber + " orders: ");
            writer.newLine();
            for (String data : tableOrder.wholeOrder) {
                writer.write(data);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        public void writeActiveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(activeOrders.toFile(),true))) {
            writer.write("Table "+tableNumber+" orders: ");
            writer.newLine();
            for (String data : tableOrder.wholeOrder) {
                writer.write(data);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
