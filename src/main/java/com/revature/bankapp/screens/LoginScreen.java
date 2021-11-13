package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.AuthenticationException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class LoginScreen extends Screen {
    private final CustomerService customerService;
    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, CustomerService customerService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.customerService = customerService;
    }

    @Override
    public void render() throws Exception {

        System.out.println("Welcome to the login screen");
        System.out.print("Username: ");
        String username = consoleReader.readLine();
        System.out.print("Password: ");
        String password = consoleReader.readLine();

        try {
            customerService.authenticateLogin(username, password);
            router.navigate("/choose_account");
        } catch(InvalidRequestException | AuthenticationException e) {
            System.out.println(e.getMessage());;
        }

    }
}
