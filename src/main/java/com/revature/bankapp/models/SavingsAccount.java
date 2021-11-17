package com.revature.bankapp.models;

public class SavingsAccount implements Account {

    private int id;
    private double money;
    private final String type = "savings";
    private Customer customer;
    private int withdrawCap;

    public SavingsAccount(Customer customer) {
        this.customer = customer;
    }

    public SavingsAccount() {
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

}
