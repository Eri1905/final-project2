import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class testTables {
    @Test
    public void testCheckForAvailabilityOfProduct_WhenProductExists_ReturnsTrue() {
        Tables tables = new Tables(1);
        boolean result = tables.checkForAvailabilityOfProduct("juice");
        assertTrue(result);
    }

    @Test
    public void testCheckForAvailabilityOfProduct_WhenProductDoesNotExist_ReturnsFalse() {
        Tables tables = new Tables(1);
        boolean result = tables.checkForAvailabilityOfProduct("NonexistentDish");
        assertFalse(result);
    }
    @Test
    public void testTakeOrder_WhenOrderIsTaken_StatusChanges() {
        Tables tables = new Tables(1);
        String input = "12-03-2023 14:30:00\nPizza\nNo\nBurger\nYes\n";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        tables.takeOrder();

        assertEquals(TableStatus.TAKEN, tables.tableStatus);
        assertEquals(OrderStatus.TAKEN, tables.tableOrder.orderStatus);
    }

}
