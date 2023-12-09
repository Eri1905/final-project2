import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Please enter the number of the role you want to log in as: Waitress(1) or Chef(2)");
        int num=sc.nextInt();
    switch (num){
            case 1: Waitress waitress=new Waitress();
                waitress.work();
            case 2: Chef chef=new Chef();
        }

    }
    }
