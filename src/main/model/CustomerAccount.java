package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerAccount {
    private ArrayList<Item> itemlist;
    private String custname;
    private int id;
    private int balance;
    private static int nextAccountId = 1;
    private ArrayList<Integer> paymentLog;
    private ArrayList<CustomerAccount> customerList;
    private final int totalInventory = 50;
    private final int maxDuration = 30;

    public CustomerAccount(String name, int deposit) {
        this.balance = deposit;
        this.id = nextAccountId++;
        this.itemlist = new ArrayList<Item>();
        this.paymentLog = new ArrayList<>();
        paymentLog.add(deposit);
        this.customerList = new ArrayList<>();
    }

    public void deposit(int amount) {
        this.balance = this.balance + amount;
        this.paymentLog.add(amount);
    }


    public void withdraw(int amount) {
        this.balance = this.balance - amount;
        this.paymentLog.add(-amount);
    }

    public int getId() {
        return this.id;
    }

    public int getBalance() {
        return this.balance;
    }

    public ArrayList getItemlist() {
        return this.itemlist;
    }

    public int getRent() {
        int totalsofar = 0;
        for (Item i : itemlist) {
            totalsofar = i.rentCalculatorPerItem() + totalsofar;
        }
        return totalsofar;
    }

    public void addItem(Item i) {
        this.itemlist.add(i);
    }

    public void removeItem(Item i) {
        for (Item i1 : itemlist) {
            if (i1.getItemId() == i.getItemId()) {
                this.itemlist.remove(i1);
                break;
            }
        }
    }

    public int totalItemsRented() {
        int rsf = 0;
        for (CustomerAccount cacc : this.customerList) {
            rsf = cacc.itemlist.size() + rsf;
        }
        return rsf;
    }

    public int totalStockPresentInStore() {
        return (totalInventory - this.totalItemsRented());
    }

    public List findItemFromThisAccount(int itemID) {
        List<Item> lst = new ArrayList<>();
        for (Item i2 : this.itemlist) {
            if (i2.getItemId() == itemID) {
                lst.add(i2);
            }
        }
        return lst;
    }

}
