import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    private static final String URL = "jdbc:mysql://localhost:3306/Bank_Application";
    private static final String USER = "root";
    private static final String PASS = "Rodeeyah05";

    public static Connection getConnection(){
      try{
          return (Connection) DriverManager.getConnection(URL,USER,PASS);
      } catch (SQLException e) {
      throw new RuntimeException("Connection Failed! Check output console");
      }
    }

    public static void closeConnection(Connection con) throws IOException, SQLException {
        if (con != null) {
            con.close();
        }
    }
}
