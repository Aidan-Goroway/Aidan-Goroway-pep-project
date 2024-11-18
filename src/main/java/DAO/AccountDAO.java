package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * You will need to design and create your own DAO classes from scratch. 
You should refer to prior mini-project lab examples and course material for guidance.

Please refrain from using a 'try-with-resources' block when connecting to your database. 
The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.
 */

public class AccountDAO { // assume table named account exists
    
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"),
                    rs.getString("password"));
                accounts.add(account);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getValidAccount(String username){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet pkeyResultSet = preparedStatement.executeQuery();

            if(pkeyResultSet.next()){
                Account account = new Account(
                    pkeyResultSet.getInt("account_id"), 
                    pkeyResultSet.getString("username"),
                    pkeyResultSet.getString("password"));
                return account;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)"; //does not need account_id, those are gen'd later
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
