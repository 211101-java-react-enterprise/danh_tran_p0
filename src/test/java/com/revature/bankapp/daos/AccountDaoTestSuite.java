package com.revature.bankapp.daos;

import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.util.collections.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountDaoTestSuite {

    AccountDao sut;

    @Before
    public void testCaseSetup() {
        sut = new AccountDao();
    }

    @Test
    public void test_findAccountsByCustomerId_returnsListOfAccounts_givenGoodCustomerId() {
        List<Account> expectedResult;

       // List<Account> actualResult = sut.findAccountsByCustomerId();
    }
}
