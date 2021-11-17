package com.revature.bankapp.services;

import com.revature.bankapp.daos.CustomerDao;
import com.revature.bankapp.daos.TransactionDao;
import com.revature.bankapp.exceptions.EmptyTransactionsException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.exceptions.UnownedAccountException;
import com.revature.bankapp.models.Transactions;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class TransactionServiceTestSuite {

    TransactionService sut;
    AccountService mockAccountService;
    TransactionDao mockTransactionDao;

    @Before
    public void testCaseSetup() {
        mockAccountService = mock(AccountService.class);
        mockTransactionDao = mock(TransactionDao.class);
        sut = new TransactionService(mockTransactionDao,mockAccountService);
    }

    @Test
    public void test_viewAllTransactions_returnsListOfTransactions_givenGoodCustomerId() {
        String customer_id = "1";
        List<Transactions> transactionList = new LinkedList<>();
        transactionList.add(new Transactions());
        transactionList.add(new Transactions());
        when(mockTransactionDao.viewAllAccountsTransactions(customer_id)).thenReturn(transactionList);

        List<Transactions> actualResult = sut.viewAllTransactions(customer_id);

        Assert.assertEquals(transactionList, actualResult);

    }
    @Test(expected = EmptyTransactionsException.class)
    public void test_viewAllTransactions_throwsEmptyTransactionsException_givenBadCustomerId() {
        String customer_id = "2";
        List<Transactions> emptyList = new LinkedList<>();
        when(mockTransactionDao.viewAllAccountsTransactions(customer_id)).thenReturn(emptyList);

        List<Transactions> actualResult = sut.viewAllTransactions(customer_id);

    }
/*
    @Test
    public void test_viewSingleTransactions_returnsTransactionListOfOneAccount_givenGoodAccountId() {
        String account_id = "1";
        UUID customer_id = UUID.randomUUID();
        List<Transactions> transactionsList = new LinkedList<>();
        transactionsList.add(new Transactions());

        when(mockTransactionDao.selectTransactionByAccountId(customer_id, account_id)).thenReturn(transactionsList);

        List<Transactions> pulledTransactionsList = sut.viewSingleTransactions(account_id);

        Assert.assertEquals(transactionsList, pulledTransactionsList);
    }

    @Test(expected = UnownedAccountException.class)
    public void test_viewSingleTransactions_throwsUnownedAccountException_givenUnownedAccountId() {
        String account_id = "1";
        UUID customer_id = UUID.randomUUID();
        List<Transactions> emptyList = new LinkedList<>();

        when(mockAccountService.getSessionUser().getSessionUser().getId()).thenReturn(customer_id);
        when(mockTransactionDao.selectTransactionByAccountId(customer_id, account_id)).thenReturn(emptyList);


        List<Transactions> pulledTransactionsList = sut.viewSingleTransactions(account_id);

    }

 */

    @Test(expected = InvalidRequestException.class)
    public void test_viewSingleTransactions_throwsInvalidRequestException_givenNegativeAccountId() {
        String account_id = "-123";
        UUID customer_id = UUID.randomUUID();

        when(mockTransactionDao.selectTransactionByAccountId(customer_id, account_id)).thenReturn(null);

        List<Transactions> transactionsList = sut.viewSingleTransactions(account_id);
    }

    @Test(expected = NumberFormatException.class)
    public void test_viewSingleTransactions_throwsNumberFormatException_givenNonNumericAccountId() {
        String account_id = "asdf";
        UUID customer_id = UUID.randomUUID();

        when(mockTransactionDao.selectTransactionByAccountId(customer_id, account_id)).thenReturn(null);

        List<Transactions> transactionsList = sut.viewSingleTransactions(account_id);
    }

    @Test(expected = NumberFormatException.class)
    public void test_viewSingleTransctions_throwsNumberFormatException_givenDecimalValuedAccountId() {
        String account_id = "10.3";
        UUID customer_id = UUID.randomUUID();

        when(mockTransactionDao.selectTransactionByAccountId(customer_id, account_id)).thenReturn(null);

        List<Transactions> transactionsList = sut.viewSingleTransactions(account_id);
    }

    @Test
    public void test_isNumericInteger_returnsTrue_givenIntegerValue() {
        String integerValue = "123";

        boolean actualResult = sut.isNumericInteger(integerValue);

        Assert.assertTrue("Expected value to be an Integer Value", actualResult);
    }

    @Test
    public void test_isNumericInteger_returnsFalse_givenNonIntegerValue() {
        String decimalValue = "10.3";
        String notNumeric = "adsf";
        String whiteSpace = "";
        String nullValue = null;

        boolean actualResult1 = sut.isNumericInteger(decimalValue);
        boolean actualResult2 = sut.isNumericInteger("asdf");
        boolean actualResult3 = sut.isNumericInteger(whiteSpace);
        boolean actualResult4 = sut.isNumericInteger(nullValue);

        Assert.assertFalse("Expected false from a decimal value",actualResult1);
        Assert.assertFalse("Expected false from a string value",actualResult2);
        Assert.assertFalse("Expected false from a white spaced value",actualResult3);
        Assert.assertFalse("Expected false from a null value",actualResult4);
    }

    @Test
    public void test_isPositiveNumber_returnsTrue_givenPositiveIntegerValue() {
        String positiveInteger = "1";

        boolean actualResult = sut.isPositiveNumber(positiveInteger);

        Assert.assertTrue("Expected true from a positive integer value", actualResult);
    }

    @Test
    public void test_isPositiveNumber_returnsFalse_givenNegativeIntegerNumber() {
        String negativeInteger = "-1";

        boolean actualResult = sut.isPositiveNumber(negativeInteger);

        Assert.assertFalse("Expected false from a negative number", actualResult);
    }


}
