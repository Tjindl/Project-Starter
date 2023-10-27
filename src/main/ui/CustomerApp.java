package ui;

import model.CustomerAccount;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

// Represents the app/ console ui for the program
public class CustomerApp {
    private ArrayList<CustomerAccount> existingCustomerAccounts = new ArrayList<>();
    CustomerAccount ca1;
    public final int totalInventory = 50;
    private ArrayList<CustomerAccount> customerAccounts = new ArrayList<>();
    private Scanner input;
    private static final String JSON_STORE = "./data/workroomSample.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;




    // EFFECTS: runs the CustomerApp application
    public CustomerApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
                String command2 = null;
                System.out.println("Do you want to save your work? \n y->yes and n->no and l->load");
                command2 = input.next();
                if (command2 == "n") {
                    keepGoing = false;
                } else if (command2 == "l") {
                    loadWorkRoom();
                } else {
                    saveWorkRoom();
                    keepGoing = false;
                }
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }


    // MODIFIES : saves the data to a json file.
    private void saveWorkRoom() {
        try {
            System.out.println(existingCustomerAccounts);
            existingCustomerAccounts.addAll(customerAccounts);
                jsonWriter.open();
                jsonWriter.write(existingCustomerAccounts);
                jsonWriter.close();
                System.out.println("Saved " + existingCustomerAccounts.toString() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

//     MODIFIES: this
//     EFFECTS: loads data from file
    private void loadWorkRoom() {
        try {
            existingCustomerAccounts = jsonReader.read();
            System.out.println("Loaded " + existingCustomerAccounts.toString() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
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
                break;
            }
        }
        System.out.println("Cannot find your account, please make one\n");
    }

    // MODIFIES: this
    // EFFECTS: conducts a borrow transaction
    private void doBorrow() {
        boolean accountFound = false;
        System.out.print("Enter account id : ");
        int accountId = input.nextInt();
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == accountId) {
                accountFound = true;
                System.out.print("enter 1 for Ski or 2 for Snowboard?");
                int i = input.nextInt();
                if (i == 1) {
                    ca.addItem(new Item("Ski", 1,0,15));
                    System.out.print("Ski successfully issued!");
                } else if (i == 2) {
                    ca.addItem(new Item("Snowboard", 2,0,20));
                    System.out.print("Snowboard successfully issued!");
                } else {
                    System.out.println("Selection not valid...");
                }
                break;
            }
        }
        if (!accountFound) {
            System.out.println("Cannot find your account, please make one\n");
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
        int count = 0;
        for (CustomerAccount ca : customerAccounts) {
            count = ca.getItemlist().size() + count;
        }
        System.out.print("Available stock: ");
        System.out.println(Integer.toString(totalInventory - count));
    }


    // EFFECTS: gives rented stock
    private void doRentedStock() {
        int count = 0;
        for (CustomerAccount ca : customerAccounts) {
            count = ca.getItemlist().size() + count;
        }
        System.out.print("rented stock: ");
        System.out.println(Integer.toString(count));
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
                    System.out.println(Arrays.toString((ca.findItemFromThisAccount(1)).toArray()));
                } else if (amt == 2) {
                    List lst = ca.findItemFromThisAccount(amt);
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
                    System.out.println(Integer.toString(ca.remainingLendingTime(1)));
                } else if (amt == 2) {
                    System.out.print("required list: ");
                    System.out.println(Integer.toString(ca.remainingLendingTime(2)));
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
        String name = input.next();
        System.out.print("Please enter your deposit ");
        int deposit = input.nextInt();
        CustomerAccount nct = new CustomerAccount(name,deposit);
        this.customerAccounts.add(nct);
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
//        ca1 = new CustomerAccount("Tushar", 500);
//        ca1.setId(2);
//        ca1.addItem(new Item("Ski", 1, 2, 15));

        loadWorkRoom();
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//        customerAccounts.add(ca1);
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
