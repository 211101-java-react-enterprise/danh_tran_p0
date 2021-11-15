package com.revature.bankapp.screens;

import com.revature.bankapp.services.AccountService;
import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

//We need to ensure that the user gets sent to this screen if the Customer fails to own an account
public class AccountCreationScreen extends Screen {

    AccountService sessionUser;
    public AccountCreationScreen(BufferedReader consoleReader, ScreenRouter router, AccountService sessionUser) {
        super("AccountCreationScreen", "/create_account", consoleReader, router);
        this.sessionUser = sessionUser;
    }

    @Override
    public void render() throws Exception {
        System.out.println("What type of account do you wish to create?\n" +
                           "1) Savings\n" +
                           "2) Checkings");
        String userSelection = consoleReader.readLine();

        switch(userSelection) {
            case("1"):
                if(sessionUser.createNewAccount("savings")) {
                    logger.log("Successfully created a savings account");
                    router.navigate("/choose_account");
                } else {
                    logger.warn("Failed to make a savings account, please try again");
                }
                break;
            case("2"):
                if(sessionUser.createNewAccount("checkings")) {
                    logger.log("Successfully created a checkings account");
                    router.navigate("/choose_account");
                } else {
                    logger.warn("Failed to make a checkings account, please try again");
                }
                break;
            default:
                logger.warn("User made an invalid selection");
                logger.warn("Returning to the dashboard...");
        }
    }
}
