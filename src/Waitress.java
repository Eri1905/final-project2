import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Waitress {
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\033[0;34m";
    Scanner sc = new Scanner(System.in);
    int tableNum;
    Tables[] tables;
    Path path = Paths.get("src/menu.txt");

    boolean keepGoing = true;

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public Tables[] getTables() {
        return tables;
    }

    public void setTables(Tables[] tables) {
        this.tables = tables;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isKeepGoing() {
        return keepGoing;
    }

    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }

    public void work() throws IOException {
        System.out.println("Please enter the quantity of tables you serve: ");
        tables = createTables();
        do {
            System.out.println("Please enter the number of the task you want to do: See the menu(1); " +
                    "Change the menu(2); " +
                    "Take an order(3); " +
                    "Edit an order(4); "+
                    "Calculate total(5); "+
                    "Change the status of the order(6)");
            try{
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
                    System.out.println("Please enter the number of the table you want to edit the order: ");
                    int editOrderNum = sc.nextInt();
                    tables[editOrderNum - 1].editOrder();
                    break;
                case 5:
                    System.out.println("Please enter the number of the table you want to see the total: ");
                    int sumTable=sc.nextInt();
                    tables[sumTable-1].calculateTotal();
                    break;
                case 6:
                    System.out.println("Please enter the number of the table you want to change the status of the order: ");
                    int changeStatus= sc.nextInt();
                    tables[changeStatus-1].changeStatusOfOrder();
                    break;
            }
            }catch (Exception ex){
                System.out.println("Invalid input!");
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

    public Tables[] createTables() {
        tableNum = sc.nextInt();
        Tables[] tables = new Tables[tableNum];

        for (int i = 0; i < tables.length; i++) {
            Tables table = new Tables(i + 1);
            tables[i] = table;
        }
        return tables;
    }


    public void checkForFree(Tables[] tables) {
        //въвежда се номера на масата, проверяваме дали е свободна, ако не е, отива на най-близката свободна, ако няма свободна, излиза съобщение
        boolean allTablesTaken = true;
        System.out.println("Enter the number of the table: ");
        int currentTableNumber = sc.nextInt();
        if(tables[currentTableNumber - 1].tableStatus == TableStatus.FREE){
            System.out.println("Table number " + currentTableNumber + " is free");
            System.out.println("You are working on table " + currentTableNumber);
            tables[currentTableNumber - 1].takeOrder();
            allTablesTaken = false;
        }
        else {
            System.out.println("This table is already taken.");
            for (int i = 0; i < tables.length; i++) {
                if (tables[i].tableStatus == TableStatus.FREE) {
                    currentTableNumber = i+1;
                    System.out.println("You are working on table " + currentTableNumber);
                    allTablesTaken = false;
                    tables[i].takeOrder();
                    break;
                }
        }
        }
        if (allTablesTaken == true) {
            System.out.println("I am sorry, there is no empty table");
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
        System.out.println(ANSI_YELLOW + " ,+.      ,=|=.     ,+.       ,-\"-. \n" +
                "((|))    (XXXXX)   //|\\\\     / ,-. \\\n" +
                " )|(      |   STARTERS ||    |(:::)|\n" +
                "((|))     \\   /    \\\\|//     \\ `-' /\n" +
                " `-'       `+'      `+'       `-.-'" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("starters"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showMainCourse() {
        System.out.println(ANSI_RED +
                "        _.:`.--|--.`:._\n" +
                "     .: .'\\o  | o /'. '.\n" +
                "    // '.  MAIN COURSES '.\\\n" +
                "    //'._o'. \\ |o/ o_.-'o\\\\\n" +
                "    || o '-.'.\\|/.-' o   ||" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("main"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDeserts() { //fix it for desserts
        System.out.println(ANSI_YELLOW +
                "         (\n" +
                "          )\n" +
                "     __..---..__\n" +
                " ,-='  /  |  \\  `=-.\n" +
                ":--.._DESSERTS_..--;\n" +
                " \\.,_____________,./" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("deserts"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDrinks() {
        System.out.println(ANSI_BLUE +
                " .-'---------|  \n" +
                "( C|/\\/\\/\\/\\/|\n" +
                " '-/\\ DRINKS |\n" +
                "   '_________'\n" +
                "    '-------'" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("drinks"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
