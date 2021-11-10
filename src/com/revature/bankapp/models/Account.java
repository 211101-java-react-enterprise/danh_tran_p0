package com.revature.bankapp.models;

public abstract class Account {

    private double money;
    private int id;
    private Customer customer;

    public Account(Customer customer) {
        this.customer = customer;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    //might not need this
    public void setId(int id) {
        this.id = id;
    }

    //should maybe just get the customer ID instead of the whole customer object
    public Customer getCustomer() {
        return customer;
    }
}
