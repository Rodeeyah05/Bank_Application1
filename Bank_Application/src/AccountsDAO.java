import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountsDAO {

    public boolean createAccount(int userID, String accountNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB_Connection.getConnection();
           String sql = "INSERT INTO Accounts (User_ID, Account_Number) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setString(2, accountNumber);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            conn.close();
            pstmt.close();
        }
        return false;
    }

    public Accounts getAccountByUserID(int userID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DB_Connection.getConnection();
            String sql = "SELECT * FROM Accounts WHERE User_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int UserID = rs.getInt("User_ID");
                String AccountNumber = rs.getString("Account_Number");
                Accounts accounts = new Accounts(UserID,AccountNumber);
                return accounts;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();  
            pstmt.close();
            rs.close();
        }
        return null;
    }

    public boolean updateBalance (int accountID, Double balance) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DB_Connection.getConnection();
            String sql = "UPDATE Accounts SET Balance = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, accountID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
            pstmt.close();
        }
        return false;
    }
    public double getBalance (int accountID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = DB_Connection.getConnection();
            String sql = "SELECT Balance FROM Accounts WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Double balance = rs.getDouble("Balance");
                return balance;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();
            pstmt.close();
            rs.close();
        }
        return 0.00;
    }
    public Accounts getAccountByAccountNumber (String accountNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Accounts newAccounts = null;
        try {
            conn = DB_Connection.getConnection();
            String sql = "Select * from Accounts WHERE Account_Number = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                int userId = rs.getInt("User_ID");
                String accNumber = rs.getString("Account_Number");
                double balance = rs.getDouble("Balance");
                String accountType = rs.getString("Account_Type");
                String status = rs.getString("Status");
                String createdAt = rs.getString("Created_At");
                newAccounts = new Accounts(id, userId, accNumber, balance, accountType, status, createdAt);
                return newAccounts;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
            pstmt.close();
            rs.close();
        }
        return newAccounts;
    }
}
