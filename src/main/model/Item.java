package model;

import org.json.JSONObject;

// Represents the items and their properties
public class Item {
    private int period;
    private int rent;
    private int itemId;
    private static int nextItemId = 1;
    private String name;


    public void setPeriod(int period) {
        this.period = period;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getItemId() {
        return itemId;
    }

    // MODIFIES : this
    // EFFECTS : Constructs an item
    public Item(String name, int itemId, int period, int rent) {
        this.period = period;
        this.itemId = itemId;
        this.rent = rent;
        this.name = name;
    }


    public int getrent() {
        return this.rent;
    }

    //EFFECTS : calculates the rent of that particular item
    public int rentCalculatorPerItem() {
        return ((this.period) * (this.rent));
    }

    public String getname() {
        return this.name;
    }

    public int getperiod() {
        return this.period;
    }



    // EFFECTS : Function to convert item to json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemId", this.itemId);
        json.put("name", this.name);
        json.put("period", this.period);
        json.put("rent", this.rent);
        return json;
    }
}
