/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author Admin
 */
public class AcountDBContext extends DBContext {

    private static final String SELECT_ACCOUNT_QUERY = "SELECT * FROM Account WHERE isAdmin != 1";
    private static final String SELECT_ACCOUNT_BY_PAGE_QUERY = "SELECT * FROM Account WHERE isAdmin != 1 ORDER BY uID OFFSET (?-1)*? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String LOGIN_QUERY = "SELECT * FROM Account WHERE [user] = ? AND pass = ?";
    private static final String CHECK_ACCOUNT_EXIST_QUERY = "SELECT * FROM Account WHERE [user] = ?";
    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO Account ([user], [pass], [isSell], [isAdmin], [active]) VALUES (?, ?, 0, 0, 1)";
    private static final String UPDATE_ACCOUNT_QUERY = "UPDATE Account SET active = ? WHERE uId = ?";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE Account SET pass = ? WHERE [user] = ?";
     private static final String SELECT_ACOUNT_BY_ID ="select *  from Account where uID = ?";

    public List<Account> getAllAccount() {
        List<Account> list = new ArrayList<>();

        try (PreparedStatement stm = connection.prepareStatement(SELECT_ACCOUNT_QUERY); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Account account = new Account();
                account.setUid(rs.getInt("uID"));
                account.setUser(rs.getString("user"));
                account.setPass(rs.getString("pass"));
                account.setIsSell(rs.getInt("isSell"));
                account.setIsAdmin(rs.getInt("isAdmin"));
                account.setActive(rs.getBoolean("active"));
                list.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public List<Account> getAllAccountByPage(int page, int pageSize) {
        List<Account> list = new ArrayList<>();

        try (PreparedStatement stm = connection.prepareStatement(SELECT_ACCOUNT_BY_PAGE_QUERY)) {
            stm.setInt(1, page);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageSize);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setUid(rs.getInt("uID"));
                    account.setUser(rs.getString("user"));
                    account.setPass(rs.getString("pass"));
                    account.setIsSell(rs.getInt("isSell"));
                    account.setIsAdmin(rs.getInt("isAdmin"));
                    account.setActive(rs.getBoolean("active"));
                    list.add(account);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int getTotalAccount() {
        int totalCount = 0;

        try (PreparedStatement stm = connection.prepareStatement(SELECT_ACCOUNT_QUERY); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                totalCount++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return totalCount;
    }

    public Account login(String user, String pass) {
        Account account = null;

        try (PreparedStatement stm = connection.prepareStatement(LOGIN_QUERY)) {
            stm.setString(1, user);
            stm.setString(2, pass);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    account = new Account();
                    account.setUid(rs.getInt("uID"));
                    account.setUser(rs.getString("user"));
                    account.setPass(rs.getString("pass"));
                    account.setIsSell(rs.getInt("isSell"));
                    account.setIsAdmin(rs.getInt("isAdmin"));
                    account.setActive(rs.getBoolean("active"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return account;
    }

    public Account checkAccountExist(String user) {
        Account account = null;

        try (PreparedStatement stm = connection.prepareStatement(CHECK_ACCOUNT_EXIST_QUERY)) {
            stm.setString(1, user);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    account = new Account();
                    account.setUid(rs.getInt(1));
                    account.setUser(rs.getString(2));
                    account.setPass(rs.getString(3));
                    account.setIsSell(rs.getInt(4));
                    account.setIsAdmin(rs.getInt(5));
                    account.setActive(rs.getBoolean(6));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return account;
    }

    public void insertAccount(String user, String pass) {
        try (PreparedStatement stm = connection.prepareStatement(INSERT_ACCOUNT_QUERY)) {
            stm.setString(1, user);
            stm.setString(2, pass);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAccountById(int accountId) {
    try (PreparedStatement stm = connection.prepareStatement(SELECT_ACOUNT_BY_ID)) {
        stm.setInt(1, accountId);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                Account account = new Account();
                account.setUid(rs.getInt("uID"));
                account.setUser(rs.getString("username"));
                account.setPass(rs.getString("password"));
                account.setIsSell(rs.getInt("isSeller"));
                account.setIsAdmin(rs.getInt("isAdmin"));
                account.setActive(rs.getBoolean("active"));

                return account;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}

    public void updateAccount(Account account) {
        try (PreparedStatement stm = connection.prepareStatement(UPDATE_ACCOUNT_QUERY)) {
            stm.setBoolean(1, account.isActive());
            stm.setInt(2, account.getUid());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        AcountDBContext sd = new AcountDBContext();
        List<Account> li = sd.getAllAccount();
        System.out.println(li.get(0).getUid());
    }

    public void UpDatePassWord(String pass, String user) {
        try (PreparedStatement stm = connection.prepareStatement(UPDATE_PASSWORD_QUERY)) {
            stm.setString(1, pass);
            stm.setString(2, user);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
