package model;

import java.util.Date;

public class Item {
    private int period;
    private int rent;
    private int itemId;
    private static int nextItemId = 1;


    public Item(int itemId, int period, int rent) {
        this.period = period;
        this.itemId = nextItemId++;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int rentCalculatorPerItem() {
        return ((this.period) * (this.rent));
    }

}
