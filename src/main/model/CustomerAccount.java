package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a customer account and its properties
public class CustomerAccount {
    private String name;
    private ArrayList<Item> itemlist;
    private int id;
    private int balance;
    private static int nextAccountId = 0;

    private ArrayList<Integer> paymentLog;
    private ArrayList<Item> allRentedList = new ArrayList<>();
    public final int totalInventory = 50;
    private final int maxDuration = 30;



    // setter for payment log
    public void setPaymentLog(ArrayList<Integer> paymentLog) {
        this.paymentLog = paymentLog;
    }

    // MODIFIES : this
    // EFFECTS : constructs a new customer account
    public CustomerAccount(String name, int deposit) {
        EventLog.getInstance().logEvent(new Event("Customer added"));
        this.name = name;
        this.balance = deposit;
        this.id = nextAccountId++;
        this.itemlist = new ArrayList<>();
        this.paymentLog = new ArrayList<>();
        this.paymentLog.add(deposit);

    }



    // REQUIRES : amount >=0
    // MODIFIES : this, payment log
    // EFFECTS : Deposits the amount in tha balance and notes a log of the deposit
    public void deposit(int amount) {
        this.balance = this.balance + amount;
        this.paymentLog.add(amount);
        EventLog.getInstance().logEvent(new Event("Deposit made to the Account"));

    }




    // REQUIRES : amount <= balance
    // MODIFIES : this
    // EFFECTS : Deducts the rent from funds
    public void deductRentFromFunds(int amount) {
        this.balance = this.balance - amount;
        this.paymentLog.add(-amount);
        EventLog.getInstance().logEvent(new Event("Rent Deducted from the funds"));
    }

    public int getId() {
        return this.id;
    }

    public int getBalance() {
        return this.balance;
    }

    public ArrayList<Item> getItemlist() {
        return this.itemlist;
    }

    public void setItemlist(ArrayList<Item> itemlist) {
        this.itemlist = itemlist;
    }


    // EFFECTS : calculates the total rent
    public int calculateRent() {
        EventLog.getInstance().logEvent(new Event("Rent Calculated for this Account"));
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
        EventLog.getInstance().logEvent(new Event("Item added to this Account."));
    }


    // MODIFIES : this
    // EFFECTS : returns an item and deducts its rent from the account balance
    public void returnItem(int itemid) {
        EventLog.getInstance().logEvent(new Event("Item Returned"));
        for (Item i : this.itemlist) {
            if (i.getItemId() == itemid) {
                this.itemlist.remove(i);
                this.balance = this.balance - i.getrent();
            }
            break;
        }
    }

    // MODIFIES : this
    // EFFECTS : removes the item to the list of rented items
    public void removeItem(Item i) {
        EventLog.getInstance().logEvent(new Event("Item Removed from Account"));
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
        EventLog.getInstance().logEvent(new Event("Total rented Items Calculated"));
        return allRentedList.size();
    }



    // EFFECTS : calculates the total stock
    public int totalStockPresentInStore() {
        EventLog.getInstance().logEvent(new Event("Total store stock Calculated"));
        return (totalInventory - this.totalItemsRented());
    }


    // EFFECTS : gives the list of items of the given item id
    public List<String> findItemFromThisAccount(int itemID) {
        EventLog.getInstance().logEvent(new Event("Searched Item by ItemId from this Account"));
        List<String> lst = new ArrayList<>();
        for (Item i2 : this.itemlist) {
            if (i2.getItemId() == itemID) {
                lst.add(i2.getname() + "-" + i2.getperiod() + "days");
            }
        }
        return lst;
    }



    // EFFECTS : gives the list of payments made by a customer
    public ArrayList<Integer> getPaymentLog() {
        EventLog.getInstance().logEvent(new Event("Payment log extracted"));
        return this.paymentLog;
    }


    // EFFECTS : gives the remaining lending time for an item
    public int remainingLendingTime(int itemid) {
        EventLog.getInstance().logEvent(new Event("the remaining lending time for an item Calculated"));
        int ans = 0;
        for (Item i : this.itemlist) {
            if (itemid == i.getItemId()) {
                ans = (maxDuration - i.getperiod());
            }
            break;
        }
        return ans;
    }




    // EFFECTS : writes customer account to json
    public JSONObject customerAccountToJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("balance", this.balance);
        json.put("payment_log", this.paymentLog);
        json.put("itemlist", itemsToJson());
        json.put("calculated rent", calculateRent());
        return json;
    }



    // EFFECTS : traverses the item list of a customer to write it into json
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : itemlist) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }


    public String getname() {
        return this.name;
    }

    public void setid(int i)     {
        this.id = i;
    }
}
