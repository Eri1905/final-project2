import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class testWaitress {
    @Test
    void testCreateTablesMethodGiven4Tables(){
        String tableAmount = "4";
        System.setIn(new ByteArrayInputStream(tableAmount.getBytes()));
        Scanner scanner = new Scanner(System.in);
        Waitress waitress = new Waitress();
        waitress.setSc(scanner);

        Tables[] result = waitress.createTables();

        Assertions.assertEquals(4, result.length);

        System.setIn(System.in);
        scanner.close();
    }
    @Test
    void testGoOnMehodGivenYes(){

}
}

