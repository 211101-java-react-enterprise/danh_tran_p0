package com.revature.bankapp.screens;

import com.revature.bankapp.util.AppState;
import com.revature.bankapp.util.ScreenRouter;
import static com.revature.bankapp.util.AppState.shutdown;

import java.io.BufferedReader;

public class WelcomeScreen extends Screen {
    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("Welcome to BankingApp!\n" +
                           "1) Login\n" +
                           "2) Register\n" +
                           "3) Exit");

        String userSelection = consoleReader.readLine();
        switch(userSelection) {
            case("1"):
                router.navigate("/login");
                break;
            case("2"):
                router.navigate("/register");
                break;
            case("3"):
                System.out.println("Exiting application...");
                shutdown();
                break;
            default:
                System.out.println("User made an invalid selection");
        }
    }

}
