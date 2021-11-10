package com.revature.bankapp.services;

import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.daos.CustomerDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


//validation class for user registration
public class CustomerService {

    private CustomerDao customerDao = new CustomerDao();

    public boolean registerNewUser(Customer user) {

        if(!isEmailValid(user)) {
            System.out.println("Enter an appropriate email");
            return false;
        }
        if(isUserEmpty(user)) {
            System.out.println("Missing entry for registration");
            return false;
        }
        if(isEmailTaken(user)) {
            System.out.println("Email is already in use");
            return false;
        }
        if(isUsernameTaken(user)) {
            System.out.println("Username is already in use");
            return false;
        }
        customerDao.save(user);
        return true;
    }

    public boolean isUserEmpty(Customer user) {
        if (user == null) return true;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return true;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return true;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return true;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return true;
        return user.getPassword() == null || user.getPassword().trim().equals("");
    }

    public boolean isUsernameValid(Customer user) {

        return true;
    }

    public boolean isUsernameTaken(Customer user) {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/customerData.txt"))) {
            String currentLine = br.readLine();
            while(currentLine != null) {
                String[] userArray = currentLine.split(":");
                if(userArray[4].equals(user.getUsername()))
                    return true;
                currentLine = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer authenticateLogin(String username, String password) {
        if(username == null || username.equals("") || password == null || password.equals("")) {
            throw new InvalidRequestException("Invalid Credentials");
        }
        Customer authenticatedUser = customerDao.findUserByUsernameAndPassword(username, password);
        if(authenticatedUser != null) {
            return authenticatedUser;
        }
        throw new RuntimeException("Unable to authenticate user with credentials");
    }

    public boolean isEmailValid(Customer user) {
        String emailRegex = "(.+)@(.+)\\.(.*)";
        return user.getEmail().matches(emailRegex);
    }

    public boolean isEmailTaken(Customer user) {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/customerData.txt"))) {
            String currentLine = br.readLine();
            while(currentLine != null) {
                String[] userArray = currentLine.split(":");
                if(userArray[3].equals(user.getEmail()))
                    return true;
                currentLine = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
