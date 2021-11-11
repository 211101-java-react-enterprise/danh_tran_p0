package com.revature.bankapp.daos;

import com.revature.bankapp.models.Customer;
import com.revature.bankapp.util.ConnectionFactory;

import java.io.*;
import java.sql.*;
import java.util.UUID;

//Data access object for Customers
public class CustomerDao implements CrudDAO<Customer> {

    File userFile = new File("resources/customerData.txt");

    //Finds user information if they exist in the database
    public Customer findUserByUsernameAndPassword(String username, String password) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from customer where username = ? and password = ?";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1,username);
            pstat.setString(2,password);

            ResultSet queryResult = pstat.executeQuery();
            if(queryResult.next()) {
                String firstName = queryResult.getString("first_name");
                String lastName = queryResult.getString("last_name");
                String email = queryResult.getString("email");

                return new Customer(firstName, lastName, email, username, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //isn't necessarily only for CustomerService.isEmailTaken,
    //can be used in forgot username
    //Finds user data by their email
    public Customer findUserByEmail(String email) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from customer where email = ?";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1, email);

            ResultSet queryResult = pstat.executeQuery();
            if(queryResult.next()) {
                String firstName = queryResult.getString("first_name");
                String lastName = queryResult.getString("last_name");
                String username = queryResult.getString("username");
                String password = queryResult.getString("password");
                return new Customer(firstName, lastName, email, username, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer save(Customer newUser) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            newUser.setId(UUID.randomUUID());

            String sql = "insert into customer (id, first_name, last_name, email, username, password) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setObject(1, newUser.getId());
            pstat.setString(2, newUser.getFirstName());
            pstat.setString(3, newUser.getLastName());
            pstat.setString(4, newUser.getEmail());
            pstat.setString(5, newUser.getUsername());
            pstat.setString(6, newUser.getPassword());

            int rowsInserted = pstat.executeUpdate();
            if(rowsInserted != 0) {
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Customer element) {

        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Customer findById(String id) {
        return null;
    }
}
