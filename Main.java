/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino;

/**
 *
 * @author erikl
 */
import javax.swing.JFrame;

/**
 *
 * @author erikl
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Communicator1 Communicator1 = new Communicator1();
        Communicator2 Communicator2 = new Communicator2();
        
        Communicator1.initialize();
        Communicator2.initialize();
        
        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();
        
        GUI gui = new GUI(Communicator1, Communicator2);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
