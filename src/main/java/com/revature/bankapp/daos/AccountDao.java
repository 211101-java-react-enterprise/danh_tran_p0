package com.revature.bankapp.daos;

import com.revature.bankapp.models.Account;
import com.revature.bankapp.models.CheckingsAccount;
import com.revature.bankapp.models.Customer;
import com.revature.bankapp.models.SavingsAccount;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;
import com.revature.bankapp.util.datasource.ConnectionFactory;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao implements CrudDAO<Account> {

    //here we should write two separate queries, one for account table,
    // another one for customer_account(customer_id, account_id) table
    @Override
    public Account save(Account newAccount) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into account (customer_id, account_type) values (?, ?)";
            PreparedStatement pstat = conn.prepareStatement(query);
            //pstat.setString(newAccount.getCustomer.getId);
            //pstat.setString(accountType);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    //change accounts
    @Override
    public Account findById(String id) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "Select * from account where id = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setInt(1,Integer.parseInt(id));

            ResultSet rs = pstat.executeQuery();

            if(rs.next()) {
                if(rs.getString("type").equals("checkings")) {
                    Account account = null;
                    String type = rs.getString("type");
                    if(type.equals("checkings")) {
                        account = new CheckingsAccount();
                    } else if(type.equals("savings")) {
                        account = new SavingsAccount();
                    }
                    account.setType(rs.getString("type"));
                    account.setMoney(rs.getDouble("money"));
                    account.setId(rs.getInt("id"));
                    return account;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Account accountToUpdate) {
        return false;
    }

    public void addMoney(Account accountToUpdate, double money) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "update account set money = (money + ?) where id = (\n" +
                    "select account_id from customer_account\n" +
                    "where customer_id = ?)\n";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setDouble(1,money);
            pstat.setObject(2,accountToUpdate.getCustomer().getId());

            int rowsUpdated = pstat.executeUpdate();
            if(rowsUpdated != 0) {
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean removeMoney(Account accountToUpdate, double money) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "update account set money = (money - ?) where id = (\n" +
                    "select account_id from customer_account\n" +
                    "where customer_id = ?)\n";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setDouble(1,money);
            pstat.setObject(2,accountToUpdate.getCustomer().getId());

            int rowsUpdated = pstat.executeUpdate();
            if(rowsUpdated != 0) {
                return true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void addTransaction(Account account, double money) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "";
            PreparedStatement pstat = conn.prepareStatement(query);

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    public Account findAccountByCustomerId() {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from account a join customer c on a.customer_id = c.id;";


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> findAllAccounts(Customer customer) {
        List<Account> myAccounts = new LinkedList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from account a\n" +
                    "where id = (\n" +
                    "select account_id from customer_account ca \n" +
                    "where customer_id = ?\n" +
                    ");";
            PreparedStatement pstat = conn.prepareStatement(query);
            System.out.println("USER ID IS " + customer.getId().toString());
            pstat.setObject(1,customer.getId());

            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                Account account = null;
                String type = rs.getString("type");
                if(type.equals("checkings")) {
                    account = new CheckingsAccount();
                } else if(type.equals("savings")) {
                    account = new SavingsAccount();
                }
                account.setType(rs.getString("type"));
                account.setMoney(rs.getDouble("money"));
                account.setId(rs.getInt("id"));
                myAccounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myAccounts;

    }

}
