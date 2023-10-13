package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerAccountTest {
    private CustomerAccount testCustomerAccount;
    private Item testItem1;
    private Item testItem2;

    @BeforeEach
    void runBefore() {
        testCustomerAccount = new CustomerAccount("Paul", 500);
        testItem1 = new Item("Ski", 1, 7, 15);
        testItem2 = new Item("Snowboard", 2, 3, 20);
    }

    @Test
    void testConstructor() {
        assertEquals(500, testCustomerAccount.getBalance());
        assertEquals(1, testCustomerAccount.getId());
        ArrayList<Integer> tstPaymentLog = testCustomerAccount.getPaymentLog();
        assertEquals(1, tstPaymentLog.size());
        assertEquals(500, tstPaymentLog.get(0));
        ArrayList<Item> tstItemList = testCustomerAccount.getItemlist();
        assertEquals(0, tstItemList.size());
        testCustomerAccount.setId(5);
        assertEquals(5, testCustomerAccount.getId());
    }


    @Test
    void testDeposit() {
        testCustomerAccount.deposit(300);
        assertEquals(800, testCustomerAccount.getBalance());
        testCustomerAccount.deposit(200);
        assertEquals(1000, testCustomerAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        testCustomerAccount.deductRentFromFunds(100);
        assertEquals(400, testCustomerAccount.getBalance());
        testCustomerAccount.deductRentFromFunds(200);
        assertEquals(200, testCustomerAccount.getBalance());
    }

    @Test
    void testCalculateRent() {
        assertEquals(0, testCustomerAccount.calculateRent());
        testCustomerAccount.addItem(testItem1);
        assertEquals(105, testCustomerAccount.calculateRent());
        testCustomerAccount.addItem(testItem2);
        assertEquals(165, testCustomerAccount.calculateRent());
    }

    @Test
    void testAddItem() {
        testCustomerAccount.addItem(testItem1);
        ArrayList<Item> testList1 = testCustomerAccount.getItemlist();
        assertEquals(1, testList1.size());
        assertEquals("Ski", testList1.get(0).getName());
        testCustomerAccount.addItem(testItem2);
        ArrayList<Item> testList2 = testCustomerAccount.getItemlist();
        assertEquals(2, testList1.size());
    }

    @Test
    void testRemoveItem() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        testCustomerAccount.removeItem(testItem1);
        ArrayList<Item> testList1 = testCustomerAccount.getItemlist();
        assertEquals(1, testList1.size());
        assertEquals("Snowboard", testList1.get(0).getName());
    }

    @Test
    void testTotalItemsRented() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        assertEquals(2, testCustomerAccount.totalItemsRented());
    }


    @Test
    void testTotalStockPresentInStore() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        assertEquals(48, testCustomerAccount.totalStockPresentInStore());
    }

    @Test
    void testFindItemFromThisAccount() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        testCustomerAccount.addItem(testItem1);
        assertEquals(2, testCustomerAccount.findItemFromThisAccount(1).size());
        assertEquals(1, testCustomerAccount.findItemFromThisAccount(2).size());
    }

    @Test
    void testRemainingLendingTime() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        testCustomerAccount.addItem(testItem1);
        assertEquals(23, testCustomerAccount.remainingLendingTime(1));
    }

    @Test
    void testReturnItem() {
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.addItem(testItem2);
        testCustomerAccount.addItem(testItem1);
        testCustomerAccount.returnItem(1);
        assertEquals(2, testCustomerAccount.getItemlist().size());
    }


}