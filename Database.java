package javaarduino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection con = null;
    private static String host = "104.199.31.189";
    private static String dbname = "kauwgombal";
    private static String username = "root";
    private static String password = "1424bb@12";

    private static Connection getConnection() {
        if (con != null) {
            return con;
        }
        return getNewConnection();
    }

    private static Connection getNewConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ host +":3306/"+ dbname +"?useSSL=false", username, password);
        } catch(Exception e) {
            System.out.println("Error while trying to get a new Database connection!");
        }
        return con;
    }

    static void Query(String Query) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(Query);
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
    }

    static void PrepQueryLogboek(long time, String text) {
        try {
            Connection con = getConnection();
            PreparedStatement s = con.prepareStatement("INSERT INTO logboek (Tijd, Activiteit) VALUES (?, ?)");
            s.setLong(1, time);
            s.setString(2, text);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
    }

    static void PrepQuery(String Query, int Variable, int Variable2, int Variable3, int Variable4, int Variable5) {
        try {
            Connection con = getConnection();
            PreparedStatement s = con.prepareStatement(Query);
            s.setInt(1, Variable);
            s.setInt(2, Variable2);
            s.setInt(3, Variable3);
            s.setInt(4, Variable4);
            s.setInt(5, Variable5);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
    }
}
