package persistence;


import model.CustomerAccount;
import model.Item;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Customer accounts from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Customer account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<CustomerAccount> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCustomerAccounts(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Customer account list from JSON object and returns it
    private ArrayList<CustomerAccount> parseCustomerAccounts(JSONObject jsonObject) {
        ArrayList<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
        JSONArray jsonArray = jsonObject.getJSONArray("customers");
        for (Object json : jsonArray) {
            ArrayList<Item> customerItems;
            JSONObject existingCustomerAccount = (JSONObject) json;
            String customerName = existingCustomerAccount.getString("name");
            Integer customerBalance = existingCustomerAccount.getInt("balance");
            JSONArray jsonCustomerItems = existingCustomerAccount.getJSONArray("itemlist");
            customerItems = parseCustomerItems(jsonCustomerItems);
            CustomerAccount customerAccount = new CustomerAccount(customerName, customerBalance);
            customerAccount.setItemlist(customerItems);
            customerAccounts.add(customerAccount);
        }
        return customerAccounts;
    }

    // EFFECTS: parses Customer item list from JSON object and returns it
    private ArrayList<Item> parseCustomerItems(JSONArray jsonArray) {
        ArrayList<Item> customerItems = new ArrayList<Item>();
        for (Object json : jsonArray) {
            JSONObject existingCustomerItem = (JSONObject) json;
            String itemName = existingCustomerItem.getString("name");
            Integer itemId = existingCustomerItem.getInt("itemId");
            Integer period = existingCustomerItem.getInt("period");
            Integer rent = existingCustomerItem.getInt("rent");
            Item item = new Item(itemName, itemId, period, rent);
            customerItems.add(item);
        }
        return customerItems;
    }

//    // MODIFIES: wr
//    // EFFECTS: parses Customers from JSON object and adds them to workroom
//    private void addCustomers(CustomerAccount customerAccount, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("items");
//        for (Object json : jsonArray) {
//            JSONObject nextAccount = (JSONObject) json;
//            addItem(customerAccount, nextAccount);
//        }
//    }
//
//    // MODIFIES: wr
//    // EFFECTS: parses Items from JSON object and adds them to workroom
//    private void addItems(CustomerAccount customerAccount, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("items");
//        for (Object json : jsonArray) {
//            JSONObject nextAccount = (JSONObject) json;
//            addItem(customerAccount, nextAccount);
//        }
//    }

//    // MODIFIES: wr
//    // EFFECTS: parses Item from JSON object and adds it to CustomerAccount
//    private void addItem(CustomerAccount customerAccount, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        Integer itemid = jsonObject.getInt("itemId");
//        Integer period = jsonObject.getInt("period");
//        Integer rent = jsonObject.getInt("rent");
//        Item item = new Item(name, itemid, period, rent);
//        customerAccount.addItem(item);
//    }
//}
}
