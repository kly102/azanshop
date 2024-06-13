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
import model.Product;

/**
 *
 * @author Admin
 */
public class AcountDBContext extends DBContext {

public List<Account> getAllAccount() {
    List<Account> list = new ArrayList<>();
    PreparedStatement stm = null;

    try {
        stm = connection.prepareStatement("SELECT * FROM Account WHERE isAdmin != 1");
        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Account account = new Account();
                account.setUid(rs.getInt(1));
                account.setUser(rs.getString(2));
                account.setPass(rs.getString(3));
                account.setIsSell(rs.getInt(4));
                account.setIsAdmin(rs.getInt(5));
                account.setActive(rs.getBoolean(6));

                list.add(account);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    return list;
}
    public List<Account> getAllAccountByPage(int page, int pageSize) {
        List<Account> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account where isAdmin != 1"
                    + " order by uID\n"
                    + "offset (?-1)*? row fetch next ? rows only";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, page);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setUid(rs.getInt(1));
                account.setUser(rs.getString(2));
                account.setPass(rs.getString(3));
                account.setIsSell(rs.getInt(4));
                account.setIsAdmin(rs.getInt(5));
                account.setActive(rs.getBoolean(6));

                list.add(account);
            }
        } catch (Exception ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getTotalAccount() {
        List<Account> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account where isAdmin != 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setUid(rs.getInt(1));
                account.setUser(rs.getString(2));
                account.setPass(rs.getString(3));
                account.setIsSell(rs.getInt(4));
                account.setIsAdmin(rs.getInt(5));
                account.setActive(rs.getBoolean(6));

                list.add(account);
            }
        } catch (Exception ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list.size();
    }

public Account login(String user, String pass) {
    String sql = "SELECT * FROM Account WHERE [user] = ? AND pass = ?";
    
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setString(1, user);
        stm.setString(2, pass);

        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                Account a = new Account();
                a.setUid(rs.getInt(1));
                a.setUser(rs.getString(2));
                a.setPass(rs.getString(3));
                a.setIsSell(rs.getInt(4));
                a.setIsAdmin(rs.getInt(5));
                a.setActive(rs.getBoolean(6));
                
                return a;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return null;
}

public Account checkAccountExist(String user) {
    String sql = "SELECT * FROM Account WHERE [user] = ?";
    
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setString(1, user);

        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                Account a = new Account();
                a.setUid(rs.getInt(1));
                a.setUser(rs.getString(2));
                a.setPass(rs.getString(3));
                a.setIsSell(rs.getInt(4));
                a.setIsAdmin(rs.getInt(5));
                a.setActive(rs.getBoolean(6));
                
                return a;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return null;
}

public void insertAccount(String user, String pass) {
    String sql = "INSERT INTO Account([user], pass, isSell, isAdmin, active) VALUES(?, ?, 0, 0, 1)";
    
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setString(1, user);
        stm.setString(2, pass);
        stm.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public Account getAccountById(int accountId) {
    try {
        String sql = "SELECT * FROM Account WHERE uID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, accountId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setUid(rs.getInt(1));
                    account.setUser(rs.getString(2));
                    account.setPass(rs.getString(3));
                    account.setIsSell(rs.getInt(4));
                    account.setIsAdmin(rs.getInt(5));
                    account.setActive(rs.getBoolean(6));

                    return account;
                }
            }
        }
    } catch (Exception ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return null;
}

   public void updateAccount(Account account) {
    PreparedStatement stm = null;
    try {
        String sql = "UPDATE Account SET active = ? WHERE uId = ?";
        stm = connection.prepareStatement(sql);
        stm.setBoolean(1, account.isActive());
        stm.setInt(2, account.getUid());
        stm.executeUpdate();
    } catch (SQLException ex) {
         Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}

    public static void main(String[] args) {
        AcountDBContext sd = new AcountDBContext();
        List<Account> li = sd.getAllAccount();
        System.out.println(li.get(0).getUid());
    }

public void UpDatePassWord(String pass, String user) {
    PreparedStatement stm = null;
    try {
        stm = connection.prepareStatement("UPDATE Account SET pass = ? WHERE user = ?");
        stm.setString(1, pass);
        stm.setString(2, user);
        stm.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AcountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
}
