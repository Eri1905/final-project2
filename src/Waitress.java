import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Waitress {
    Scanner sc = new Scanner(System.in);
    int numtables;
    Tables[] tables;
    Path path = Paths.get("src/menu.txt");

    boolean keepGoing = true;

    public void work() throws IOException {
        tables = createTables();
        do {
            System.out.println("Please enter the number of the task you want to do: See the menu(1); " +
                    "Change the menu(2); " +
                    "Take an order(3) +" +
                    "Edit an order(4)");
            int numbersWaitress = sc.nextInt();
            switch (numbersWaitress) {
                case 1:
                    System.out.println("Please enter the number of the type you want to see: Starters(1); Main course(2); Deserts(3); Drinks(4)");
                    int numsTypes = sc.nextInt();
                    switch (numsTypes) {
                        case 1:
                            showStarters();
                            break;
                        case 2:
                            showMainCourse();
                            break;
                        case 3:
                            showDeserts();
                            break;
                        case 4:
                            showDrinks();
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Please enter the number of the task you want to do: Add element(1); Remove element(2)");
                    int numsAddRemove = sc.nextInt();
                    switch (numsAddRemove) {
                        case 1:
                            System.out.println("Please enter the dish,price,type of the dish you want to add: ");
                            addToMenu();
                            break;
                        case 2:
                            System.out.println("Please enter the name of the dish you want to remove from the menu: ");
                            removeFromMenu();
                            break;
                    }
                    break;
                case 3:
                    checkForFree(tables);
                    break;
                case 4:
                    for (int i = 0; i < tables.length; i++){
                        tables[i].editOrder();
                    }
                    break;
            }
            keepGoing = goOn();
        }
        while (keepGoing == true);
    }

    private boolean goOn() {
        System.out.println("Do you want to keep going?");
        String answerGoOn = sc.next();
        if (answerGoOn.equalsIgnoreCase("no")) {
            keepGoing = false;
        }
        return keepGoing;
    }

    private Tables[] createTables() {
        System.out.println("Please enter the quantity of tables you serve: ");
        numtables = sc.nextInt();
        Tables[] tables = new Tables[numtables];

        for (int i = 0; i < tables.length; i++) {
            Tables table = new Tables(i + 1);
            tables[i] = table;
        }
        return tables;
    }

    private void checkForFree(Tables[] tables) {
        for (int i = 0; i < tables.length; i++) {
            if (tables[i].tableStatus == TableStatus.FREE) {
                tables[i].takeOrder();
                break;
            } else {
                System.out.println("There is no free tables!");
            }
        }
    }

    private void addToMenu() {
        Scanner sc = new Scanner(System.in);
        String addedDish = sc.next();
        try {
            FileWriter fileWriter = new FileWriter(path.toFile(), true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();

            bufferedWriter.write(addedDish);

            bufferedWriter.close();

            System.out.println("You added new dish to the menu!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFromMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        String elementToRemove = sc.next();
        List<String> lines = Files.readAllLines(path);

        List<String> updatedLines = lines.stream()
                .filter(line -> !line.contains(elementToRemove))
                .collect(Collectors.toList());
        Files.write(path, updatedLines);
    }

    private void showStarters() {
        try {
            Files.lines(path)
                    .filter(line -> line.contains("starters"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showMainCourse() {
        try {
            Files.lines(path)
                    .filter(line -> line.contains("main"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDeserts() {
        try {
            Files.lines(path)
                    .filter(line -> line.contains("deserts"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDrinks() {
        try {
            Files.lines(path)
                    .filter(line -> line.contains("drinks"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
