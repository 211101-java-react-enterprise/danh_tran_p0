package com.revature.bankapp.models;

import java.util.UUID;

public class CheckingsAccount implements Account {

    private int id;
    private double money;
    private String type;
    private Customer customer;

    public CheckingsAccount(Customer customer) {
        this.customer = customer;
    }

    public CheckingsAccount() {
        super();
    }

    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

}
