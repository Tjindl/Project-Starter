package PersistenceTest;

import model.CustomerAccount;
import model.Item;

import org.junit.jupiter.api.Test;

import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CustomerAccount customerAccount = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            CustomerAccount customerAccount = reader.read();
            assertEquals(1, customerAccount.getId());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            CustomerAccount customerAccount = reader.read();
            assertEquals(1, customerAccount.getId());
            customerAccount.addItem(new Item("Ski", 1, 5, 15));
            List<Item> items = customerAccount.getItemlist();
            assertEquals(1, items.size());
            checkItem("Ski", items.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
