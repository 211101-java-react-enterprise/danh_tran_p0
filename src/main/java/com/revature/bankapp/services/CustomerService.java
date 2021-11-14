package com.revature.bankapp.services;

import com.revature.bankapp.exceptions.AuthenticationException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.daos.CustomerDao;

//validation class for user registration
public class CustomerService {

    private CustomerDao customerDao;
    private Customer sessionUser;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
        sessionUser = null;
    }

    public Customer getSessionUser() {
        return sessionUser;
    }

    public void logout() {
        sessionUser = null;
    }

    public boolean isSessionActive() {
        return sessionUser != null;
    }

    public boolean registerNewUser(Customer user) {
        if(!isUserValid(user)) {
            System.out.println("Missing entry for registration");
            return false;
        }
        if(!isEmailValid(user)) {
            System.out.println("Email is not a valid email");
            return false;
        }
        if(isEmailTaken(user.getEmail())) {
            System.out.println("Email is already in use");
            return false;
        }
        if(isUsernameTaken(user.getUsername())) {
            System.out.println("Username is already in use");
            return false;
        }

        customerDao.save(user);
        return true;

    }

    public boolean isUserValid(Customer user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    public boolean isUsernameValid(Customer user) {

        return true;
    }

    public boolean isUsernameTaken(String username) {
        return customerDao.findUserByUsername(username) != null;
    }

    public void authenticateLogin(String username, String password) {
        if(username == null || username.equals("") || password == null || password.equals("")) {
            throw new InvalidRequestException("Invalid Credentials provided");
        }
        Customer authenticatedUser = customerDao.findUserByUsernameAndPassword(username, password);
        if(authenticatedUser == null) {
            throw new AuthenticationException();
        }
        sessionUser = authenticatedUser;
    }

    public boolean isEmailValid(Customer user) {
        String emailRegex = "(.+)@(.+)\\.(.*)";
        return user.getEmail().matches(emailRegex);
    }

    public boolean isEmailTaken(String email) {
        return customerDao.findUserByEmail(email) != null;
    }

}
