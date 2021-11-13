package com.revature.bankapp.models;

public class SavingsAccount implements Account {

    private int id;
    private double money;
    private String type;
    private Customer customer;
    int maxWithdrawlAmount;


    public SavingsAccount() {
    }


    @Override
    public double getMoney() {
        return 0;
    }

    @Override
    public void setMoney(double money) {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }


}
