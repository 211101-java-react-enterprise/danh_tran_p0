package com.revature.bankapp.screens;

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
                logger.log("Navigating to login screen");
                router.navigate("/login");
                break;
            case("2"):
                logger.log("Navigating to registration screen");
                router.navigate("/register");
                break;
            case("3"):
                logger.logPrint("Exiting application...");
                shutdown();
                break;
            default:
                logger.warn("User made an invalid selection");
        }
    }

}
