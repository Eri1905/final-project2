import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class testWaitress {
    private InputStream originalSystemIn;
    private ByteArrayInputStream simulatedInput;
    private final PrintStream originalSystemOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Before
    public void setUp() {
        originalSystemIn = System.in;
    }

    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
    }
    @Test
    void testCreateTablesMethodGiven4Tables() {
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
    void testGoOnMethodGivenYes() {
        simulatedInput = new ByteArrayInputStream("yes\n".getBytes());
        System.setIn(simulatedInput);
        Scanner scanner = new Scanner(System.in);
        Waitress waitress = new Waitress();
        waitress.setSc(scanner);

        boolean result = waitress.goOn();

        Assertions.assertTrue(result);
    }

    @Test
    void testGoOnMethodGivenNo() {
        simulatedInput = new ByteArrayInputStream("no\n".getBytes());
        System.setIn(simulatedInput);
        Scanner scanner = new Scanner(System.in);
        Waitress waitress = new Waitress();
        waitress.setSc(scanner);

        boolean result = waitress.goOn();

        Assertions.assertFalse(result);
    }

    @Before
    public void setUp2() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown2() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    public void testCheckForFree_TableIsFree() {
        // Arrange
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        Tables[] tables = new Tables[3];
        tables[0] = new Tables(1);
        tables[0].setTableStatus(TableStatus.FREE);

        Waitress waitress = new Waitress();
        waitress.setSc(new Scanner(System.in));

        // Act
        waitress.checkForFree(tables);

        // Assert
        Assertions.assertEquals(TableStatus.TAKEN, tables[0].getTableStatus());
    }

    @Test
    public void testCheckForFree_TableIsTaken_FindNextFreeTable() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        Tables[] tables = new Tables[3];
        tables[0] = new Tables(1);
        tables[0].setTableStatus(TableStatus.TAKEN);
        tables[1] = new Tables(2);
        tables[1].setTableStatus(TableStatus.FREE);

        Waitress waitress = new Waitress();
        waitress.setSc(new Scanner(System.in));

        // Act
        waitress.checkForFree(tables);

        // Assert
        Assertions.assertEquals(TableStatus.TAKEN, tables[0].getTableStatus());
        Assertions.assertEquals(TableStatus.TAKEN, tables[1].getTableStatus());
    }

    @Test
    public void testCheckForFree_AllTablesTaken() {
        // Arrange
        System.setIn(new ByteArrayInputStream("1\n".getBytes())); // Simulate user input of "1"

        Tables[] tables = new Tables[3];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Tables(i + 1);
            tables[i].setTableStatus(TableStatus.TAKEN);
        }

        Waitress waitress = new Waitress();
        waitress.setSc(new Scanner(System.in));

        // Act
        waitress.checkForFree(tables);

        // Assert
        Assertions.assertTrue(outputStreamCaptor.toString().contains("I am sorry, there is no empty table"));
    }
}


