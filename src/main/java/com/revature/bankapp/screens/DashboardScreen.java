package com.revature.bankapp.screens;

import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    CustomerService sessionUser;
    //Probably need to pass in the account class into the constructor
    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, CustomerService sessionUser) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.sessionUser = sessionUser;
    }

    @Override
    public void render() throws Exception {
        while (sessionUser.isSessionActive()) {
            System.out.println("Welcome to BankingApp " + sessionUser.getSessionUser().getFirstName() + ". This account currently has $" + String.format("%.2f",sessionUser.getSessionUser().getCurrentAccount().getMoney()));
            String menu = "1) Withdraw\n" +
                          "2) Deposit\n" +
                          "3) Accounts\n" +
                          "4) Display Transactions History\n" +
                          "5) Logout";
            System.out.println(menu);


            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case ("1"):
                    logger.log("Navigating to withdraw screen...");
                    router.navigate("/withdraw");
                    break;
                case ("2"):
                    logger.log("Navigating to deposit screen...");
                    router.navigate("/deposit");
                    break;
                case ("3"):
                    logger.log("Navigating to accounts screen...");
                    router.navigate("/accounts");
                    break;
                case("4"):
                    logger.log("Navigating to transactions screen...");
                    router.navigate("/transaction_history");
                    break;
                case ("5"):
                    sessionUser.logout();
                    logger.info("Logging out");
                    break;
                default:
                    logger.warn("User entered an invalid option");
            }
        }
    }

}
