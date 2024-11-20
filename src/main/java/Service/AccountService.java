package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

import javax.print.DocFlavor.STRING;

public class AccountService {
    AccountDAO accountDAO;
    
    /**
     * No args constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    /**
     * Constructor when accountDAO is provided.
     * For testing purposes.
     */
    public AccountService(AccountDAO accountDAO){
        accountDAO = new AccountDAO();
    }

    /**
     * Adds an account.
     * @param account An account to be added.
     * @return Returns an account to be inserted, or null in the case of an sql error.
     */
    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    /**
     * Looks for a valid account when loging in or registering, by searching the username.
     * @param username Account's username, which is a String.
     * @return Returns an Account, or null on failcases.
     */
    public Account getValidAccount(String username){
        return accountDAO.getValidAccount(username);
    }

    /**
     * Looks for a valid account when loging in or registering, by searching the account_id.
     * @param account_id Account's account_id, which is an int.
     * @return Returns an Account, or null on failcases.
     */
    public Account getValidAccount(int account_id){
        return accountDAO.getValidAccount(account_id);
    }

}
