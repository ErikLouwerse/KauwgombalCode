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

public class Communicator1 implements SerialPortEventListener {
    SerialPort serialPort;

    //The port we're going to use.
    private String PORT_NAME[] = { "COM5" };

    //A BufferedReader which will be fed by a InputStreamReader converting the bytes into characters
    private BufferedReader input;

    //The output stream to the port
    private OutputStream output;

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
            Logboek.addRule(System.currentTimeMillis(), "ERROR: Could not initialize serialport or input/output streams!");
            GUInew.showError("Error with Arduino1", "Could not initialize serialport or input/output streams. Please try again.");
        }
    }

    //Handle an event on the serial port. Read the data and print it.
    @Override
    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                Logboek.addRule(System.currentTimeMillis(), "(Arduino1): " + inputLine);
            } catch (IOException e) {
                Logboek.addRule(System.currentTimeMillis(), "ERROR: could not read output from Arduino1!");
                GUInew.showError("Error with Arduino1", "Could not read output from Arduino1");
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