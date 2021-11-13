package com.revature.bankapp.services;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.exceptions.NegativeMoneyChargeException;
import com.revature.bankapp.exceptions.UnownedAccountException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;

public class AccountService {

    private Account account;
    private final CustomerService sessionUser;
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao, CustomerService sessionUser) {
        this.accountDao = accountDao;
        this.sessionUser = sessionUser;
    }

    public void withdrawMoney(Account account, double value) {
        if(value < 0) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        accountDao.removeMoney(account, value);
    }

    public void depositMoney(Account account, double value) {
        if(value < 0) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        accountDao.addMoney(account, value);
    }

    public CustomerService getSessionUser() {
        return sessionUser;
    }

    public List<Account> returnMyAccounts() {
        return accountDao.findAllAccounts(sessionUser.getSessionUser());
    }

    public void getAccountById(String id) {

        Account account = accountDao.findById(id);
        if(account == null) {
            throw new UnownedAccountException("User does not own this account");
        }
        sessionUser.getSessionUser().setCurrentAccount(account);
    }

}
