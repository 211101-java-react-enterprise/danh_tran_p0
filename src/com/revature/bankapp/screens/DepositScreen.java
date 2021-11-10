package com.revature.bankapp.screens;

import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class DepositScreen extends Screen {


    public DepositScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DepositScreen", "/deposit", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        System.out.println("How much do you want to deposit?");
        double depositMoney = Double.parseDouble(consoleReader.readLine());

    }
}
