import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Tables {
    Order tableOrder;
    int tableNumber;
    TableStatus tableStatus;
    String[] words;
    Scanner sc=new Scanner(System.in);
    Path activeOrders = Paths.get("src/activeOrders.txt");

    public Tables(int tableNumber) {
        this.tableStatus = TableStatus.FREE;
        this.tableOrder = new Order();
        this.tableNumber = tableNumber;
    }

    public void takeOrder() {
        Scanner sc = new Scanner(System.in);
        try{System.out.println("The date and time is(dd-MM-yyyy HH:mm:ss): ");
        tableOrder.setCreationDateTimeFromString(sc.nextLine());
       // System.out.println(tableOrder.creationDateTime); беше за да проверя дали е вярно, самото нещо е създадено в order
        String answer;
        do {
            System.out.println("What do you want to order?");
            String dish = sc.next();
            if (checkForAvailability(dish)){
            tableOrder.wholeOrder.add(dish);
            }
            System.out.println("Is this the whole order?");
            answer = sc.next();
        } while (answer.equalsIgnoreCase("No"));
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Order is taken!");
        }
        tableOrder.orderStatus = OrderStatus.TAKEN;
        }catch (Exception ex){
            System.out.println("Invalid input!");
        }
        writeActiveOrdersToFile();
    }

    public void editOrder() throws IOException {
        if (tableOrder.orderStatus==OrderStatus.TAKEN){
            System.out.println("Enter the number of the task you want to do: Add to order(1); Remove from order(2)");
            int orderEditNum=sc.nextInt();
            switch (orderEditNum){
                case 1:
                    System.out.println("Enter the item you want to add: ");
                    String addItem =sc.next();
                    tableOrder.wholeOrder.add(addItem);
                    System.out.println("The food/drink is added successfully!");
                    break;
                case 2:
                    System.out.println("Enter the item you want to remove: ");
                    String removeItem = sc.next();
                    tableOrder.wholeOrder.remove(removeItem);
                    removeProductFromActiveOrder(removeItem);
                    System.out.println("The food/drink is removed successfully!");
                    break;
            }
        }
    }
    private void removeProductFromActiveOrder(String removeFromOrder) throws IOException {
        List<String> lines = Files.readAllLines(activeOrders);
        List<String> updatedLines = lines.stream()
                .filter(line -> !line.contains(removeFromOrder))
                .collect(Collectors.toList());
        Files.write(activeOrders, updatedLines);
    }
    private boolean checkForAvailability(String dishes) {
        boolean isProductAvailable=false;
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
        }return isProductAvailable;
    }

        public void calculateTotal() {
        takeOrder();
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
    public void writeActiveOrdersToFile() {
        String fileName="src/activeOrders.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String orderedProducts : tableOrder.wholeOrder) {
                    writer.write(orderedProducts);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
        }
    }
