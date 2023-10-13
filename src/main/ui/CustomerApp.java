package ui;

import model.CustomerAccount;
import model.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerApp {
    private CustomerAccount Ca1;
    private ArrayList<CustomerAccount> CustomerAccountList = new ArrayList<>();
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




    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("cr")) {
            doCalculate();
        } else if (command.equals("b")) {
            doBorrow();
//        } else if (command.equals("r")) {
//            doReturn();
        } else if (command.equals("ts")) {
            doAvailabeStock();
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


    private void doDeposit() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.print("Enter amount to deposit: $");
                int amt = input.nextInt();
                ca.deposit(amt);
                int bal = ca.getBalance();
                System.out.printf("Your total balance is: \n", bal);
            } else {
            System.out.println("Cannot find your account, please make one\n");
        }

    }}

    private void doCalculate() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.printf("Your total balance is: ", ca.calculateRent());
            }
        }
        System.out.println("Cannot find your account, please make one\n");
    }

    private void doBorrow() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.print("enter 1 for Ski or 2 for Snowboard?");
                int i = input.nextInt();
                if (i == 1) {
                    ca.addItem(new Item("Ski", 1,0,15));
                    System.out.printf("Ski succesfully issued!");
                } else if (i == 2) {
                    ca.addItem(new Item("Snowboard", 2,0,20));
                    System.out.printf("Snowboard succesfully issued!");
                } else {
                    System.out.println("Selection not valid...");
                }

        } else {  System.out.println("Cannot find your account, please make one\n");
            }
        }
    }

//    private void doReturn() {
//        System.out.print("Enter account id : ");
//        int amount = input.nextInt();
//        for (CustomerAccount ca: CustomerAccountList) {
//            if (ca.getId() == amount) {
//                System.out.print("Ski or Snowboard?");
//                String str = input.next();
//                if (str == "Ski") {
//                    ca.addItem(new Item("Ski", 1,0,15));
//                } else if (str == "Snowboard") {
//                    ca.addItem(new Item("Snowboard", 2,0,20));
//                } else {
//                    System.out.println("Selection not valid...");
//                }
//            }
//            System.out.println("Cannot find your account, please make one\n");
//        }
//    }

    private void doAvailabeStock() {
        int i = Ca1.totalStockPresentInStore();
        System.out.printf("Available stock: ", Integer.toString(i));
    }

    private void doRentedStock() {
        System.out.printf("rented stock: ",Ca1.totalItemsRented());
    }

    private void doListItem() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.print("Enter item id : ");
                int amt = input.nextInt();
                if (amt == 1) {
                    System.out.printf("required list: ",Ca1.findItemFromThisAccount(1));
                } else if (amt == 2) {
                    System.out.printf("required list: ",Ca1.findItemFromThisAccount(2));
                } else {
                    System.out.println("Selection not valid...");
                }
            }
            System.out.println("Cannot find your account, please make one\n");
        }
    }

    private void doRemainingTime() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.print("Enter item id : ");
                int amt = input.nextInt();
                if (amt == 1) {
                    System.out.printf("required list: ",Ca1.remainingLendingTime(1));
                } else if (amt == 2) {
                    System.out.printf("required list: ",Ca1.remainingLendingTime(2));
                } else {
                    System.out.println("Selection not valid...");
                }
            }
            System.out.println("Cannot find your account, please make one\n");
        }
    }



    private void doAddCustomer() {
        System.out.print("Enter your name: ");
        String str = input.next();
        System.out.print("Please enter your deposit ");
        int amt = input.nextInt();
        CustomerAccount nct = new CustomerAccount(str,amt);
        System.out.printf("A new account has been added, Account id: ", nct.getId());
    }


    private void doPaymentLog() {
        System.out.print("Enter account id : ");
        int amount = input.nextInt();
        for (CustomerAccount ca: CustomerAccountList) {
            if (ca.getId() == amount) {
                System.out.printf("Log", ca.getPaymentLog());

            }
        }
        System.out.println("Cannot find your account, please make one\n");
    }



    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        Ca1 = new CustomerAccount("Tushar", 500);
        Ca1.setId(2);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        CustomerAccountList.add(Ca1);
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tcr -> Calculate rent");
        System.out.println("\tb -> borrow item");
        System.out.println("\tr -> return item");
        System.out.println("\tts -> see available stock");
        System.out.println("\trs -> rented stock");
        System.out.println("\tli -> list of items rented");
        System.out.println("\trlt -> remaining lending time");
        System.out.println("\tq -> quit");
    }


}
