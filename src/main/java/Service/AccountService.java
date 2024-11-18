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
     * TODO
     * @param message
     * @return
     */
    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    public Account getValidAccount(Account account){
        return accountDAO.getValidAccount(account.getUsername(), account.getPassword());
    }

}
