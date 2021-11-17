package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.EmptyTransactionsException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.exceptions.UnownedAccountException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.Transactions;
import com.revature.bankapp.services.TransactionService;
import com.revature.bankapp.util.ScreenRouter;
import com.revature.bankapp.util.collections.List;

import java.io.BufferedReader;

public class TransactionHistoryScreen extends Screen {

    TransactionService transactionService;
    public TransactionHistoryScreen(BufferedReader consoleReader, ScreenRouter router, TransactionService transactionServiceService) {
        super("TransactionHistory", "/transaction_history", consoleReader, router);
        this.transactionService = transactionServiceService;
    }

    @Override
    public void render() throws Exception {
        List<Account> accountList = transactionService.getCurrentAccount().returnMyAccounts();
        System.out.println("Which account transactions would you like to view?");
        logger.warnUser("Select 0 for all accounts");
        Account account;
            for (int i = 0; i < accountList.size(); i++) {
                account = accountList.get(i);
                String type = account.getType().substring(0, 1).toUpperCase() + account.getType().substring(1);
                System.out.printf("%s Account id is %s\n", type, account.getId());
                System.out.println("Current deposited money in this account is: $" + String.format("%.2f", account.getMoney()));
                System.out.println();
            }

            List<Transactions> transactionsList;
            String userSelection = consoleReader.readLine();
            try {
                if (userSelection.equals("0")) {
                    transactionsList = transactionService.viewAllTransactions(transactionService.getCurrentAccount().getSessionUser().getSessionUser().getId().toString());
                } else {
                    transactionsList = transactionService.viewSingleTransactions(userSelection);
                }
                for(int i = 0; i < transactionsList.size(); i++) {
                    System.out.printf("%s: Account %d: %s\n", transactionsList.get(i).getDate(), transactionsList.get(i).getAccount_id(), transactionsList.get(i).getDescription());
                }
            } catch (UnownedAccountException | EmptyTransactionsException | InvalidRequestException e) {
                logger.warn(e.getMessage());
            }
            logger.logPrint("Returning to dashboard...");
    }
}
