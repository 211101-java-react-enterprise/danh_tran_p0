package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.IncorrectFormatException;
import com.revature.bankapp.exceptions.NegativeMoneyChargeException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.services.AccountService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class DepositScreen extends Screen {

    AccountService accountService;


    public DepositScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService) {
        super("DepositScreen", "/deposit", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {

        System.out.println("How much do you want to deposit?");
        String moneyToDeposit = consoleReader.readLine();
        try {
            accountService.depositMoney(accountService.getSessionUser().getSessionUser().getCurrentAccount(), moneyToDeposit);
            System.out.println("Money successfully deposited!");
        } catch (NegativeMoneyChargeException | IncorrectFormatException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Returning to dashboard...");
    }
}
