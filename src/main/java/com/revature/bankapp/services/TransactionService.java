package com.revature.bankapp.services;

import com.revature.bankapp.daos.TransactionDao;
import com.revature.bankapp.exceptions.EmptyTransactionsException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.exceptions.UnownedAccountException;
import com.revature.bankapp.models.Transactions;
import com.revature.bankapp.util.collections.List;

import java.util.UUID;

public class TransactionService {

    TransactionDao transactionDao;
    AccountService accountService;
    public TransactionService(TransactionDao transactionDao, AccountService accountService) {
        this.transactionDao = transactionDao;
        this.accountService = accountService;
    }

    //View all transaction history the customer owns from all accounts
    public List<Transactions> viewAllTransactions(String customer_id) {
        List<Transactions> list = transactionDao.viewAllAccountsTransactions(customer_id);
        if(list.isEmpty()) {
            throw new EmptyTransactionsException("There are no previously existing transactions");
        }
        return list;
    }
    //View a single account's transaction history
    public List<Transactions> viewSingleTransactions(String account_id) {
        if(!isPositiveNumber(account_id)) {
        throw new InvalidRequestException("An account number cannot be negative");
        }
        if(!isNumericInteger(account_id)) {
            throw new InvalidRequestException("This is not an account_id");
        }
        UUID current_id = accountService.getSessionUser().getSessionUser().getId();
        List<Transactions> list = transactionDao.selectTransactionByAccountId(current_id, account_id);
        if(list == null) {
            throw new UnownedAccountException("You do not own this account or there are no previously existing transactions");
        }
        if(list.isEmpty()) {
            throw new UnownedAccountException("There are no previously existing transactions");
        }
        return list;
    }

    public AccountService getCurrentAccount() {
        return accountService;
    }

    //user validation for checking if account number entered is positive value
    public boolean isPositiveNumber(String account_id) {
        return Integer.parseInt(account_id) > 0;
    }
    //user validation for checking if account number entered is a number
    public boolean isNumericInteger(String value) {
        if(value == null || value.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


}
