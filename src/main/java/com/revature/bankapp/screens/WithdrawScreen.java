package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.NegativeMoneyChargeException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.services.AccountService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class WithdrawScreen extends Screen {

    AccountService accountService;
    public WithdrawScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService) {
        super("WithdrawScreen", "/withdraw", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {

        System.out.println("How much do you want to withdraw?");
        double moneyToWithdraw = Double.parseDouble(consoleReader.readLine());
        Account account = new CheckingsAccount(accountService.getSessionUser().getSessionUser());
        try {
            accountService.withdrawMoney(account, moneyToWithdraw);
            System.out.println("Money successfully withdrew");
        } catch (NegativeMoneyChargeException e) {
            System.out.println(e.getMessage());
        }
    }
}
