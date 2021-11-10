package com.revature.bankapp.daos;

public interface CrudDAO<T> {

    //Create row of information
    T save(T newUser);

    //Read row from database
    T findById(String id);

    //Update row from database
    boolean update(T updatedUser);

    //Delete row from database
    boolean delete(String id);

}
