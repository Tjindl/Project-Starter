package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item testItem1;
    private Item testItem2;

    @BeforeEach
    void runBefore() {
        testItem1 = new Item("Ski", 1, 7, 15);
        testItem2 = new Item("Snowboard", 2, 3, 20);
    }

    @Test
    void testConstructor() {
        assertEquals("Ski", testItem1.getname());
        assertEquals(1, testItem1.getitemid());
        assertEquals(7, testItem1.getperiod());
        assertEquals(15, testItem1.getrent());

        assertEquals("Snowboard", testItem2.getname());
        assertEquals(2, testItem2.getitemid());
        assertEquals(3, testItem2.getperiod());
        assertEquals(20, testItem2.getrent());
    }

    @Test
    void testRentCalculatorPerItem() {
        assertEquals(105, testItem1.rentCalculatorPerItem());
        assertEquals(60, testItem2.rentCalculatorPerItem());
    }
}
