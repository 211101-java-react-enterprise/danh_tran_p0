package com.revature.bankapp.screens;

import com.revature.bankapp.models.Customer;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    //Probably need to pass in the account class into the constructor
    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("Welcome to BankingApp\n" +
                            "1) Withdraw\n" +
                            "2) Deposit\n" +
                            "3) Logout");

        String userSelection = consoleReader.readLine();

        switch(userSelection) {
            case("1"):
                router.navigate("/withdraw");
                break;
            case("2"):
                router.navigate("/deposit");
                break;
            case("3"):
                router.navigate("/welcome");
                break;
            default:
                System.out.println("User entered an invalid option");
        }
    }

}
