import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
     private static Connection conn = null;

     public static Connection getConnection(){
         if(conn == null){
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:wordDB.db");
         }
         return conn;
     }

     public static void closeConnection(){
         if(conn != null){
            conn.close();
         }
     }
}
