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
        assertEquals(1, testItem1.getItemId());
        assertEquals(7, testItem1.getperiod());
        assertEquals(15, testItem1.getrent());

        assertEquals("Snowboard", testItem2.getname());
        assertEquals(2, testItem2.getItemId());
        assertEquals(3, testItem2.getperiod());
        assertEquals(20, testItem2.getrent());

    }

    @Test
    void testRentCalculatorPerItem() {
        assertEquals(105, testItem1.rentCalculatorPerItem());
        assertEquals(60, testItem2.rentCalculatorPerItem());
    }

    @Test
    void testSetters() {
        testItem1.setItemId(5);
        testItem1.setPeriod(1);
        testItem1.setRent(13);
        testItem1.setName("abc");
        assertEquals(5,testItem1.getItemId());
        assertEquals(1, testItem1.getperiod());
        assertEquals(13, testItem1.getrent());
        assertEquals("abc", testItem1.getname());
    }
}
