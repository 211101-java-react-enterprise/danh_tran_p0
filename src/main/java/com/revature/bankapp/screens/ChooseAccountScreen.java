package com.revature.bankapp.screens;

import com.revature.bankapp.exceptions.UnownedAccountException;
import com.revature.bankapp.models.Account;
import com.revature.bankapp.services.AccountService;
import com.revature.bankapp.util.ScreenRouter;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;

import java.io.BufferedReader;

public class ChooseAccountScreen extends Screen {

    AccountService accountService;
    public ChooseAccountScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService) {
        super("ChooseAccount", "/choose_account", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        List<Account> accountList = accountService.returnMyAccounts();

        if(accountList.isEmpty()) {
            System.out.println("You currently don't have any accounts");
            System.out.println("Navigating to account creation...");
            router.navigate("/create_account");
            return;
        }
        Account account = null;
        do {
            System.out.println("These are your accounts");

            for (int i = 0; i < accountList.size(); i++) {
                account = accountList.get(i);
                String type = account.getType().substring(0, 1).toUpperCase() + account.getType().substring(1);
                System.out.printf("%s Account current id is %d\n", type, account.getId());
                System.out.println("Currently deposited money in this account is: " + account.getMoney());
            }
            System.out.println("Select an account id to continue");
            String userSelection = consoleReader.readLine();
            try {
                accountService.getAccountById(userSelection);
                System.out.println("Sending user to dashboard...");
                router.navigate("/dashboard");
            } catch (UnownedAccountException e) {
                account = null;
                System.out.println(e.getMessage());
            }
        } while(account == null);






    }
}
