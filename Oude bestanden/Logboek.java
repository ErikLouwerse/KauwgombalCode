package javaarduino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logboek {
    private static ArrayList<String> logrules = new ArrayList<>();

    private static String convertTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date resultdate = new Date(millis);
        return sdf.format(resultdate);
    }

    public static void addRule(long time, String text) {
        String addme = "[" + convertTime(time) + "] " + text;
        logrules.add(addme);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://104.199.31.189:3306/kauwgombal?useSSL=false", "root", "1424bb@12");
            PreparedStatement s = con.prepareStatement("INSERT INTO logboek (Tijd, Activiteit) VALUES (?, ?)");
            s.setLong(1, time);
            s.setString(2, text);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        GUInew.getLogarea().appendText(addme + "\n");
    }

    public static void printAll() {
        for(int i=0; i<logrules.size(); i++) {
            GUInew.getLogarea().appendText(logrules.get(i));
        }
    }
}
