package com.revature.bankapp.daos;

import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.util.datasource.ConnectionFactory;

import java.sql.*;
import java.util.UUID;

//Data access object for Customers
public class CustomerDao implements CrudDAO<Customer> {

    //Finds user information if they exist in the database
    public Customer findUserByUsernameAndPassword(String username, String password) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            //String query = "select * from customer where username = ? and password = ?";
            String query = "select * from customer where username = ? and password = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setString(1,username);
            pstat.setString(2,password);

            ResultSet rs = pstat.executeQuery();
            if(rs.next()) {
                Customer customer = new Customer();
                customer.setId((UUID)rs.getObject("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer findUserByUsername(String username) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "select * from customer where username = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setString(1,username);

            ResultSet rs = pstat.executeQuery();
            if(rs.next()) {
                Customer customer = new Customer();
                customer.setId((UUID)rs.getObject("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
                return customer;
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
            String query = "select * from customer where email = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setString(1, email);

            ResultSet rs = pstat.executeQuery();
            if(rs.next()) {
                Customer customer = new Customer();
                customer.setId((UUID)rs.getObject("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
                return customer;
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

            String query = "insert into customer (id, first_name, last_name, email, username, password) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstat = conn.prepareStatement(query);
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
