package PersistenceTest;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, Item item) {
        assertEquals(name, item.getname());
    }
}