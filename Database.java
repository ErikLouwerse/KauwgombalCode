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
            System.out.println("ERROR: niet gelukt om te verbinden met de Database!");
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
                List<Integer> numbers = Arrays.asList(yellow, red, green, blue, quantity, dispose);
                values.addAll(numbers);
            }
        } catch (SQLException e) {
            System.out.println("Error: Geen connectie met Database!");
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
            System.out.println("Error: Geen connectie met Database!");
        }
    }

    static void Query(String query) {
        Connection con = getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            rs.next();
            setGeelcount(rs.getInt("Aantal"));
            setGeelBak(rs.getInt("Aantal"));
            rs.next();
            setRoodcount(rs.getInt("Aantal"));
            setRoodBak(rs.getInt("Aantal"));
            rs.next();
            setGroencount(rs.getInt("Aantal"));
            setGroenBak(rs.getInt("Aantal"));
            rs.next();
            setBlauwcount(rs.getInt("Aantal"));
            setBlauwBak(rs.getInt("Aantal"));
        } catch (Exception e) {
        }
    }

    static void UpdateQuery(String query) {
        Connection con = getConnection();
        try (PreparedStatement s = con.prepareStatement(query)){
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void PrepQuery(String query, int yellow, int red, int green, int blue, int quantity, boolean dispose) {
        Connection con = getConnection();
        try (PreparedStatement s = con.prepareStatement(query)) {
            s.setInt(1, yellow);
            s.setInt(2, red);
            s.setInt(3, green);
            s.setInt(4, blue);
            s.setInt(5, quantity);
            s.setBoolean(6, dispose);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: Geen connectie met Database!");
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
            System.out.println("Error: Geen connectie met Database!");
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

    public static void setBlauwcount(int blauwcount) {
        Communicator1.blauwcount = blauwcount;
    }

    public static void setGeelcount(int geelcount) {
        Communicator1.geelcount = geelcount;
    }

    public static void setGroencount(int groencount) {
        Communicator1.groencount = groencount;
    }

    public static void setRoodcount(int roodcount) {
        Communicator1.roodcount = roodcount;
    }
}
