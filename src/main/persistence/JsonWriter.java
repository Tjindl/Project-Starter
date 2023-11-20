package persistence;

import model.CustomerAccount;

import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Represents a writer that writes JSON representation of Customer Account to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CustomerAccount to file
    public void write(ArrayList<CustomerAccount> customerAccountList) {
        Map<String, JSONArray> map = new HashMap<>();
        map.put("customers", CustomerAccountListToJsom(customerAccountList));
        JSONObject json = new JSONObject(map);
        saveToFile(json.toString(TAB));
    }

    public JSONArray CustomerAccountListToJsom(ArrayList<CustomerAccount> customerList) {
        JSONArray jsonArray = new JSONArray();

        for (CustomerAccount ca : customerList) {
            jsonArray.put(ca.CustomerAccountToJson());
        }

        return jsonArray;
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
