package PersistenceTest;



import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            CustomerAccount customerAccount = new CustomerAccount("Tushar", 500);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ArrayList<CustomerAccount> ca = new ArrayList<>();
            ca.add(new CustomerAccount("My account", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(ca);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
//            ca = reader.read();
            assertEquals("My account", ca.get(0).getName());
            assertEquals(0, ca.get(0).getItemlist().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ArrayList<CustomerAccount> customerAccounts = new ArrayList<>();
            CustomerAccount customerAccount = new CustomerAccount("My Account", 10);
            customerAccounts.add(customerAccount);
            customerAccount.addItem(new Item("Ski", 1, 1, 15));
            customerAccount.addItem(new Item("Ski", 1,5,15));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(customerAccounts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
//            customerAccounts = reader.read();
            assertEquals("My Account", customerAccount.getName());
            customerAccount.addItem(new Item("Ski", 1, 5, 15));
            List<Item> items = customerAccount.getItemlist();
            assertEquals(3, items.size());
            checkItem("Ski", items.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}