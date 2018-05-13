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
        GUInew.getLogarea().appendText(addme);
        try {
            Database.PrepQueryLogbook(time, text);
        } catch (Exception e) {
            GUInew.getLogarea().appendText("ERROR: Database error, program can't function.\n");
            GUInew.showError("Fatale fout opgetreden!", "Er kon geen connectie met de Database gemaakt worden. Het programma kan zo niet functioneren.");
        }
    }

    static String getRules(int amount) {
        return Database.QueryFullLogbook(amount);
    }
}
