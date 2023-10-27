package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a customer account and its properties
public class CustomerAccount implements Writable {
    private String name;
    private ArrayList<Item> itemlist;
    private int id;
    private int balance;
    private static int nextAccountId = 0;
    private ArrayList<Integer> paymentLog;
    private ArrayList<Item> allRentedList = new ArrayList<>();
    public final int totalInventory = 50;
    private final int maxDuration = 30;



    // MODIFIES : this
    // EFFECTS : constructs a new customer account
    public CustomerAccount(String name, int deposit) {
        this.name = name;
        this.balance = deposit;
        this.id = nextAccountId++;
        this.itemlist = new ArrayList<>();
        this.paymentLog = new ArrayList<>();
        paymentLog.add(deposit);

    }

    // REQUIRES : amount >=0
    // MODIFIES : this, payment log
    // EFFECTS : Deposits the amount in tha balance and notes a log of the deposit
    public void deposit(int amount) {
        this.balance = this.balance + amount;
        this.paymentLog.add(amount);

    }




    // REQUIRES : amount <= balance
    // MODIFIES : this
    // EFFECTS : Deducts the rent from funds
    public void deductRentFromFunds(int amount) {
        this.balance = this.balance - amount;
        this.paymentLog.add(-amount);
    }

    public int getId() {
        return this.id;
    }

    public int setId(int i) {
        return this.id = i;
    }

    public int getBalance() {
        return this.balance;
    }

    public ArrayList getItemlist() {
        return this.itemlist;
    }

    public void setItemlist(ArrayList<Item> itemlist) {
        this.itemlist = itemlist;
    }


    // EFFECTS : calculates the total rent
    public int calculateRent() {
        int totalsofar = 0;
        for (Item i : itemlist) {
            totalsofar = i.rentCalculatorPerItem() + totalsofar;
        }
        return totalsofar;
    }

    // MODIFIES : this
    // EFFECTS : adds the item to the list of rented items
    public void addItem(Item i) {
        this.itemlist.add(i);
        allRentedList.add(i);
    }


    // MODIFIES : this
    // EFFECTS : returns an item and deducts its rent from the account balance
    public void returnItem(int itemid) {
        for (Item i : this.itemlist) {
            if (i.getItemId() == itemid) {
                this.itemlist.remove(i);
                this.balance = this.balance - i.getRent();
            }
            break;
        }
    }

    // MODIFIES : this
    // EFFECTS : removes the item to the list of rented items
    public void removeItem(Item i) {
        for (Item i1 : this.itemlist) {
            if (i1.getItemId() == i.getItemId()) {
                this.itemlist.remove(i1);
                allRentedList.remove(i1);
                this.balance = this.balance - i1.rentCalculatorPerItem();
            }
            break;
        }
    }

    // EFFECTS : calculates the number of rented items
    public int totalItemsRented() {
        return allRentedList.size();
    }



    // EFFECTS : calculates the total stock
    public int totalStockPresentInStore() {
        return (totalInventory - this.totalItemsRented());
    }


    // EFFECTS : gives the list of items of the given item id
    public List findItemFromThisAccount(int itemID) {
        List<Integer> lst = new ArrayList<>();
        for (Item i2 : this.itemlist) {
            if (i2.getItemId() == itemID) {
                lst.add(i2.getItemId());
            }
        }
        return lst;
    }



    // EFFECTS : gives the list of payments made by a customer
    public ArrayList<Integer> getPaymentLog() {
        return this.paymentLog;
    }


    // EFFECTS : gives the remaining lending time for an item
    public int remainingLendingTime(int itemid) {
        int ans = 0;
        for (Item i : this.itemlist) {
            if (itemid == i.getItemId()) {
                ans = (maxDuration - i.getPeriod());
            }
            break;
        }
        return ans;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("balance", this.balance);
        json.put("payment_log", this.paymentLog);
        json.put("items", itemsToJson());
        json.put("calculated rent", calculateRent());
        return json;
    }

    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : itemlist) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }


    public String getName() {
        return this.name;
    }
}
