package javaarduino;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.util.Enumeration;
import javafx.scene.paint.Color;

public class Communicator1 implements SerialPortEventListener {

    private int blauwcount = 0;
    private int geelcount = 0;
    private int groencount = 0;
    private int roodcount = 0;

    SerialPort serialPort;

    //The port we're going to use.
    private String PORT_NAME[] = {"COM4"};

    //A BufferedReader which will be fed by a InputStreamReader converting the bytes into characters
    BufferedReader input;

    //The output stream to the port
    OutputStream output;

    //Milliseconds to block while waiting for port open
    private int TIME_OUT = 2000;

    //Default bits per second for COM port.
    private int DATA_RATE = 9600;

    void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, find an instance of serial port as set in PORT_NAME.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAME) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }

        if (portId == null) {
            System.out.println("ERROR: Could not find COM port for Arduino1!");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.out.println("ERROR: Could not initialize serialport or input/output streams!");
        }
    }

    //Handle an event on the serial port. Read the data and print it.
    @Override
    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                Logboek.addRule(System.currentTimeMillis(), "(Arduino1): " + inputLine);
                if (inputLine.equals("rood")) {
                    roodcount++;
                    for (int i = 0; i < 500; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        GUInew.gc.strokeRect(20, 200, 200, 100);
                        GUInew.gc.strokeLine(220,225, 425, 145);
                        GUInew.gc.strokeLine(220,275, 425, 195);
                        //GUInew.gc.strokeRect(220, 225, 200, 50);
                        GUInew.gc.strokeRect(450, 215, 80, 70);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(455, 232, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + groencount, 495, 255);
                        GUInew.gc.strokeRect(425, 135, 80, 70);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(430, 152, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + roodcount, 470, 175);
                        GUInew.gc.strokeRect(425, 295, 80, 70);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(430, 312, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + blauwcount, 470, 335);
                        GUInew.gc.strokeRect(400, 55, 80, 70);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(405, 72, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + geelcount, 445, 95);
                        GUInew.gc.strokeRect(400, 375, 80, 70);
                        GUInew.gc.fillText("Restbak", 410, 415);
                        Thread.sleep(20);
                    }
                }
                if (inputLine.equals("geel")) {
                    geelcount++;
                    for (int i = 0; i < 500; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        GUInew.gc.strokeRect(20, 200, 200, 100);
                        GUInew.gc.strokeLine(220,225, 400, 65);
                        GUInew.gc.strokeLine(220,275, 400, 115);
                        //GUInew.gc.strokeRect(220, 225, 200, 50);
                        GUInew.gc.strokeRect(450, 215, 80, 70);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(455, 232, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + groencount, 495, 255);
                        GUInew.gc.strokeRect(425, 135, 80, 70);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(430, 152, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + roodcount, 470, 175);
                        GUInew.gc.strokeRect(425, 295, 80, 70);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(430, 312, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + blauwcount, 470, 335);
                        GUInew.gc.strokeRect(400, 55, 80, 70);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(405, 72, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + geelcount, 445, 95);
                        GUInew.gc.strokeRect(400, 375, 80, 70);
                        GUInew.gc.fillText("Restbak", 410, 415);
                        Thread.sleep(20);
                    }
                }
                if (inputLine.equals("blauw")) {
                    blauwcount++;
                    for (int i = 0; i < 500; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        GUInew.gc.strokeRect(20, 200, 200, 100);
                        GUInew.gc.strokeLine(220,225, 425, 305);
                        GUInew.gc.strokeLine(220,275, 425, 355);
                        //GUInew.gc.strokeRect(220, 225, 200, 50);
                        GUInew.gc.strokeRect(450, 215, 80, 70);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(455, 232, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + groencount, 495, 255);
                        GUInew.gc.strokeRect(425, 135, 80, 70);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(430, 152, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + roodcount, 470, 175);
                        GUInew.gc.strokeRect(425, 295, 80, 70);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(430, 312, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + blauwcount, 470, 335);
                        GUInew.gc.strokeRect(400, 55, 80, 70);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(405, 72, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + geelcount, 445, 95);
                        GUInew.gc.strokeRect(400, 375, 80, 70);
                        GUInew.gc.fillText("Restbak", 410, 415);
                        Thread.sleep(20);
                    }
                }
                if (inputLine.equals("groen")) {
                    groencount++;
                    for (int i = 0; i < 455; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        GUInew.gc.strokeRect(20, 200, 200, 100);
                        GUInew.gc.strokeLine(220,225, 450, 225);
                        GUInew.gc.strokeLine(220,275, 450, 275);
                        //GUInew.gc.strokeRect(220, 225, 200, 50);
                        GUInew.gc.strokeRect(450, 215, 80, 70);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(455, 232, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + groencount, 495, 255);
                        GUInew.gc.strokeRect(425, 135, 80, 70);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(430, 152, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + roodcount, 470, 175);
                        GUInew.gc.strokeRect(425, 295, 80, 70);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(430, 312, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + blauwcount, 470, 335);
                        GUInew.gc.strokeRect(400, 55, 80, 70);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(405, 72, 36, 36);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillText("X " + geelcount, 445, 95);
                        GUInew.gc.strokeRect(400, 375, 80, 70);
                        GUInew.gc.fillText("Restbak", 410, 415);
                        Thread.sleep(20);
                    }
                }
            } catch (IOException | InterruptedException e) {
            }
        }
    }

    void ColorDisposeOn() {
        try {
            output.write(3);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: could not send input to Arduino1!");
        }
    }

    void ColorDisposeOff() {
        try {
            output.write(4);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: could not send input to Arduino1!");
        }
    }
}
