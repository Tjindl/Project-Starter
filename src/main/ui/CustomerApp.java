package ui;

import model.CustomerAccount;
import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class CustomerApp {
    private CustomerAccount ca1;
    private ArrayList<CustomerAccount> customerAccounts = new ArrayList<>();
    private Scanner input;


    // EFFECTS: runs the CustomerApp application
    public CustomerApp() {
        runCustomer();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCustomer() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }




    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("cr")) {
            doCalculate();
        } else if (command.equals("b")) {
            doBorrow();
        } else if (command.equals("r")) {
            doReturn();
        } else if (command.equals("ts")) {
            doAvailableStock();
        } else if (command.equals("rs")) {
            doRentedStock();
        } else if (command.equals("li")) {
            doListItem();
        } else if (command.equals("rlt")) {
            doRemainingTime();
        } else if (command.equals("ac")) {
            doAddCustomer();
        } else if (command.equals("pl")) {
            doPaymentLog();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doDeposit() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Enter amount to deposit: $");
                int amt = input.nextInt();
                ca.deposit(amt);
                System.out.print("Balance: ");
                System.out.println(Integer.toString(ca.getBalance()));
//
            } else {
                System.out.println("Cannot find your account, please make one\n");
            }
        }
    }



    // MODIFIES: this
    // EFFECTS: Calculates rent
    private void doCalculate() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Your total rent is: ");
                System.out.println(Integer.toString(ca.calculateRent()));
            }
            System.out.println("Cannot find your account, please make one\n");
        }

    }

    // MODIFIES: this
    // EFFECTS: conducts a borrow transaction
    private void doBorrow() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("enter 1 for Ski or 2 for Snowboard?");
                int i = input.nextInt();
                if (i == 1) {
                    ca.addItem(new Item("Ski", 1,0,15));
                    System.out.print("Ski succesfully issued!");
                } else if (i == 2) {
                    ca.addItem(new Item("Snowboard", 2,0,20));
                    System.out.print("Snowboard succesfully issued!");
                } else {
                    System.out.println("Selection not valid...");
                }
            } else {
                System.out.println("Cannot find your account, please make one\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doReturn() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Enter item id : ");
                int itemid = input.nextInt();
                ca.returnItem(itemid);
                System.out.println("returned!");
            } else {
                System.out.println("Cannot find your account, please make one\n");
            }
        }
    }




    // EFFECTS: gives available stock
    private void doAvailableStock() {
        int i = ca1.totalStockPresentInStore();
        System.out.print("Available stock: ");
        System.out.println(Integer.toString(i));
    }


    // EFFECTS: gives rented stock
    private void doRentedStock() {
        System.out.print("rented stock: ");
        System.out.println(Integer.toString(ca1.totalItemsRented()));
    }


    // EFFECTS: gives item list of the given item id from a given account
    private void doListItem() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Enter item id : ");
                int amt = input.nextInt();
                if (amt == 1) {
                    System.out.print("required list: ");
                    System.out.println(Arrays.toString((ca1.findItemFromThisAccount(1)).toArray()));
                } else if (amt == 2) {
                    List lst = ca1.findItemFromThisAccount(amt);
                    System.out.print("required list: ");
                    for (int i = 0; i < lst.size(); i++) {
                        System.out.println(lst.get(i));
                    }

                } else {
                    System.out.println("Selection not valid...");
                }
            }
            System.out.println("Cannot find your account, please make one\n");
        }
    }


    // EFFECTS: gives the remaining lending time of the given item
    private void doRemainingTime() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Enter item id : ");
                int amt = input.nextInt();
                if (amt == 1) {
                    System.out.print("required list: ");
                    System.out.println(Integer.toString(ca1.remainingLendingTime(1)));
                } else if (amt == 2) {
                    System.out.print("required list: ");
                    System.out.println(Integer.toString(ca1.remainingLendingTime(2)));
                } else {
                    System.out.println("Selection not valid...");
                }
            }
            System.out.println("Cannot find your account, please make one\n");
        }
    }



    // MODIFIES: this
    // EFFECTS: adds a new customer to the system
    private void doAddCustomer() {
        System.out.print("Enter your name: ");
        String str = input.next();
        System.out.print("Please enter your deposit ");
        int amt = input.nextInt();
        CustomerAccount nct = new CustomerAccount(str,amt);
        System.out.print("A new account has been added, Account id: ");
        System.out.println(Integer.toString(nct.getId()));
    }


    // EFFECTS: gives the payment log for a given customer
    private void doPaymentLog() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                System.out.print("Log");
                System.out.println(Arrays.toString(ca.getPaymentLog().toArray()));

            }
        }
        System.out.println("Cannot find your account, please make one\n");
    }



    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        ca1 = new CustomerAccount("Tushar", 500);
        ca1.setId(2);
        ca1.addItem(new Item("Ski", 1, 2, 15));

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        customerAccounts.add(ca1);
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tcr -> Calculate rent");
        System.out.println("\tb -> borrow item");
        System.out.println("\tr -> return item");
        System.out.println("\tts -> see available stock");
        System.out.println("\trs -> rented stock");
        System.out.println("\tli -> list of items rented");
        System.out.println("\trlt -> remaining lending time");
        System.out.println("\tac -> add customer");
        System.out.println("\tpl -> payment log");
        System.out.println("\tq -> quit");
    }
}
