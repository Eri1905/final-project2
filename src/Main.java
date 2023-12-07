import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        File file=new File("src/menu.txt");
        System.out.println("Please enter the number of the role you want to log in as: Waitress(1) or Chef(2)");
        int num=sc.nextInt();
        switch (num){
            case 1: Waitress waitress=new Waitress();
                System.out.println("Please enter the number of the task you want to do: See the menu(1); Change the menu(2); ");
            int numbersWaitress= sc.nextInt();
            switch (numbersWaitress){
                case 1:
                    System.out.println("Please enter the number of the type you want to see: Starters(1); Main course(2); Deserts(3); Drinks(4)");
                    int numsTypes=sc.nextInt();
                    Path path = Paths.get("src/menu.txt");
                    switch (numsTypes){
                        case 1:
                            try {
                                Files.lines(path)
                                        .filter(line -> line.contains("starters"))
                                        .forEach(System.out::println);
                            } catch (IOException e) {
                                System.err.println("Error reading the file: " + e.getMessage());
                            }break;
                        case 2:
                            try {
                                Files.lines(path)
                                        .filter(line -> line.contains("main"))
                                        .forEach(System.out::println);
                            } catch (IOException e) {
                                System.err.println("Error reading the file: " + e.getMessage());
                            }break;
                        case 3:
                            try {
                                Files.lines(path)
                                        .filter(line -> line.contains("deserts"))
                                        .forEach(System.out::println);
                            } catch (IOException e) {
                                System.err.println("Error reading the file: " + e.getMessage());
                            }break;
                        case 4:
                            try {
                                Files.lines(path)
                                        .filter(line -> line.contains("drinks"))
                                        .forEach(System.out::println);
                            } catch (IOException e) {
                                System.err.println("Error reading the file: " + e.getMessage());
                            }break;
                    }
            }
            break;
            case 2: Chef chef=new Chef();
        }



    }
}