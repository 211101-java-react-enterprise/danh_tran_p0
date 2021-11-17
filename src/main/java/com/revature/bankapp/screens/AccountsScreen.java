package com.revature.bankapp.screens;

import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class AccountsScreen extends Screen {
    public AccountsScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("AccountsScreen", "/accounts", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("What would you like to do to your accounts?");
        String menu = "1) Create a new account\n" +
                      "2) Display and choose accounts\n" +
                      "3) Back";
        System.out.println(menu);


        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case ("1"):
                logger.log("Navigating to account creation screen...");
                router.navigate("/create_account");
                break;
            case ("2"):
                logger.log("Navigating to account choosing screen...");
                router.navigate("/choose_account");
                break;
            case ("3"):
                logger.log("Navigating to dashboard screen...");
                router.navigate("/dashboard");
                break;
            default:
                logger.warn("User entered an invalid option");
        }
    }
}
