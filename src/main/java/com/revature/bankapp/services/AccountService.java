package com.revature.bankapp.services;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.exceptions.*;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.models.SavingsAccount;
import com.revature.bankapp.util.collections.List;
import com.revature.bankapp.util.logging.Logger;

import java.util.UUID;

public class AccountService {

    private Account account;
    private final CustomerService sessionUser;
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao, CustomerService sessionUser) {
        this.accountDao = accountDao;
        this.sessionUser = sessionUser;
    }

    public void withdrawMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new IncorrectFormatException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isMoneyValid(value)) {
            throw new IncorrectFormatException("Please enter a positive numeric value");
        }
        if(!isMoneyPositive(value)) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        double moneyToWithdraw = Double.parseDouble(value);
        if(moneyToWithdraw > account.getMoney()) {
            throw new OverChargeException("You are attempting to withdraw more than you have");
        }
        account.setMoney(account.getMoney() - moneyToWithdraw);
        accountDao.update(account);
    }

    public void depositMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new IncorrectFormatException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isMoneyValid(value)) {
            throw new IncorrectFormatException("Please enter a positive numeric value");
        }

        if(!isMoneyPositive(value)) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        double moneyToDeposit = Double.parseDouble(value);

        account.setMoney(account.getMoney() + moneyToDeposit);
        accountDao.update(account);
    }

    public CustomerService getSessionUser() {
        return sessionUser;
    }

    public List<Account> returnMyAccounts() {
        return accountDao.findAccountsByCustomerId(sessionUser.getSessionUser());
    }

    public void changeToAccount(UUID customer_id, String account_id) {

        if(Integer.parseInt(account_id) < 0) {
            throw new NegativeAccountIdException("Account IDs can't be negative");
        }
        account = accountDao.findAccountByCustomerAndAccountId(customer_id, account_id);
        if(account == null) {
            throw new UnownedAccountException("User does not own this account");
        }
        sessionUser.getSessionUser().setCurrentAccount(account);
    }

    public boolean isMoneyPositive(String value) {
        double money = Double.parseDouble(value);
        if(money < 0) {
            return false;
        }
        return true;
    }

    public boolean isMoneyValid(String value) {
        if(value == null || value.equals("")) {
            return false;
        }
        return true;
    }

    public boolean isProperFormat(String value) {
        if(Double.parseDouble(value) % 1 == 0) {
            return true;
        }
        int integerLength = value.indexOf(".");
        int decimalLength = value.length() - 1 - integerLength;
        if(decimalLength > 2) {
            return false;
        }
        return true;
    }

    public boolean createNewAccount(String type) {
        Account account = null;
        if(type.equals("savings")) {
            account = new SavingsAccount();
        } else if(type.equals("checkings")) {
            account = new CheckingsAccount();
        }
        account.setType(type);
        account.setCustomer(sessionUser.getSessionUser());

        return accountDao.save(account) != null;
    }

}
