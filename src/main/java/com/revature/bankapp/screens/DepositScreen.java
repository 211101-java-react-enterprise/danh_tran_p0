package com.revature.bankapp.screens;

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
        double moneyToDeposit = Double.parseDouble(consoleReader.readLine());
        Account account = new CheckingsAccount(accountService.getSessionUser().getSessionUser());
        try {
            accountService.depositMoney(account, moneyToDeposit);
            System.out.println("Money successfully deposited");

        } catch (NegativeMoneyChargeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Returning to dashboard");
    }
}
