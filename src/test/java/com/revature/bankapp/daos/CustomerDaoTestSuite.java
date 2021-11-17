package com.revature.bankapp.daos;

import com.revature.bankapp.models.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class CustomerDaoTestSuite {

    CustomerDao sut;

    @Before
    public void testCaseSetup() {
        sut = new CustomerDao();
    }

    /*
    @Test
    public void test_findUserByEmail_returnsCustomerInfo_givenEmailInDatabase() {
        Customer expectedResult = new Customer("Danh", "Tran", "danhtran1337@gmail.com", "danhtran123", "password");
        expectedResult.setId(UUID.fromString("8c1104b0-8f18-465e-9db2-d13b9a0946d4"));

        Customer actualResult = sut.findUserByEmail("danhtran1337@gmail.com");

        Assert.assertEquals(expectedResult, actualResult);
    }

     */

    @Test
    public void test_findUserByEmail_returnsNull_givenBadEmail() {
        Customer actualResult = sut.findUserByEmail("DEFINITELYANUNUSEDEMAIL@revvy.net");

        Assert.assertNull(actualResult);
    }
/*
    @Test
    public void test_findUserByUsernameAndPassword_returnsCustomerInfo_givenGoodUsernameAndPassword() {
        Customer expectedResult = new Customer("Danh", "Tran", "danhtran1337@gmail.com", "danhtran123", "password");
        expectedResult.setId(UUID.fromString("8c1104b0-8f18-465e-9db2-d13b9a0946d4"));

        Customer actualResult = sut.findUserByUsernameAndPassword("danhtran123", "password");

        Assert.assertEquals(expectedResult, actualResult);
    }

 */

    @Test
    public void test_findUserByUsernameAndPassword_returnsNull_givenBadUsernameAndOrPassword() {
        Customer goodUsernameResult = new Customer("valid", "valid", "valid", "danhtran123", "BADPASSWORDDD");
        Customer goodPasswordResult = new Customer("valid", "valid", "valid", "BADUSERNAMEEED", "password");
        Customer badUsernameAndPassword = new Customer("valid", "valid", "valid", "BADUSERNAMEEED", "BADPASSWORDDD");

        Customer actualResult1 = sut.findUserByUsernameAndPassword(goodUsernameResult.getUsername(), goodUsernameResult.getPassword());
        Customer actualResult2 = sut.findUserByUsernameAndPassword(goodPasswordResult.getUsername(), goodPasswordResult.getPassword());
        Customer actualResult3 = sut.findUserByUsernameAndPassword(badUsernameAndPassword.getUsername(), badUsernameAndPassword.getPassword());

        Assert.assertNull(actualResult1);
        Assert.assertNull(actualResult2);
        Assert.assertNull(actualResult3);
    }

}
