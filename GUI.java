/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author erikl
 */
public class GUI extends JFrame implements ActionListener {

    private SerialTest SerialTest;
    private JButton jbSpeedUp;
    private JButton jbSlowDown;
    private JLabel jlCurrentSpeed;

    public GUI(SerialTest SerialTest) {
        
        setTitle("GUI");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        
        this.SerialTest = SerialTest;
        jbSpeedUp = new JButton("Speed up");
        add(jbSpeedUp);
        jbSpeedUp.addActionListener(this);

        jbSlowDown = new JButton("Slow down");
        add(jbSlowDown);
        jbSlowDown.addActionListener(this);
        
        jlCurrentSpeed = new JLabel();
        add(jlCurrentSpeed);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jbSpeedUp) {
            SerialTest.SpeedUp();
        } else if (e.getSource() == jbSlowDown) {
            SerialTest.SlowDown();
        }
    }
}