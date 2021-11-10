package com.revature.bankapp.screens;

import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class WithdrawScreen extends Screen {

    public WithdrawScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("WithdrawScreen", "/withdraw", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        System.out.println("How much do you want to withdraw?");
        double moneyToWithdraw = Double.parseDouble(consoleReader.readLine());
    }
}
