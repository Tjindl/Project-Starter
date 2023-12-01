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
    private ArrayList<CustomerAccount> customerAccounts = new ArrayList<>(); // List for current work
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

    // EFFECTS : runs the CustomerApp application with gui
    public CustomerApp(String withgui) {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }






    // MODIFIES: this
    // EFFECTS: processes user input
    void runCustomer() {
        boolean keepGoing = true;
        String runCustCommand = null;
        init();
        while (keepGoing) {
            displayMenu();
            runCustCommand = input.next().toLowerCase();
            if (runCustCommand.equals("q")) {
                String command2 = null;
                System.out.println("Do you want to save your work? \n y->yes and n->no and l->load");
                command2 = input.next();
                if (command2 == "n") {
                    keepGoing = false;
                } else {
                    saveWorkRoom();
                    keepGoing = false;
                }
            } else {
                processCommand(runCustCommand);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user input for gui app
    void runCustomer(String quitstr) {
        boolean keepGoing = true;
        init();
        while (keepGoing) {

            String runCustCommand = "q";
            if (runCustCommand.equals("q")) {
                String command2 = null;
                keepGoing = false;
            } else {
                processCommand(runCustCommand);
            }
        }
        System.out.println("\nGoodbye!");
    }




    // MODIFIES : saves the data to a json file.
    void saveWorkRoom() {
        try {

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
    public void loadWorkRoom() {
        try {
            this.existingCustomerAccounts = jsonReader.read();
//            System.out.println("Loaded " + existingCustomerAccounts.toString() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    public void processCommand(String command) {
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
    void doDeposit() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: existingCustomerAccounts) {
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
    // EFFECTS: conducts a deposit transaction by taking input
    void doDeposit(int id, int amount) {
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == id) {
                ca.deposit(amount);
//                System.out.println(ca.getPaymentLog());
            }
        }
//        System.out.println("Cannot find your account, please make one\n");
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

    // REQUIRES : accID is in list
    // EFFECTS : gives out the calculated rent
    public int doCalculate(int accID) {
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == accID) {
                return ca.calculateRent();
            }
        }
        return 0;
    }

    @SuppressWarnings("methodlength")
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
                System.out.print("enter period you want it for");
                int per = input.nextInt();
                if (i == 1) {
                    ca.addItem(new Item("Ski", 1,per,15));
                    System.out.print("Ski successfully issued!");
                } else if (i == 2) {
                    ca.addItem(new Item("Snowboard", 2,per,20));
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

    // REQUIRES : a valid itemid and a valid accId
    // MODIFIES : a customer in existingCustomerAccounts
    // EFFECTS : adds the item related to given itemid and period to the customer related to accId
    public void doBorrow(int accId, int itemid, int period) {
        boolean accountFound = false;
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == accId) {
                accountFound = true;
                if (itemid == 1) {
                    ca.addItem(new Item("Ski", 1,period,15));
                } else if (itemid == 2) {
                    ca.addItem(new Item("Snowboard", 2,period,20));
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
        boolean accountFound = false;
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: customerAccounts) {
            if (ca.getId() == amount) {
                accountFound = true;
                System.out.print("Enter item id : ");
                int itemid = input.nextInt();
                ca.returnItem(itemid);
                System.out.println("returned!");
                break;
            }
        }
        if (!accountFound) {
            System.out.println("Cannot find your account, please make one\n");
        }
    }


    // MODIFIES: this
    // EFFECTS: conducts a return transaction for a specific account
    public void doReturn(int accId, int itemId) {
        boolean accountFound = false;
        ArrayList<CustomerAccount> traversalList = new ArrayList<>();
        traversalList.addAll(existingCustomerAccounts);
        traversalList.addAll(customerAccounts);
        for (CustomerAccount ca: traversalList) {
            if (ca.getId() == accId) {
                accountFound = true;
                ca.returnItem(itemId);
            }
        }
        if (!accountFound) {
            System.out.println("Cannot find your account, please make one\n");
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

    // REQUIRES : customerAcct and itemid exists
    // EFFECTS : returns the list of items of type itemid which customerAcc has borrowed
    public List<String> doListItem(int customerAcct, int itemid) {
        boolean accountFound = false;
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == customerAcct) {
                accountFound = true;
                if (itemid == 1) {
                    return (ca.findItemFromThisAccount(1));
                } else if (itemid == 2) {
                    return ca.findItemFromThisAccount(2);
                } else {
                    System.out.println("Selection not valid...");
                }
            }
        }
        if (!accountFound) {
            System.out.println("Cannot find your account, please make one\n");
        }
        return null;
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

    // MODIFIES : customerAccounts
    // EFFECTS : makes new customer with given name and deposit and gives out the generated customer id
    public int doAddCustomer(String name, int deposit) {
        CustomerAccount nct = new CustomerAccount(name,deposit);
        this.customerAccounts.add(nct);
        return (nct.getId());
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

    // REQUIRES : customerAcc exists
    // EFFECTS : gives out the payment log of the related customer
    public ArrayList<Integer> doPaymentLog(int customerAcc) {
        for (CustomerAccount ca: existingCustomerAccounts) {
            if (ca.getId() == customerAcc) {
                return ca.getPaymentLog();
            }
        }
        return null;
    }




    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        loadWorkRoom();
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

    public ArrayList<CustomerAccount> getExistingCustomerAccounts() {
        return existingCustomerAccounts;
    }

}
