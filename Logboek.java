package javaarduino;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logboek {
    private static ArrayList<String> logrules = new ArrayList<>();

    static String convertTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date resultdate = new Date(millis);
        return sdf.format(resultdate);
    }

    static void addRule(long time, String text) {
        String addme = "[" + convertTime(time) + "]  " + text + "\n";
        logrules.add(addme);
        if (GUInew.getLogarea() != null) {
            GUInew.getLogarea().appendText(addme);
        }
        try {
            if (Database.getConnection() != null) {
                Database.PrepQueryLogbook(time, text);
            }
        } catch (Exception e) {
            GUInew.getLogarea().appendText("ERROR: Database error, program can't function.\n");
        }
    }

    static String getRules(int amount) {
        return Database.QueryFullLogbook(amount);
    }
}
