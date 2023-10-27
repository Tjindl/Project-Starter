package persistence;


import model.CustomerAccount;
import model.Item;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CustomerAccount read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCustomerAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private CustomerAccount parseCustomerAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer dep = jsonObject.getInt("deposit");
        CustomerAccount customerAccount = new CustomerAccount(name, dep);
        addItems(customerAccount, jsonObject);
        return customerAccount;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addItems(CustomerAccount customerAccount, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addThingy(customerAccount, nextAccount);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addThingy(CustomerAccount customerAccount, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer itemid = jsonObject.getInt("itemId");
        Integer period = jsonObject.getInt("period");
        Integer rent = jsonObject.getInt("rent");
        Item item = new Item(name, itemid, period, rent);
        customerAccount.addItem(item);
    }
}
