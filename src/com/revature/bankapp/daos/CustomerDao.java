package com.revature.bankapp.daos;

import com.revature.bankapp.models.Customer;

import java.io.*;
import java.util.UUID;

//Data access object for Customers
public class CustomerDao implements CrudDAO<Customer> {

    File userFile = new File("resources/customerData.txt");

    //Finds user information if they exist in the database
    public Customer findUserByUsernameAndPassword(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))){
            String currentLine = br.readLine();
            while(currentLine != null) {
                String[] userArray = currentLine.split(":");
                if(username.equals(userArray[4]) && password.equals(userArray[5])) {
                    Customer customer = new Customer(userArray[1], userArray[2], userArray[3], userArray[4], userArray[5]);
                    customer.setId(userArray[0]);
                    return customer;
                }
                currentLine = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer save(Customer newUser) {
        try (FileWriter fileWriter = new FileWriter(userFile, true)){
            String uuid = UUID.randomUUID().toString();
            newUser.setId(uuid);
            fileWriter.write(newUser.toFileString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving user to file");
        }
        return newUser;
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
