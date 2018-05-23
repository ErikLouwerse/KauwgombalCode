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

    static Connection getConnection() {
        if (con != null) {
            return con;
        }
        return getNewConnection();
    }

    private static Connection getNewConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + dbname + "?useSSL=false", username, password);
        } catch (Exception e) {
            System.out.println("Error while trying to get a new Database connection!");
        }
        return con;
    }

    static List<Integer> QueryPrevSettings() {
        List<Integer> values = new ArrayList<>();
        Connection con = getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `transacties` WHERE Transactienummer = (SELECT MAX(Transactienummer) FROM `transacties`)")) {
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
        Connection con = getConnection();
        try (PreparedStatement s = con.prepareStatement("INSERT INTO logboek (Tijd, Activiteit) VALUES (?, ?)")) {
            s.setLong(1, time);
            s.setString(2, text);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
    }

    static void Query(String query) {
        Connection con = getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            rs.next();
            setGeelBak(rs.getInt("Aantal"));
            rs.next();
            setRoodBak(rs.getInt("Aantal"));
            rs.next();
            setGroenBak(rs.getInt("Aantal"));
            rs.next();
            setBlauwBak(rs.getInt("Aantal"));

        } catch (Exception e) {

        }

    }

    static void UpdateQuery(String kleur) {
        Connection con = getConnection();
        try {
        if (kleur.equals("geel")) {
            PreparedStatement s = con.prepareStatement("Update aantal_ballen set Aantal = Aantal+1 where Naam = 'Geel'");
            s.executeUpdate();
        } else if (kleur.equals("rood")) {
            PreparedStatement s = con.prepareStatement("Update aantal_ballen set Aantal = Aantal+1 where Naam = 'Rood'");
            s.executeUpdate();
        } else if (kleur.equals("groen")) {
            PreparedStatement s = con.prepareStatement("Update aantal_ballen set Aantal = Aantal+1 where Naam = 'Groen'");
            s.executeUpdate();
        } else if (kleur.equals("blauw")) {
            PreparedStatement s = con.prepareStatement("Update aantal_ballen set Aantal = Aantal+1 where Naam = 'Blauw'");
            s.executeUpdate();
        }
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    static void PrepQuery(String query, int yellow, int red, int green, int blue, int quantity, boolean dispose, int speed) {
        Connection con = getConnection();
        try (PreparedStatement s = con.prepareStatement(query)) {
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

    static String QueryFullLogbook(int amount) {
        StringBuilder sb = new StringBuilder();
        Connection con = getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `logboek` ORDER BY Tijd DESC LIMIT " + amount)) {
            while (rs.next()) {
                long dbtime = rs.getLong("Tijd");
                String text = rs.getString("Activiteit");
                String time = Logboek.convertTime(dbtime);
                sb.append("[").append(time).append("]  ").append(text).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: No connection with Database!");
        }
        return sb.toString();
    }

    private static void setGeelBak(int geelBak) {
        Communicator2.geelBak = geelBak;
    }

    private static void setRoodBak(int roodBak) {
        Communicator2.roodBak = roodBak;
    }

    private static void setGroenBak(int groenBak) {
        Communicator2.groenBak = groenBak;
    }

    private static void setBlauwBak(int blauwBak) {
        Communicator2.blauwBak = blauwBak;
    }
}
