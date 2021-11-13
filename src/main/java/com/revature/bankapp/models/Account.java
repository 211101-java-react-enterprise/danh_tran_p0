package com.revature.bankapp.models;

public interface Account {

    public double getMoney();

    public void setMoney(double money);

    public int getId();

    public void setId(int id);

    public Customer getCustomer();

    public String getType();

    public void setType(String type);

    //should maybe just get the customer ID instead of the whole customer object
    //public LinkedList<Customer> getCustomer() return customerList;


}
