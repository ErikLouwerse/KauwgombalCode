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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

public class Communicator2 implements SerialPortEventListener {

    static boolean connection = false;
    static double bal1 = 0;
    static double bal2 = 0;

    static Color kleur;

    static boolean animatie = false;

    static SerialPort serialPort;

    //The port we're going to use.
    private String PORT_NAME[] = {"COM6"};

    //A BufferedReader which will be fed by a InputStreamReader converting the bytes into characters
    BufferedReader input;

    //The output stream to the port
    static OutputStream output;

    //Milliseconds to block while waiting for port open
    private int TIME_OUT = 2000;

    //Default bits per second for COM port.
    private int DATA_RATE = 9600;

    static int geelBak;
    static int roodBak;
    static int groenBak;
    static int blauwBak;

    static void drawSetup() {
        GUInew.gc.strokeLine(220, 225, Communicator1.baan1, Communicator1.baan2);
        GUInew.gc.strokeLine(220, 275, Communicator1.baan1, Communicator1.baan3);
        Communicator1.drawMachine();
    }

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
            System.out.println("ERROR: COM poort van Arduino 2 niet gevonden!");
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
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om serialport of input/output streams te initializeren!");
        }
    }

    //Handle an event on the serial port. Read the data and print it.
    @Override
    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                Logboek.addRule(System.currentTimeMillis(), "(Arduino2): " + inputLine);
                if (inputLine.equals("201")) {
                    connection = true;
                    output.write(Communicator1.geelcount + 100);
                    output.write(Communicator1.roodcount + 100);
                    output.write(Communicator1.groencount + 100);
                    output.write(Communicator1.blauwcount + 100);
                }
                if (inputLine.equals("Dropping yellow ball")) {
                    kleur = Color.YELLOW;
                    animatie = true;
                    Communicator1.geelcount = Communicator1.geelcount - 1;
                    for (int i = 0; i < 392; i = i + 4) {
                        if (Communicator1.animatie == false) {
                            GUInew.gc.clearRect(0, 0, 5000, 5000);
                            GUInew.gc.setFill(Color.YELLOW);
                        }
                        if (i <= 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(480 + i, 72 + i / 2.5, 36, 36);
                            }
                            bal1 = 480 + i;
                            bal2 = 72 + i / 2.5;
                        } else if (i > 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(480 + i, 188.8, 36, 36);
                            }
                            bal1 = 480 + i;
                        }
                        if (Communicator1.animatie == false) {
                            drawSetup();
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                    Communicator1.bakgeelcount = Communicator1.bakgeelcount + 1;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                    animatie = false;
                }
                if (inputLine.equals("Dropping red ball")) {
                    kleur = Color.RED;
                    animatie = true;
                    Communicator1.roodcount = Communicator1.roodcount - 1;
                    for (int i = 0; i < 392; i = i + 4) {
                        if (Communicator1.animatie == false) {
                            GUInew.gc.clearRect(0, 0, 5000, 5000);
                            GUInew.gc.setFill(Color.RED);
                        }
                        if (i <= 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(505 + i, 152 + i / 7.5, 36, 36);
                            }
                            bal1 = 505 + i;
                            bal2 = 152 + i / 7.5;
                        } else if (i > 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(505 + i, 190.93, 36, 36);
                            }
                            bal1 = 505 + i;
                        }
                        if (Communicator1.animatie == false) {
                            drawSetup();
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                    Communicator1.bakroodcount = Communicator1.bakroodcount + 1;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                    animatie = false;
                }
                if (inputLine.equals("Dropping green ball")) {
                    kleur = Color.GREEN;
                    animatie = true;
                    Communicator1.groencount = Communicator1.groencount - 1;
                    for (int i = 0; i < 352; i = i + 4) {
                        if (Communicator1.animatie == false) {
                            GUInew.gc.clearRect(0, 0, 5000, 5000);
                            GUInew.gc.setFill(Color.GREEN);
                        }
                        if (i <= 268) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(530 + i, 232 - i / 6.5, 36, 36);
                            }
                            bal1 = 530 + i;
                            bal2 = 232 - i / 6.5;
                        } else if (i > 268) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(530 + i, 190.77, 36, 36);
                            }
                            bal1 = 530 + i;
                        }
                        if (Communicator1.animatie == false) {
                            drawSetup();
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }

                    Communicator1.bakgroencount = Communicator1.bakgroencount + 1;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                    animatie = false;
                }
                if (inputLine.equals("Dropping blue ball")) {
                    kleur = Color.BLUE;
                    animatie = true;
                    Communicator1.blauwcount = Communicator1.blauwcount - 1;
                    for (int i = 0; i < 392; i = i + 4) {
                        if (Communicator1.animatie == false) {
                            GUInew.gc.clearRect(0, 0, 5000, 5000);
                            GUInew.gc.setFill(Color.BLUE);
                        }
                        if (i <= 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(505 + i, 295 - i / 2.8, 36, 36);
                            }
                            bal1 = 505 + i;
                            bal2 = 295 - i / 2.8;
                        } else if (i > 292) {
                            if (Communicator1.animatie == false) {
                                GUInew.gc.fillOval(505 + i, 190.71, 36, 36);
                            }
                            bal1 = 505 + i;
                        }
                        if (Communicator1.animatie == false) {
                            drawSetup();
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                    Communicator1.bakblauwcount = Communicator1.bakblauwcount + 1;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                    animatie = false;
                }
                if (inputLine.equals("Pakje klaar!")) {
                    Communicator1.pakjes = Communicator1.pakjes + 1;
                    Communicator1.bakgroencount = 0;
                    Communicator1.bakgeelcount = 0;
                    Communicator1.bakblauwcount = 0;
                    Communicator1.bakroodcount = 0;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                }
                if (inputLine.equals("Bestelling is klaar!")) {
                    Communicator1.pakjes = 0;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawSetup();
                } else {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                    }
                }

            } catch (Exception e) {
            }
        }
    }

    void YellowBalls(int t
    ) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om input naar Arduino2 te sturen!!");
        }
    }

    void RedBalls(int t
    ) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om input naar Arduino2 te sturen!");
        }
    }

    void GreenBalls(int t
    ) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om input naar Arduino2 te sturen!");
        }
    }

    void BlueBalls(int t
    ) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om input naar Arduino2 te sturen!");
        }
    }

    void QuantityPackage(int i
    ) {
        try {
            output.write(i);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om input naar Arduino2 te sturen!");
        }
    }
}
