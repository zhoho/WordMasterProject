import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     private static Connection conn = null;

     public static Connection getConnection(){
         if(conn == null){
             try {
                 Class.forName("org.sqlite.JDBC");
             } catch (ClassNotFoundException e) {
                 throw new RuntimeException(e);
             }
             try {
                 conn = DriverManager.getConnection("jdbc:sqlite:mywordlist.db");
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         return conn;
     }
     public static void closeConnection(){
         if(conn != null){
             try {
                 conn.close();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
     }
}
