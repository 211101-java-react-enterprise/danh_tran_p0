package com.revature.bankapp.util;

import com.revature.bankapp.daos.AccountDao;
import com.revature.bankapp.daos.CustomerDao;
import com.revature.bankapp.daos.TransactionDao;
import com.revature.bankapp.screens.*;
import com.revature.bankapp.services.AccountService;
import com.revature.bankapp.services.CustomerService;
import com.revature.bankapp.services.TransactionService;
import com.revature.bankapp.util.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private final Logger logger;
    public final ScreenRouter router;
    public static boolean appRunning;

    public AppState() {
        logger = Logger.getLogger(true);
        logger.log("Initializing Application...");
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        CustomerDao customerDao = new CustomerDao();
        CustomerService customerService = new CustomerService(customerDao);

        AccountDao accountDao = new AccountDao();
        TransactionDao transactionDao = new TransactionDao();
        AccountService accountService = new AccountService(accountDao, customerService);
        TransactionService transactionService = new TransactionService(transactionDao, accountService);
        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader,router, customerService));
        router.addScreen(new LoginScreen(consoleReader, router, customerService));
        router.addScreen(new DashboardScreen(consoleReader, router, customerService));
        router.addScreen(new WithdrawScreen(consoleReader, router, accountService));
        router.addScreen(new DepositScreen(consoleReader, router, accountService));
        router.addScreen(new ChooseAccountScreen(consoleReader, router, accountService));
        router.addScreen(new AccountCreationScreen(consoleReader, router, accountService));
        router.addScreen(new AccountsScreen(consoleReader,router));
        router.addScreen(new TransactionHistoryScreen(consoleReader, router, transactionService));
        logger.log("Application Initialized");
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
