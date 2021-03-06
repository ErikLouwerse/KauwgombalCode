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

    static int blauwcount = 0;
    static int geelcount = 0;
    static int groencount = 0;
    static int roodcount = 0;
    static int bakblauwcount = 0;
    static int bakgeelcount = 0;
    static int bakgroencount = 0;
    static int bakroodcount = 0;
    static int pakjes = 0;
    private int r = 200;
    static int baan1 = 450;
    static int baan2 = 225;
    static int baan3 = 275;
    static boolean animatie = false;
    static boolean connection = false;

    SerialPort serialPort;

    //Dit is de poort van de ardiuno
    private String PORT_NAME[] = {"COM9"};

    //Dit leest de gegevens van de arduino
    BufferedReader input;

    //Dit stuurt gegevens naar de arduino
    OutputStream output;

    //Wachttijd voordat de COM poort geopened is
    private int TIME_OUT = 2000;

    //Serial.begin aantal van Arduino
    private int DATA_RATE = 9600;

    //Starten met connectie maken
    void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //Kijken of COM poort gevonden kan worden
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAME) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }

        //Error geven als COM poort niet gevonden is
        if (portId == null) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: Could not find COM port for Arduino1!");
            return;
        }

        try {
            //serial port openen
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            //set port parameters
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            //open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            //event listeners toevoegen
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: Niet gelukt om serialport of input/output streams van Arduino1 te initializeren!");
            GUInew.showError("Error met Arduino1", "niet gelukt om serialport of input/output streams te initializeren.");
        }
    }

    static void drawMachine() {
        try {
            //Kleuren sorteermachine
            GUInew.gc.strokeRect(20, 200, 200, 100);

            //Groene bakje, bal en counter
            GUInew.gc.strokeRect(450, 215, 85, 70);
            GUInew.gc.setFill(Color.GREEN);
            GUInew.gc.fillOval(455, 232, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + groencount, 495, 255);

            //Rode bakje, bal en counter
            GUInew.gc.strokeRect(425, 135, 85, 70);
            GUInew.gc.setFill(Color.RED);
            GUInew.gc.fillOval(430, 152, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + roodcount, 470, 175);

            //Blauwe bakje, bal en counter
            GUInew.gc.strokeRect(425, 295, 85, 70);
            GUInew.gc.setFill(Color.BLUE);
            GUInew.gc.fillOval(430, 312, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + blauwcount, 470, 335);

            //Gele bakje, bal en counter
            GUInew.gc.strokeRect(400, 55, 85, 70);
            GUInew.gc.setFill(Color.YELLOW);
            GUInew.gc.fillOval(405, 72, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + geelcount, 445, 95);

            //Restbakje
            GUInew.gc.strokeRect(400, 375, 80, 70);
            GUInew.gc.fillText("Restbak", 410, 415);

            //Trechter
            GUInew.gc.strokeLine(550, 55, 800, 185);
            GUInew.gc.strokeLine(550, 365, 800, 235);
            GUInew.gc.strokeLine(800, 185, 870, 185);
            GUInew.gc.strokeLine(800, 235, 870, 235);

            //Eindbakje
            GUInew.gc.strokeRect(900, 150, 200, 120);
            GUInew.gc.setFill(Color.GREEN);
            GUInew.gc.fillOval(925, 215, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + bakgroencount, 965, 240);
            GUInew.gc.setFill(Color.RED);
            GUInew.gc.fillOval(1005, 165, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + bakroodcount, 1045, 190);
            GUInew.gc.setFill(Color.BLUE);
            GUInew.gc.fillOval(1005, 215, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + bakblauwcount, 1045, 240);
            GUInew.gc.setFill(Color.YELLOW);
            GUInew.gc.fillOval(925, 165, 36, 36);
            GUInew.gc.setFill(Color.BLACK);
            GUInew.gc.fillText("X " + bakgeelcount, 965, 190);

            //Pakketjes klaar teller
            if (pakjes > 0) {
                GUInew.gc.fillText("Aantal pakketjes klaar:  " + pakjes, 1200, 210);
            } else {
                GUInew.gc.fillText("Aantal pakketjes klaar: 0", 1200, 210);
            }

            Thread.sleep(20);
        } catch (InterruptedException ex) {
        }
    }

    //Gegevens van Arduino uitlezen en er iets mee doen
    @Override
    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                
                //Succesvol connectie gemaakt
                if (inputLine.equals("200")) {
                    Logboek.addRule(System.currentTimeMillis(), "Succesvol connectie gemaakt met machine 1");
                    connection = true;
                    try {
                        output.write(geelcount + 100);
                        output.write(roodcount + 100);
                        output.write(groencount + 100);
                        output.write(blauwcount + 100);
                        output.write(254);
                    } catch (IOException ex) {
                    }
                }
                
                //Gele bal gedetecteerd
                else if (inputLine.equals("204")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 heeft een gele kauwgombal gedetecteerd");
                    if (Communicator2.connection && Communicator2.output != null) {
                        Communicator2.output.write(209);
                    }
                    //animatie wordt op true gezet zodat de tweede animatie weet dat deze animatie bezig is
                    animatie = true;
                    baan1 = 400;
                    baan2 = 70;
                    baan3 = 120;
                    //animatie loop voor eerste stuk
                    for (int i = 0; i < 200; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 400, 70);
                        GUInew.gc.strokeLine(220, 275, 400, 120);
                        drawMachine();
                    }
                    //animatie loop voor stuk over baan
                    for (double i = 0; i < 160; i = i + 3.3) {
                        r = r + 4;
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.YELLOW);
                        GUInew.gc.fillOval(r + 4, 232 - i, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 400, 70);
                        GUInew.gc.strokeLine(220, 275, 400, 120);
                        drawMachine();
                    }
                    //aantal ballen van deze kleur gaat met 1 omhoog
                    Database.UpdateQuery("UPDATE aantal_ballen SET Aantal = Aantal+1 WHERE Naam = 'Geel'");
                    geelcount++;
                    Communicator2.geelBak++;
                    r = 200;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    drawMachine();
                    GUInew.gc.strokeLine(220, 225, 400, 70);
                    GUInew.gc.strokeLine(220, 275, 400, 120);
                    animatie = false;
                }
                //Rode bal gedectecteerd
                else if (inputLine.equals("205")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 heeft een rode kauwgombal gedetecteerd");
                    if (Communicator2.connection && Communicator2.output != null) {
                        Communicator2.output.write(210);
                    }
                    //animatie wordt op true gezet zodat de tweede animatie weet dat deze animatie bezig is
                    animatie = true;
                    baan1 = 425;
                    baan2 = 145;
                    baan3 = 195;
                    //animatie loop voor eerste stuk
                    for (int i = 0; i < 200; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 425, 145);
                        GUInew.gc.strokeLine(220, 275, 425, 195);
                        drawMachine();
                    }
                    //animatie loop voor stuk over baan
                    for (double i = 0; i < 80; i = i + 1.5) {
                        r = r + 4;
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.RED);
                        GUInew.gc.fillOval(r + 4, 232 - i, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 425, 145);
                        GUInew.gc.strokeLine(220, 275, 425, 195);
                        drawMachine();
                    }
                    Database.UpdateQuery("UPDATE aantal_ballen SET Aantal = Aantal+1 WHERE Naam = 'Rood'");
                    roodcount++;
                    Communicator2.roodBak++;
                    r = 200;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    GUInew.gc.strokeLine(220, 225, 425, 145);
                    GUInew.gc.strokeLine(220, 275, 425, 195);
                    drawMachine();
                    animatie = false;
                }
                //Groene bal gedectecteerd
                else if (inputLine.equals("206")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 heeft een groene bal gedetecteerd");
                    if (Communicator2.connection && Communicator2.output != null) {
                        Communicator2.output.write(211);
                    }
                    animatie = true;
                    baan1 = 450;
                    baan2 = 225;
                    baan3 = 275;
                    for (int i = 0; i < 455; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.GREEN);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 450, 225);
                        GUInew.gc.strokeLine(220, 275, 450, 275);
                        drawMachine();
                    }
                    Database.UpdateQuery("UPDATE aantal_ballen SET Aantal = Aantal+1 WHERE Naam = 'Groen'");
                    groencount++;
                    Communicator2.groenBak++;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    GUInew.gc.strokeLine(220, 225, 450, 225);
                    GUInew.gc.strokeLine(220, 275, 450, 275);
                    drawMachine();
                    animatie = false;
                }
                else if (inputLine.equals("207")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 detected a blue ball");
                    if (Communicator2.connection && Communicator2.output != null) {
                        Communicator2.output.write(212);
                    }
                    animatie = true;
                    baan1 = 425;
                    baan2 = 305;
                    baan3 = 355;
                    for (int i = 0; i < 200; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 425, 305);
                        GUInew.gc.strokeLine(220, 275, 425, 355);
                        drawMachine();
                    }
                    for (double i = 0; i < 80; i = i + 1.5) {
                        r = r + 4;
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.BLUE);
                        GUInew.gc.fillOval(r + 4, 232 + i, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 425, 305);
                        GUInew.gc.strokeLine(220, 275, 425, 355);
                        drawMachine();
                    }
                    r = 200;
                    Database.UpdateQuery("UPDATE aantal_ballen SET Aantal = Aantal+1 WHERE Naam = 'Blauw'");
                    Communicator2.blauwBak++;
                    blauwcount++;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    GUInew.gc.strokeLine(220, 225, 425, 305);
                    GUInew.gc.strokeLine(220, 275, 425, 355);
                    drawMachine();
                    animatie = false;
                }
                else if (inputLine.equals("208")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 detected a rest ball");
                    animatie = true;
                    baan1 = 400;
                    baan2 = 385;
                    baan3 = 435;
                    for (int i = 0; i < 200; i = i + 4) {
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillOval(i, 232, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 400, 385);
                        GUInew.gc.strokeLine(220, 275, 400, 435);
                        drawMachine();
                    }
                    for (double i = 0; i < 160; i = i + 3.3) {
                        r = r + 4;
                        GUInew.gc.clearRect(0, 0, 5000, 5000);
                        GUInew.gc.setFill(Color.BLACK);
                        GUInew.gc.fillOval(r + 4, 232 + i, 36, 36);
                        //als communicator 2 ook een animatie aan het doen is zal die gestopt worden, deze animatie wordt nu hier getekend
                        if (Communicator2.animatie) {
                            GUInew.gc.setFill(Communicator2.kleur);
                            GUInew.gc.fillOval(Communicator2.bal1, Communicator2.bal2, 36, 36);
                        }
                        GUInew.gc.strokeLine(220, 225, 400, 385);
                        GUInew.gc.strokeLine(220, 275, 400, 435);
                        drawMachine();
                    }
                    r = 200;
                    GUInew.gc.clearRect(0, 0, 5000, 5000);
                    GUInew.gc.strokeLine(220, 225, 400, 385);
                    GUInew.gc.strokeLine(220, 275, 400, 435);
                    drawMachine();
                    animatie = false;
                }
                else if(inputLine.equals("240")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 started successfully");
                }
                else if(inputLine.equals("241")) {
                    Logboek.addRule(System.currentTimeMillis(), "Machine 1 stopped successfully");
                }
                else {
                    Logboek.addRule(System.currentTimeMillis(), "(Arduino1): " + inputLine);
                }
            } catch (Exception e) {
            }
        }
    }

    void ColorDisposeOn() {
        try {
            output.write(203);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void ColorDisposeOff() {
        try {
            output.write(202);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void YellowBalls(int t) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void RedBalls(int t) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void GreenBalls(int t) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void BlueBalls(int t) {
        try {
            output.write(t);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }

    void QuantityPackage(int i) {
        try {
            output.write(i);
        } catch (IOException e) {
            Logboek.addRule(System.currentTimeMillis(), "ERROR: kan input niet naar Arduino1 sturen!");
        }
    }
}
