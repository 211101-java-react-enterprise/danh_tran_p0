package com.revature.bankapp.screens;

import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

//We need to ensure that the user gets sent to this screen if the Customer fails to own an account
public class AccountCreationScreen extends Screen {

    CustomerService sessionUser;
    public AccountCreationScreen(String name, String route, BufferedReader consoleReader, ScreenRouter router, CustomerService sessionUser) {
        super("AccountCreationScreen", "/create_account", consoleReader, router);
        this.sessionUser = sessionUser;
    }

    @Override
    public void render() throws Exception {
        System.out.println("What type of account do you wish to create?" +
                           "1) Savings" +
                           "2) Checkings");
        String userSelection = consoleReader.readLine();

        switch(userSelection) {
            case("1"):
                break;
            case("2"):
                break;
            default:
                System.out.println("User made an invalid selection");
        }
    }
}
