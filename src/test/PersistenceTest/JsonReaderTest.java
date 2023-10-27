package PersistenceTest;

import model.CustomerAccount;
import model.Item;

import org.junit.jupiter.api.Test;

import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<CustomerAccount> customerAccounts = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            ArrayList<CustomerAccount> customerAccounts = reader.read();
            assertEquals(0, customerAccounts.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            ArrayList<CustomerAccount> customerAccounts = reader.read();
            assertEquals(0, customerAccounts.get(0).getId());
            customerAccounts.get(0).addItem(new Item("Ski", 1, 5, 15));
            List<Item> items = customerAccounts.get(0).getItemlist();
            assertEquals(2, items.size());
            checkItem("Ski", items.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
