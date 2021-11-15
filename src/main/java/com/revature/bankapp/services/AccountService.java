package com.revature.bankapp.services;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.exceptions.*;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.models.SavingsAccount;
import com.revature.bankapp.models.Transactions;
import com.revature.bankapp.util.collections.List;

import java.util.UUID;

public class AccountService {

    private Account account;
    private final CustomerService sessionUser;
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao, CustomerService sessionUser) {
        this.accountDao = accountDao;
        this.sessionUser = sessionUser;
    }

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
        return accountDao.update(account);
    }

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
        return accountDao.update(account);
    }

    public CustomerService getSessionUser() {
        return sessionUser;
    }

    public List<Account> returnMyAccounts() {
        return accountDao.findAccountsByCustomerId(sessionUser.getSessionUser());
    }

    public boolean changeToAccount(UUID customer_id, String account_id) {

        if(Integer.parseInt(account_id) < 0) {
            throw new NegativeAccountIdException("Account IDs can't be negative");
        }
        account = accountDao.findAccountByCustomerAndAccountId(customer_id, account_id);
        if(account == null) {
            throw new UnownedAccountException("User does not own this account");
        }
        sessionUser.getSessionUser().setCurrentAccount(account);
        return true;
    }

    public boolean isPositiveNumber(String value) {
        double money = Double.parseDouble(value);
        if(money < 0) {
            return false;
        }
        return true;
    }

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

    public List<Transactions> viewAllTransactions(String customer_id) {
        List<Transactions> list = accountDao.viewAllAccountsTransactions(customer_id);
        if(list.isEmpty()) {
            throw new EmptyTransactionsException("There are no previously existing transactions");
        }
        System.out.println("Not empty");
        return list;
    }
    public List<Transactions> viewSingleTransactions(String account_id) {
        if(!isNumeric(account_id) || !isInteger(account_id)) {
            throw new InvalidRequestException("This is not an account_id");
        }
        if(!isPositiveNumber(account_id)) {
            throw new InvalidRequestException("An account number cannot be negative");
        }
        List<Transactions> list = accountDao.selectTransactionByAccountId(account_id);
        // TODO figure out how to get this to be null if possible
        if(list == null) {
            throw new UnownedAccountException("You do not own this account");
        }
        if(list.isEmpty()) {
            throw new UnownedAccountException("There are no previously existing transactions");
        }
        return list;
    }

    public boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
