package com.revature.bankapp.screens;

import com.revature.bankapp.util.ScreenRouter;
import com.revature.bankapp.util.logging.Logger;

import java.io.BufferedReader;

public abstract class Screen {

    protected Logger logger = Logger.getLogger(true);
    protected String name;
    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;

    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;
    }

    public String getName() {
        return name;
    }
    public String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;

}
