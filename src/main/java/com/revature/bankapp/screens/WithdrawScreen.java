package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.IncorrectFormatException;
import com.revature.bankapp.exceptions.NegativeMoneyChargeException;
import com.revature.bankapp.exceptions.OverChargeException;
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
        String moneyToWithdraw = consoleReader.readLine();
        try {
            accountService.withdrawMoney(accountService.getSessionUser().getSessionUser().getCurrentAccount(), moneyToWithdraw);
            logger.info("Money successfully withdrew!");
        } catch (NegativeMoneyChargeException | OverChargeException  | IncorrectFormatException e) {
            logger.warn(e.getMessage());
        } catch (NumberFormatException e) {
            logger.warn("You cannot input a non-numeric value");
        }
        logger.logPrint("Returning to dashboard...");
    }
}
