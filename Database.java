/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author erikl
 */
public class Database {

    static void Query(String Query) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kauwgombaldb?serverTimezone=UTC", "root", "");
            Statement stmt = con.createStatement();
            stmt.execute(Query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void PrepQuery(String Query, int Variable, int Variable2, int Variable3, int Variable4, int Variable5) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kauwgombaldb?serverTimezone=UTC", "root", "");
            PreparedStatement s = con.prepareStatement(Query);
            s.setInt(1, Variable);
            s.setInt(2, Variable2);
            s.setInt(3, Variable3);
            s.setInt(4, Variable4);
            s.setInt(5, Variable5);
            s.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
