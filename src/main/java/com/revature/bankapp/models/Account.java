package com.revature.bankapp.models;

import java.util.UUID;

public interface Account {

    public double getMoney();

    public void setMoney(double money);

    public int getId();

    public void setId(int id);

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public String getType();

    //should maybe just get the customer ID instead of the whole customer object
    //public LinkedList<Customer> getCustomer() return customerList;


}
