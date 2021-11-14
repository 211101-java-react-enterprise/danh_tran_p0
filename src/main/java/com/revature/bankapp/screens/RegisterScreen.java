package com.revature.bankapp.screens;

import com.revature.bankapp.models.Customer;
import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.util.ScreenRouter;

import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    private final CustomerService customerService;
    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, CustomerService customerService) {
        super("RegisterScreen", "/register", consoleReader, router);
        this.customerService = customerService;
    }

    @Override
    public void render() throws Exception {

        System.out.println("Registration");
        logger.info("Please provide us with information about yourself");

        System.out.print("First Name: ");
        String firstName = consoleReader.readLine();

        System.out.print("Last Name: ");
        String lastName = consoleReader.readLine();

        System.out.print("Email: ");
        String email = consoleReader.readLine();

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        Customer user = new Customer(firstName, lastName, email, username, password);

        if(customerService.registerNewUser(user)) {
            logger.log("Created account: First Name: %s : Last Name: %s : Email: %s : Username: %s", firstName, lastName, email, username);
            router.navigate("/account_creation");
        } else {
            logger.warn("Failed to create user, please try again");
            logger.log("Attempted created account: First Name: %s : Last Name: %s : Email: %s : Username: %s",firstName, lastName, email, username);
        }

    }
}
