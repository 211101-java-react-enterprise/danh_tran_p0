package com.revature.bankapp.daos;

import com.revature.bankapp.models.*;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;
import com.revature.bankapp.util.datasource.ConnectionFactory;

import java.sql.*;
import java.util.UUID;

public class AccountDao implements CrudDAO<Account> {

    //persists the account creation into the database
    @Override
    public Account save(Account newAccount) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query1 = "insert into account(type) values (?)";
            PreparedStatement pstat1 = conn.prepareStatement(query1);
            pstat1.setString(1, newAccount.getType());
            int rowsInserted = pstat1.executeUpdate();
            if(rowsInserted != 0) {
                String query3 = "insert into customer_account (customer_id, account_id) values (?, (select max(id) from account))";
                PreparedStatement pstat2 = conn.prepareStatement(query3);
                pstat2.setObject(1,newAccount.getCustomer().getId());
                pstat2.executeUpdate();
                return newAccount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public boolean update(Account accountToUpdate) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "update account\n" +
                    "set money = ?, type = ?\n" +
                    "where id = ?;";

            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setDouble(1,accountToUpdate.getMoney());
            pstat.setString(2,accountToUpdate.getType());
            pstat.setInt(3,accountToUpdate.getId());
            int rowsUpdated = pstat.executeUpdate();

            if(rowsUpdated != 0) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
    //Find all accounts that are owned by the current user
    public List<Account> findAccountsByCustomerId(String customer_id) {
        List<Account> myAccounts = new LinkedList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from account a\n" +
                    "join customer_account ca on ca.account_id = a.id \n" +
                    "where ca.customer_id = ?\n" +
                    "order by type, a.id ";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setObject(1,UUID.fromString(customer_id));

            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                Account account = null;
                String type = rs.getString("type");
                if(type.equals("checkings")) {
                    account = new CheckingsAccount();
                } else if(type.equals("savings")) {
                    account = new SavingsAccount();
                }
                account.setMoney(rs.getDouble("money"));
                account.setId(rs.getInt("account_id"));
                myAccounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myAccounts;

    }

    //find a particular account owned by user
    public Account findAccountByCustomerAndAccountId(UUID customer_id, String account_id) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "select * from account a\n" +
                          "join customer_account ca on ca.account_id = a.id\n" +
                          "where a.id = ? and ca.customer_id = ?\n";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setInt(1, Integer.parseInt(account_id));
            pstat.setObject(2, customer_id);

            ResultSet rs = pstat.executeQuery();
            Account account = null;
            if(rs.next()) {
                String type = rs.getString("type");
                if(type.equals("checkings")) {
                    account = new CheckingsAccount();
                } else if (type.equals("savings")) {
                    account = new SavingsAccount();
                }
                account.setMoney(rs.getDouble("money"));
                account.setId(rs.getInt("id"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    //Adds a transaction record alongside the withdraw and deposit methods
    public void addTransactionRecord(Account account, String description) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "insert into transactions_log (id, description, date, account_id)" +
                    " values (?, ?, ?, ?)";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setObject(1, UUID.randomUUID());
            pstat.setString(2,description);
            pstat.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            pstat.setInt(4, account.getId());
            pstat.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
