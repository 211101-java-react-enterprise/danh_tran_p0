package com.revature.bankapp.util.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Logger {

    private static Logger logger;
    private final boolean printToConsole;

    private Logger(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    public static Logger getLogger(boolean printToConsole) {
        if(logger == null) {
            logger = new Logger(printToConsole);
        }
        return logger;
    }

    public void log(String message) {
        try(Writer logWriter = new FileWriter("src/main/resources/logs/app.log", true)) {
            logWriter.write(message);

            if(printToConsole) {
                System.out.println(message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
