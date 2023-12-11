import java.util.Scanner;

public abstract class Restaurant {
    Scanner sc=new Scanner(System.in);
    Waitress waitress;
    Chef chef;
    int tableAmount;
    int tableNumber;
    Tables[] tables = new Tables[tableAmount];

}

