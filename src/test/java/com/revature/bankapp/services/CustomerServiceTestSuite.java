package com.revature.bankapp.services;

import com.revature.bankapp.daos.CustomerDao;
import com.revature.bankapp.models.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CustomerServiceTestSuite {

    CustomerService sut;
    CustomerDao mockCustomerDao;

    @Before
    public void testCaseSetup() {
        mockCustomerDao = mock(CustomerDao.class);
        sut = new CustomerService(mockCustomerDao);
    }

    @Test
    public void test_isEmailValid_returnsTrue_givenValidEmail() {
        Customer customer = new Customer("Valid", "Name", "validemail@yahoo.com", "ValidUsername", "ValidPassword");

        boolean actualResult = sut.isEmailValid(customer);

        Assert.assertTrue("expected email to be considered valid", actualResult);
    }

    @Test
    public void test_isEmailValid_returnsFalse_givenInvalidEmail() {
        Customer customer = new Customer("valid", "valid", "notValidEmail", "valid", "valid");

        boolean actualResult = sut.isEmailValid(customer);

        Assert.assertFalse("expected email to be considered invalid", actualResult);
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {
        Customer customer = new Customer("valid", "valid" ,"valid" ,"valid" ,"valid");

        boolean actualResult = sut.isUserValid(customer);

        Assert.assertTrue("expected user to be considered valid", actualResult);
    }

    @Test
    public void test_isUserValid_returnsFalse_givenInvalidUser() {
        Customer nullCustomer = new Customer(null, "valid", "valid", "valid", "valid");
        Customer emptyCustomer = new Customer("", "valid", "valid", "valid", "valid");
        Customer untrimmedCustomer = new Customer("     ", "valid", "valid", "valid", "valid");

        boolean actualResult1 = sut.isUserValid(nullCustomer);
        boolean actualResult2 = sut.isUserValid(emptyCustomer);
        boolean actualResult3 = sut.isUserValid(untrimmedCustomer);

        Assert.assertFalse("expected user to be considered false", actualResult1);
        Assert.assertFalse("expected user to be considered false", actualResult2);
        Assert.assertFalse("expected user to be considered false", actualResult3);
    }

    @Test
    public void test_isEmailTaken_returnsTrue_givenUsedEmail() {

        Customer validUserUsedEmail = new Customer("valid", "valid", "danhtran1337@gmail.com", "valid", "valid");
        when(mockCustomerDao.findUserByEmail(validUserUsedEmail.getEmail())).thenReturn(validUserUsedEmail);

        boolean actualResult = sut.isEmailTaken("danhtran1337@gmail.com");

        Assert.assertTrue(actualResult);
    }

    @Test
    public void test_isEmailTaken_returnsFalse_givenUnusedEmail() {

        boolean actualResult = sut.isEmailTaken("EMAILISNOTTAKEN@NOTTAKEN.NET");

        Assert.assertFalse(actualResult);
    }

    @Test
    public void test_isUsernameTaken_returnsTrue_givenUsedUsername() {

        String username = "danhtran123";
        when(mockCustomerDao.findUserByUsername(username)).thenReturn(new Customer());

        boolean actualResult = sut.isUsernameTaken(username);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void test_isUsernameTaken_returnsFalse_givenUnusedUsername() {

        boolean actualResult = sut.isUsernameTaken("USERNAMEWILLNEVERBETAKEN");

        Assert.assertFalse(actualResult);
    }

    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {
        Customer customer = new Customer("valid", "valid", "valid@valid.valid", "valid", "valid");

        when(mockCustomerDao.save(customer)).thenReturn(customer);

        boolean actualResult = sut.registerNewUser(customer);

        Assert.assertTrue("Expected user to be created", actualResult);
    }

}
