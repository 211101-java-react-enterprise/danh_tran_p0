package com.revature.bankapp.daos;

import com.revature.bankapp.models.Transactions;
import com.revature.bankapp.util.collections.LinkedList;
import com.revature.bankapp.util.collections.List;
import com.revature.bankapp.util.datasource.ConnectionFactory;

import java.sql.*;
import java.util.UUID;

public class TransactionDao {

    //selects all transactions log related to the user
    public List<Transactions> viewAllAccountsTransactions(String customer_id) {

        List<Transactions> transactionsList = new LinkedList<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from transactions_log tl\n" +
                    "join account a on a.id = tl.account_id\n" +
                    "join customer_account ca on ca.account_id = a.id \n" +
                    "where ca.customer_id = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setObject(1, UUID.fromString(customer_id));
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                Transactions transactions = new Transactions();
                transactions.setDescription(rs.getString("description"));
                transactions.setDate(rs.getTimestamp("date"));
                transactions.setAccount_id(rs.getInt("account_id"));
                transactionsList.add(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionsList;
    }

    //selects the transactions log of the selected account id
    public List<Transactions> selectTransactionByAccountId(UUID customer_id, String account_id) {

        List<Transactions> transactionsList = null;
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from account a\n" +
                    "join customer_account ca on ca.account_id = a.id \n" +
                    "join transactions_log tl on tl.account_id = a.id\n" +
                    "where ca.customer_id = ? and tl.account_id = ?";
            PreparedStatement pstat = conn.prepareStatement(query);
            pstat.setObject(1, customer_id);
            pstat.setInt(2, Integer.parseInt(account_id));
            ResultSet rs = pstat.executeQuery();
            if(rs.next() == false) {
                return null;
            } else {
                transactionsList = new LinkedList<>();
                do {
                    Transactions transactions = new Transactions();
                    transactions.setDescription(rs.getString("description"));
                    transactions.setDate(rs.getTimestamp("date"));
                    transactions.setAccount_id(rs.getInt("account_id"));
                    transactionsList.add(transactions);
                } while(rs.next());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionsList;
    }
}
