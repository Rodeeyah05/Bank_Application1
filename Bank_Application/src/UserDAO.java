import java.sql.*;

public class UserDAO {
    public boolean registerUser(Users user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB_Connection.getConnection();
            String sql = "INSERT INTO Users (name, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhoneNumber());

            int rows = pstmt.executeUpdate();

            if(rows > 0){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public Users getUserByUsername(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = DB_Connection.getConnection();
            String sql = "SELECT * FROM Users WHERE Username = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String user = rs.getString("Username");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String phoneNumber = rs.getString("Phone_Number");
                String createdAt = rs.getString("Created_At");
                Users foundUser = new Users(id, name, user, email, password, phoneNumber, createdAt);
                return foundUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Users getUserByID(int ID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = DB_Connection.getConnection();
            String sql = "SELECT * FROM Users WHERE ID= ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ID);

            rs = pstmt.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String user = rs.getString("Username");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String phoneNumber = rs.getString("Phone_Number");
                String createdAt = rs.getString("Created_At");

                Users foundUser2 = new Users(id, name, user, email, password, phoneNumber, createdAt);
                return foundUser2;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

}
}

