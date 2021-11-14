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
                          "4) Display Transactions\n" +
                          "5) Logout";
            System.out.println(menu);


            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case ("1"):
                    router.navigate("/withdraw");
                    break;
                case ("2"):
                    router.navigate("/deposit");
                    break;
                case ("3"):
                    router.navigate("/accounts");
                    break;
                case("4"):
                    router.navigate("/transactions");
                    break;
                case ("5"):
                    sessionUser.logout();
                    //router.navigate("/welcome"); not used because it would keep things in the stack trace
                    break;
                default:
                    System.out.println("User entered an invalid option");
            }
        }
    }

}
