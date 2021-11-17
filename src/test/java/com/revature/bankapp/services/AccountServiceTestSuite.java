package com.revature.bankapp.services;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.exceptions.IncorrectFormatException;
import com.revature.bankapp.exceptions.NegativeMoneyChargeException;
import com.revature.bankapp.exceptions.OverChargeException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.models.SavingsAccount;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountServiceTestSuite {

    AccountService sut;
    AccountDao mockAccountDao;
    CustomerService mockCustomerService;

    @Before
    public void testCaseSetup() {
        mockAccountDao = mock(AccountDao.class);
        mockCustomerService = mock(CustomerService.class);
        sut = new AccountService(mockAccountDao, mockCustomerService);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isMoneyValid_returnsTrue_givenValidMoney() {
        String money1 = "123";
        String money2 = "123.45";

        boolean expectedResult1 = sut.isNumeric(money1);
        boolean expectedResult2 = sut.isNumeric(money1);

        Assert.assertTrue("Expected money to be valid (whole number)", expectedResult1);
        Assert.assertTrue("Expected money to be valid (decimal value)", expectedResult2);
    }

    @Test
    public void test_isMoneyValid_returnsFalse_givenInvalidMoney() {
        String money1 = "asdf";
        String money2 = "";
        String money3 = null;

        boolean actualResult1 = sut.isNumeric(money1);
        boolean actualResult2 = sut.isNumeric(money2);
        boolean actualResult3 = sut.isNumeric(money3);

        Assert.assertFalse("Expected money to be considered invalid (text value)", actualResult1);
        Assert.assertFalse("Expected money to be considered invalid (empty space)", actualResult2);
        Assert.assertFalse("Expected money to be considered invalid (null)", actualResult3);
    }

    @Test
    public void test_isMoneyPositive_returnsTrue_givenPositiveMoney() {
        String money = "5";

        boolean actualResult = sut.isPositiveNumber(money);

        Assert.assertTrue("Expected money to be positive value", actualResult);
    }

    @Test
    public void test_isMoneyPositive_returnsFalse_GivenNegativeMoney() {
        String money = "-5";

        boolean actualResult = sut.isPositiveNumber(money);

        Assert.assertFalse("Expected money to be negative value", actualResult);
    }

    @Test
    public void test_isProperFormat_returnsTrue_givenProperlyFormattedMoney() {
        String money1 = "1.00";
        String money2 = "2.0";
        String money3 = "3";

        boolean actualResult1 = sut.isProperFormat(money1);
        boolean actualResult2 = sut.isProperFormat(money2);
        boolean actualResult3 = sut.isProperFormat(money3);

        Assert.assertTrue("Expected money to have two decimal places", actualResult1);
        Assert.assertTrue("Expected money to have one decimal places", actualResult2);
        Assert.assertTrue("Expected money to be a whole number", actualResult3);
    }

    @Test
    public void test_isProperFormat_returnsFalse_givenImproperlyFormattedMoney() {
        String money1 = "1.234";

        boolean actualResult1 = sut.isProperFormat(money1);

        Assert.assertFalse("Expected money to have more than 2 decimal places", actualResult1);
    }

    @Test
    public void test_createNewAccount_returnsTrue_givenAccountIsCreated() {
        Account savingsAccount = new SavingsAccount();

        when(mockCustomerService.getSessionUser()).thenReturn(new Customer());
        when(mockAccountDao.save(savingsAccount)).thenReturn(savingsAccount);

        boolean actualResult1 = sut.createNewAccount(savingsAccount);

        Assert.assertTrue("Expected creation of savings account", actualResult1);
    }



    @Test
    public void test_depositMoney_returnsTrue_givenAccountAndMoney() {

        Account account = new CheckingsAccount();
        String money = "10";

        when(mockAccountDao.update(account)).thenReturn(true);

        boolean actualResult = sut.depositMoney(account, money);

        Assert.assertTrue("expected user to deposit money", actualResult);
    }

    @Test(expected = IncorrectFormatException.class)
    public void test_depositMoney_throwsIncorrectFormatException_givenBigDecimalMoney() {
        Account account = new CheckingsAccount();
        String money = "10.2342";

        boolean actualResult = sut.depositMoney(account, money);

    }

    @Test(expected = NegativeMoneyChargeException.class)
    public void test_depositMoney_throwsNegativeMoneyCharge_givenNegativeValue() {
        Account account = new CheckingsAccount();
        String money = "-10.23";

        boolean actualResult = sut.depositMoney(account, money);

    }

    @Test
    public void test_withdrawMoney_returnsTrue_givenAccountWithMoneyAndPositiveMoney() {
        Account account = new CheckingsAccount();
        account.setMoney(20);
        String money = "15";

        when(mockAccountDao.update(account)).thenReturn(true);

        boolean actualResult = sut.withdrawMoney(account,money);

        Assert.assertTrue("Expected to withdraw money given a positive money value and account", actualResult);
    }

    @Test(expected = OverChargeException.class)
    public void test_withdrawMoney_throwsOverChargeException_givenAccountWithLessMoneyThanMoneyToWithdraw() {
        Account account = new CheckingsAccount();
        account.setMoney(10);
        String money = "15";

        when(mockAccountDao.update(account)).thenReturn(true);
        boolean actualResult = sut.withdrawMoney(account,money);
    }

    @Test(expected = NegativeMoneyChargeException.class)
    public void test_withdrawMoney_throwsNegativeMoneyCharge_givenNegativeValue() {
        Account account = new CheckingsAccount();
        String money = "-10.23";

        boolean actualResult = sut.withdrawMoney(account, money);

    }

    @Test(expected = IncorrectFormatException.class)
    public void test_withdrawMoney_throwsIncorrectFormatException_givenBigDecimalMoney() {
        Account account = new CheckingsAccount();
        String money = "10.2342";

        boolean actualResult = sut.withdrawMoney(account, money);

    }

    @Test
    public void test_changeToAccount_returnsTrue_givenGoodIds() {
        UUID customer_id = UUID.randomUUID();
        String account_id = "1";
        Account account = new CheckingsAccount();

        when(mockAccountDao.findAccountByCustomerAndAccountId(customer_id, account_id)).thenReturn(account);
        when(mockCustomerService.getSessionUser()).thenReturn(new Customer());

        boolean actualResult = sut.changeToAccount(customer_id, account_id);

        Assert.assertTrue("Expected to change account", actualResult);
    }

}
