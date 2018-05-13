package javaarduino;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    static List<Integer> QueryPrevSettings() {
        List<Integer> values = new ArrayList<>();
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `transacties` WHERE Transactienummer = (SELECT MAX(Transactienummer) FROM `transacties`)");
            while (rs.next()) {
                int yellow = rs.getInt("Geel");
                int red = rs.getInt("Rood");
                int green = rs.getInt("Groen");
                int blue = rs.getInt("Blauw");
                int quantity = rs.getInt("Aantal pakketten");
                int dispose = rs.getInt("Afvoeren");
                int speed = rs.getInt("Snelheid");
                List<Integer> numbers = Arrays.asList(yellow, red, green, blue, quantity, dispose, speed);
                values.addAll(numbers);
            }
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
        return values;
    }

    static void PrepQueryLogbook(long time, String text) {
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

    static void PrepQuery(String query, int yellow, int red, int green, int blue, int quantity, boolean dispose, int speed) {
        try {
            Connection con = getConnection();
            PreparedStatement s = con.prepareStatement(query);
            s.setInt(1, yellow);
            s.setInt(2, red);
            s.setInt(3, green);
            s.setInt(4, blue);
            s.setInt(5, quantity);
            s.setBoolean(6, dispose);
            s.setInt(7, speed);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
    }
}
