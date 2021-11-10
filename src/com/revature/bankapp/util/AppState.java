package com.revature.bankapp.util;

import com.revature.bankapp.screens.*;
import com.revature.bankapp.services.CustomerService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    public final ScreenRouter router;
    public static boolean appRunning;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader,router, new CustomerService()));
        router.addScreen(new LoginScreen(consoleReader, router, new CustomerService()));
        router.addScreen(new DashboardScreen(consoleReader, router));
        router.addScreen(new WithdrawScreen(consoleReader, router));
        router.addScreen(new DepositScreen(consoleReader, router));
    }

    public void startup() {
        try {
            while (appRunning) {
                router.navigate("/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}
