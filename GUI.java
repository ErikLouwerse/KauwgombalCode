/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author erikl
 */
public class GUI extends JFrame implements ActionListener {

    private Communicator1 Communicator1;
    private Communicator2 Communicator2;
    private Database Database;
    private JCheckBox jcOtherColor;
    private JLabel jlYellow;
    private JTextField jtfYellow;
    private JLabel jlRed;
    private JTextField jtfRed;
    private JLabel jlGreen;
    private JTextField jtfGreen;
    private JLabel jlBlue;
    private JTextField jtfBlue;
    private JLabel jlQuantity;
    private JTextField jtfQuantity;
    private JButton jbSave;
    private boolean OtherColorChecked = false;

    GUI(Communicator1 Communicator1, Communicator2 Communicator2) {

        setTitle("GUI");
        setSize(1366, 768);
        setLayout(new FlowLayout());
        
        this.Communicator1 = Communicator1;
        this.Communicator2 = Communicator2;

        if (OtherColorChecked == true) {
            jcOtherColor = new JCheckBox("Dispose unnecessary Colors", true);
            add(jcOtherColor);
            jcOtherColor.addActionListener(this);
        } else {
            jcOtherColor = new JCheckBox("Dispose unnecessary Colors", false);
            add(jcOtherColor);
            jcOtherColor.addActionListener(this);
        }

        jlYellow = new JLabel("Aantal gele ballen: ");
        add(jlYellow);
        jtfYellow = new JTextField(3);
        add(jtfYellow);

        jlRed = new JLabel("Aantal rode ballen: ");
        add(jlRed);
        jtfRed = new JTextField(3);
        add(jtfRed);

        jlGreen = new JLabel("Aantal groene ballen: ");
        add(jlGreen);
        jtfGreen = new JTextField(3);
        add(jtfGreen);

        jlBlue = new JLabel("Aantal blauwe ballen: ");
        add(jlBlue);
        jtfBlue = new JTextField(3);
        add(jtfBlue);

        jlQuantity = new JLabel("Aantal pakjes: ");
        add(jlQuantity);
        jtfQuantity = new JTextField(3);
        add(jtfQuantity);

        jbSave = new JButton("Save");
        add(jbSave);
        jbSave.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jcOtherColor) {
            if (OtherColorChecked == true) {
                OtherColorChecked = false;
                Communicator1.ColorDisposeOff();
                Database.Query("UPDATE `settings` SET `Waarde`= 0 WHERE `Naam` = 'Afvoeren'");
            } else if (OtherColorChecked == false) {
                OtherColorChecked = true;
                Communicator1.ColorDisposeOn();
                Database.Query("UPDATE `settings` SET `Waarde`= 1 WHERE `Naam` = 'Afvoeren'");
            }
        } else if (e.getSource() == jbSave) {
            try {
                int YellowAmount = Integer.parseInt(jtfYellow.getText());
                int RedAmount = Integer.parseInt(jtfRed.getText());
                int GreenAmount = Integer.parseInt(jtfGreen.getText());
                int BlueAmount = Integer.parseInt(jtfBlue.getText());
                int QuantityAmount = Integer.parseInt(jtfQuantity.getText());
                Communicator2.YellowBalls(YellowAmount);
                Communicator2.RedBalls(RedAmount);
                Communicator2.GreenBalls(GreenAmount);
                Communicator2.Blueballs(BlueAmount);
                Communicator2.QuantityPackage(QuantityAmount);
                Database.PrepQuery("INSERT INTO `Logboek` (`Transactienummer`, `Geel`, `Rood`, `Groen`, `Blauw`, `Aantal pakketten`) SELECT MAX(Transactienummer)+1,?,?,?,?,? FROM Logboek", YellowAmount, RedAmount, GreenAmount, BlueAmount, QuantityAmount);
            } catch (NumberFormatException a) {
                System.out.println(a);
            }
        }
    }
}
