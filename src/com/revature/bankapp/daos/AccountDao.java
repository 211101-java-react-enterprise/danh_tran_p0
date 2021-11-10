package com.revature.bankapp.daos;

import com.revature.bankapp.models.Account;

public class AccountDao implements CrudDAO<Account> {

    @Override
    public Account save(Account newUser) {
        return null;
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public boolean update(Account updatedUser) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

}
