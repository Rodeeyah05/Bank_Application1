
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDAO{

    public boolean recordTransactions(Transactions transactions) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Get connection
            conn = DB_Connection.getConnection();

            // SQL - Insert 7 columns (skip ID and Transaction_Date - auto-generated)
            String sql = "INSERT INTO Transactions (Account_ID, Transaction_Type, Amount, Balance_After, Recipient_Account_ID, Description, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Prepare statement
            pstmt = conn.prepareStatement(sql);

            // Set parameter 1: Account_ID
            pstmt.setInt(1, transactions.getAccountId());

            // Set parameter 2: Transaction_Type
            pstmt.setString(2, transactions.getTransactionType());

            // Set parameter 3: Amount
            pstmt.setDouble(3, transactions.getAmount());

            // Set parameter 4: Balance_After
            pstmt.setDouble(4, transactions.getBalanceAfter());

            // Set parameter 5: Recipient_Account_ID (handle null)
            if ( transactions.getReciepient_id()== 0) {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(5, transactions.getReciepient_id());
            }

            // Set parameter 6: Description (handle null)
            if (transactions.getDescription() == null) {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(6, transactions.getDescription());
            }

            // Set parameter 7: Status
            pstmt.setString(7, transactions.getStatus());

            // Execute update
            int rows = pstmt.executeUpdate();

            // Check if successful
            if (rows > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // If we get here, insert failed
        return false;
    }

   public List<Transactions> getTransactions(int AccountId) throws SQLException {
       Connection conn = null;
        PreparedStatement psmnt = null;
        ResultSet rs = null;
        List<Transactions> transactions = new ArrayList<>();
        try{
            conn = DB_Connection.getConnection();
            String sql = "SELECT * FROM Transactions WHERE AccountId = ? ORDER BY Transaction_Date DESC";
            psmnt = conn.prepareStatement(sql);
            psmnt.setInt(1,AccountId);
            rs = psmnt.executeQuery();
            while(rs.next()){
                int id  = rs.getInt("Transaction_Id");
                int accountId = rs.getInt("AccountId");
                String transactionType = rs.getString("TransactionType");
                Double amount = rs.getDouble("Amount");
                Double balanceAfter = rs.getDouble("BalanceAfter");
                int recipientId = rs.getInt("Recipient_Account_Id");
                String description = rs.getString("Description");
                String transactionDate = rs.getString("TransactionDate");
                String status = rs.getString("Status");
                Transactions transaction = new Transactions(id,accountId,transactionType,amount,balanceAfter,recipientId,description,transactionDate,status);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);


        }finally{
            conn.close();
            psmnt.close();
            rs.close();
        }

      return transactions;
  }

    }
