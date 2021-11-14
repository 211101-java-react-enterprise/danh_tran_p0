package com.revature.bankapp.screens;

import com.revature.bankapp.services.AccountService;
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
                      "2) Display accounts\n" +
                      "3) Back";
        System.out.println(menu);


        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case ("1"):
                router.navigate("/create_account");
                break;
            case ("2"):
                router.navigate("/choose_account");
                break;
            case ("3"):
                router.navigate("/dashboard");
                //router.navigate("/welcome"); not used because it would keep things in the stack trace
                break;
            default:
                System.out.println("User entered an invalid option");
        }
    }
}
