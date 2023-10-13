package model;

import java.util.Date;

public class Item {
    private int period;
    private int rent;
    private int itemId;
    private static int nextItemId = 1;
    private String name;




    public Item(String name, int itemId, int period, int rent) {
        this.period = period;
        this.itemId = itemId;
        this.rent = rent;
        this.name = name;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getRent() {
        return this.rent;
    }

    public int rentCalculatorPerItem() {
        return ((this.period) * (this.rent));
    }

    public String getName() {
        return this.name;
    }

    public int getPeriod() {
        return this.period;
    }
}
