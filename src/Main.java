import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String PURPLE_BOLD = "\033[1;35m";

    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        System.out.println(PURPLE_BOLD  +
                "     .\n" +
                "    . .\n" +
                "      ...\n" +
                "   \\~~~~~/\n" +
                "    \\   /\n" +
                " W E L C O M E" +
                "      \n" +
                "      |\n" +
                "      |\n" +
                "     ---" );;
        System.out.println(ANSI_RESET + "Please enter the number of the role you want to log in as: Waitress(1) or Chef(2)");
        int num=sc.nextInt();
    switch (num){
            case 1: Waitress waitress=new Waitress();
                waitress.work();
            case 2: Chef chef=new Chef();
        }

    }
    }
