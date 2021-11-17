package com.revature.bankapp.services;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.exceptions.*;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.util.collections.List;

import java.util.UUID;

public class AccountService {

    private Account currentAccount;
    private final CustomerService sessionUser;
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao, CustomerService sessionUser) {
        this.accountDao = accountDao;
        this.sessionUser = sessionUser;
    }

    //withdraws money from the current account
    public boolean withdrawMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new IncorrectFormatException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isNumeric(value)) {
            throw new IncorrectFormatException("Please enter a positive numeric value");
        }
        if(!isPositiveNumber(value)) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        double moneyToWithdraw = Double.parseDouble(value);
        if(moneyToWithdraw > account.getMoney()) {
            throw new OverChargeException("You are attempting to withdraw more than you have");
        }
        account.setMoney(account.getMoney() - moneyToWithdraw);

        String description = String.format("Withdrew $%.2f", moneyToWithdraw);
        accountDao.addTransactionRecord(account, description);
        return accountDao.update(account);
    }

    //deposits money into the current account
    public boolean depositMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new IncorrectFormatException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isNumeric(value)) {
            throw new IncorrectFormatException("Please enter a positive numeric value");
        }
        if(!isPositiveNumber(value)) {
            throw new NegativeMoneyChargeException("User is not allowed to enter a negative number");
        }
        double moneyToDeposit = Double.parseDouble(value);

        account.setMoney(account.getMoney() + moneyToDeposit);
        String description = String.format("Deposited $%.2f", moneyToDeposit);
        accountDao.addTransactionRecord(account, description);
        return accountDao.update(account);
    }

    public CustomerService getSessionUser() {
        return sessionUser;
    }

    //get a list of all accounts that the current user owns
    public List<Account> returnMyAccounts() {
        return accountDao.findAccountsByCustomerId(sessionUser.getSessionUser().getId().toString());
    }

    //Takes current customer's id and the account id user selected to change to that account if owned
    public boolean changeToAccount(UUID customer_id, String account_id) {

        if(Integer.parseInt(account_id) < 0) {
            throw new NegativeAccountIdException("Account IDs can't be negative");
        }
        currentAccount = accountDao.findAccountByCustomerAndAccountId(customer_id, account_id);
        if(currentAccount == null) {
            throw new UnownedAccountException("User does not own this account");
        }
        sessionUser.getSessionUser().setCurrentAccount(currentAccount);
        return true;
    }

    //user validation to check if user entered a positive money value
    public boolean isPositiveNumber(String value) {
        double money = Double.parseDouble(value);
        if(money < 0) {
            return false;
        }
        return true;
    }

    //user validation to check if user entered a number
    public boolean isNumeric(String value) {
        if(value == null || value.equals("")) {
            return false;
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    //user validation to check if it is a decimal value and if it has only two decimal places $1.00
    public boolean isProperFormat(String value) {
        //checks to see if it is a whole number
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

    public boolean createNewAccount(Account account) {

        account.setCustomer(sessionUser.getSessionUser());

        return accountDao.save(account) != null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

}
