package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerAccount {
    private ArrayList<Item> itemlist;
    private int id;
    private int balance;
    private static int nextAccountId = 1;
    private ArrayList<Integer> paymentLog;
    private ArrayList<Item> allRentedList = new ArrayList<>();
    private final int totalInventory = 50;
    private final int maxDuration = 30;

    public CustomerAccount(String name, int deposit) {
        this.balance = deposit;
        this.id = nextAccountId++;
        this.itemlist = new ArrayList<Item>();
        this.paymentLog = new ArrayList<>();
        paymentLog.add(deposit);
    }

    public void deposit(int amount) {
        this.balance = this.balance + amount;
        this.paymentLog.add(amount);

    }


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

    public int calculateRent() {
        int totalsofar = 0;
        for (Item i : itemlist) {
            totalsofar = i.rentCalculatorPerItem() + totalsofar;
        }
        return totalsofar;
    }

    public void addItem(Item i) {
        this.itemlist.add(i);
        allRentedList.add(i);
    }

    public void removeItem(Item i) {
        for (Item i1 : this.itemlist) {
            if (i1.getItemId() == i.getItemId()) {
                this.itemlist.remove(i1);
                allRentedList.remove(i1);
                this.balance = this.balance - i1.rentCalculatorPerItem();
                break;
            }
        }
    }

    public int totalItemsRented() {
        return allRentedList.size();
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

    public ArrayList<Integer> getPaymentLog() {
        return this.paymentLog;
    }


    public int remainingLendingTime(int itemid) {
        int ans = 0;
        for (Item i : this.itemlist) {
            if (itemid == i.getItemId()) {
                ans = (maxDuration - i.getPeriod());
                break;
            }
        }
        return ans;
    }



}
