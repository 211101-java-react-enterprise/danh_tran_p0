package com.revature.bankapp.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//ConnectionFactory used to grab the connection to the database
public class ConnectionFactory {

    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private Properties prop = new Properties();

    private ConnectionFactory() {
        try {
            prop.load(new FileReader("resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        return connectionFactory;
    }

    public Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
