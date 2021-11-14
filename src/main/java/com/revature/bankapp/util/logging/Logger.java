package com.revature.bankapp.util.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Logger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";

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

    public void log(String msg, Object...args) {
        try(Writer logWriter = new FileWriter("src/main/resources/logs/app.log", true)) {

            String formattedMsg = String.format(msg, args);
            logWriter.write(formattedMsg + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logPrint(String msg, Object...args) {
        try(Writer logWriter = new FileWriter("src/main/resources/logs/app.log", true)) {

            String formattedMsg = String.format(msg, args);
            logWriter.write(formattedMsg + "\n");

            if (printToConsole) {
                System.out.println(formattedMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String msg) {
        try (Writer logWriter = new FileWriter("src/main/resources/logs/app.log", true)) {

            logWriter.write(msg + "\n");

            if (printToConsole) {
                System.out.println(ANSI_GREEN + msg + ANSI_RESET);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void warn(String msg) {
        try (Writer logWriter = new FileWriter("src/main/resources/logs/app.log", true)) {

            logWriter.write(msg + "\n");

            if (printToConsole) {
                System.out.println(ANSI_YELLOW + msg + ANSI_RESET);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
