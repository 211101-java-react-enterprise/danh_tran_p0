package com.revature.bankapp.services;

import com.revature.bankapp.exceptions.AuthenticationException;
import com.revature.bankapp.exceptions.InvalidRequestException;
import com.revature.bankapp.exceptions.ResourcePersistenceException;
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

    //Used to grab the current user customer
    public Customer getSessionUser() {
        return sessionUser;
    }

    public void logout() {
        sessionUser = null;
    }

    //Used to user doesn't drop out of dashboard screen when finished on other screens
    public boolean isSessionActive() {
        return sessionUser != null;
    }

    public boolean registerNewUser(Customer user) {
        if(!isUserValid(user)) {
            throw new InvalidRequestException("Invalid user data provided");
        }
        if(!isEmailValid(user)) {
            throw new InvalidRequestException("Invalid email provided");
        }
        if(isEmailTaken(user.getEmail())) {
            throw new ResourcePersistenceException("Email provided is already taken.");
        }
        if(isUsernameTaken(user.getUsername())) {
            throw new ResourcePersistenceException("Username provided is already taken.");
        }

        Customer newCustomer = customerDao.save(user);
        if(newCustomer == null) {
            throw new ResourcePersistenceException("User could not be created at this time");
        }
        return true;

    }

    //user validation to check if user
    public boolean isUserValid(Customer user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    public boolean isUsernameTaken(String username) {
        return customerDao.findUserByUsername(username) != null;
    }

    //Used to login user customer if matches with correct credentials
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
