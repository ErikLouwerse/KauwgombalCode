package javaarduino;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUInew extends Application {

    static GraphicsContext gc;

    static boolean stop;
    private static Communicator1 Communicator1;
    private static Communicator2 Communicator2;
    private GridPane root, left, right, quantities, leftbuttonpane;
    private VBox vboxleft, vboxright;
    private Label settingstext, yellowballlabel, redballlabel, greenballlabel, blueballlabel, packageslabel,
            statuslabel, loglabel;
    private TextField yellowballfield, redballfield, greenballfield, blueballfield, packagesfield;
    private static TextArea logarea;
    private Line blackLine;
    private CheckBox dispose;
    private Button savebutton, prevsettingsbutton, logbtn, startmachinebtn, startorderbtn, stopbtn, resetbtn;
    private GUIbuttonListener listener;
    private int yellowcount = -1, redcount = -1, greencount = -1, bluecount = -1, quantitycount = -1;

    public static void main(String[] args) {
        Communicator1 = new Communicator1();
        Communicator2 = new Communicator2();
        launch(args);
    }

    @Override
    public void start(final Stage window) {
        listener = new GUIbuttonListener();
        //=============== START Create root, left and right ================
        root = new GridPane();
        root.setHgap(10);

        left = new GridPane();
        left.setStyle("-fx-border-color: black; -fx-border-insets: 0; -fx-border-width: 1;");
        left.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        left.prefWidthProperty().bind(window.widthProperty().multiply(0.20));
        root.add(left, 0, 0);

        right = new GridPane();
        //right.setVgap(10);
        right.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        right.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        root.add(right, 1, 0);
        //================= END Create root, left and right ================

        //=============================== START LEFT ==================================
        vboxleft = new VBox(25);
        vboxleft.setPadding(new Insets(10));
        vboxleft.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        vboxleft.prefWidthProperty().bind(window.widthProperty().multiply(1.00));

        settingstext = new Label("Instellingen");
        settingstext.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));

        blackLine = LineBuilder.create().startX(0).startY(0).endX(0)
                .endY(0).fill(Color.BLACK).strokeWidth(2.0f).translateY(-10).build();
        blackLine.endXProperty().bind(window.widthProperty().multiply(0.175));

        dispose = new CheckBox("Onnodige ballen apart leggen");
        dispose.setOnAction(listener);

        yellowballlabel = new Label("Aantal gele ballen:");
        redballlabel = new Label("Aantal rode ballen:");
        greenballlabel = new Label("Aantal groene ballen:");
        blueballlabel = new Label("Aantal blauwe ballen:");
        packageslabel = new Label("Aantal pakketjes:");

        yellowballfield = new TextField();
        redballfield = new TextField();
        greenballfield = new TextField();
        blueballfield = new TextField();
        packagesfield = new TextField();

        quantities = new GridPane();
        quantities.setVgap(15);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(80);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        quantities.getColumnConstraints().addAll(col1, col2);
        quantities.addColumn(0, yellowballlabel, redballlabel, greenballlabel, blueballlabel, packageslabel);
        quantities.addColumn(1, yellowballfield, redballfield, greenballfield, blueballfield, packagesfield);

        savebutton = new Button(" Opslaan ");
        savebutton.setFont(Font.font(null, FontWeight.BOLD, Font.getDefault().getSize()));
        savebutton.setOnAction(listener);
        //savebutton.setStyle("-fx-background-color:#5aed5a;");
        prevsettingsbutton = new Button("Vorige instellingen");
        prevsettingsbutton.setOnAction(listener);

        leftbuttonpane = new GridPane();
        leftbuttonpane.setVgap(10);
        GridPane.setHalignment(prevsettingsbutton, HPos.RIGHT);
        ColumnConstraints butcol1 = new ColumnConstraints();
        butcol1.setPercentWidth(50);
        ColumnConstraints butcol2 = new ColumnConstraints();
        butcol2.setPercentWidth(50);
        leftbuttonpane.getColumnConstraints().addAll(butcol1, butcol2);
        leftbuttonpane.addColumn(0, savebutton);
        leftbuttonpane.addColumn(1, prevsettingsbutton);

        vboxleft.getChildren().addAll(settingstext, blackLine, dispose, quantities, leftbuttonpane);
        left.getChildren().addAll(vboxleft);
        //=============================== END LEFT =====================================

        //=============================== START RIGHT ==================================
        vboxright = new VBox(10);
        vboxright.setPadding(new Insets(0));
        //vboxright.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        //vboxright.prefWidthProperty().bind(window.widthProperty().multiply(1.00));

        statuslabel = new Label("Huidige status:");

        Canvas canvas = new Canvas(1500, 500);
        StackPane holder = new StackPane();
        holder.getChildren().add(canvas);
        holder.setStyle("-fx-background-color: white");
        gc = canvas.getGraphicsContext2D();
        loglabel = new Label("Logboek:");

        logarea = new TextArea("");
        logarea.setEditable(false);

        logbtn = new Button();
        logbtn.setText("Toon logboek");
        logbtn.setOnAction(listener);

        startmachinebtn = new Button();
        startmachinebtn.setText("  Start machines  ");
        startmachinebtn.setOnAction(listener);

        startorderbtn = new Button();
        startorderbtn.setText("  Start bestelling  ");
        startorderbtn.setDisable(true);
        startorderbtn.setOnAction(listener);

        resetbtn = new Button();
        resetbtn.setText("  Reset Ballen  ");
        resetbtn.setOnAction(listener);

        stopbtn = new Button();
        stopbtn.setText("  Stop machine 2  ");
        stopbtn.setDisable(true);
        stopbtn.setOnAction(listener);

        BorderPane controlbuttons = new BorderPane();
        HBox startstopbuttons = new HBox();
        startstopbuttons.setSpacing(15);
        startstopbuttons.setAlignment(Pos.BOTTOM_RIGHT);
        startstopbuttons.getChildren().addAll(startmachinebtn, startorderbtn, resetbtn, stopbtn);
        HBox logbookbutton = new HBox();
        logbookbutton.setAlignment(Pos.BOTTOM_LEFT);
        logbookbutton.getChildren().addAll(logbtn);
        controlbuttons.setLeft(startstopbuttons);
        controlbuttons.setRight(logbookbutton);

        vboxright.getChildren().addAll(statuslabel, holder, loglabel, logarea, controlbuttons);
        right.getChildren().addAll(vboxright);
        //=============================== END RIGHT =====================================

        final Scene scene = new Scene(root, 1000, 600);
        window.setScene(scene);
        window.setTitle("Kauwgomballen HMI");
        window.setMaximized(true);
        window.show();
        Database.Query("SELECT * FROM `aantal_ballen`");
        drawSetup();
    }

    @Override
    public void stop() {
        if (Database.getConnection() != null) {
            try {
                Database.getConnection().close();
            } catch (SQLException e) {
                System.out.println("Error met sluiten van database connectie");
            }
        }
        try {
            if (Communicator1.input != null) {
                Communicator1.input.close();
            }
            if (Communicator1.output != null) {
                Communicator1.output.close();
            }
            if (Communicator1.serialPort != null) {
                Communicator1.serialPort.close();
            }
            if (Communicator2.input != null) {
                Communicator2.input.close();
            }
            if (Communicator2.output != null) {
                Communicator2.output.close();
            }
            if (Communicator2.serialPort != null) {
                Communicator2.serialPort.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR met sluiten van communicators");
        }
    }

    static TextArea getLogarea() {
        return logarea;
    }

    private void drawSetup() {
        Communicator1.drawMachine();
        gc.strokeLine(220, 225, 450, 225);
        gc.strokeLine(220, 275, 450, 275);
    }

    static void showInfo(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informatie");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.show();
    }

    static void showWarning(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Waarschuwing!");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    static void showError(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FOUT!");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    //============================== EVENT LISTENER CLASS ======================================
    private class GUIbuttonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == savebutton) {
                Logboek.addRule(System.currentTimeMillis(), "Settings aan het opslaan...");
                saveSettings();
            } else if (event.getSource() == prevsettingsbutton) {
                Logboek.addRule(System.currentTimeMillis(), "Vorige instellingen aan het terug zetten...");
                revertSettings();
                Logboek.addRule(System.currentTimeMillis(), "Vorige instellingen zijn terug gezet!");
                showInfo("Instellingen teruggezet", "De vorige instellingen zijn teruggezet. Vergeet niet om nog op Save te klikken!");
            } else if (event.getSource() == startmachinebtn) {
                if (Communicator1.connection == true && Communicator2.connection == true) {
                    try {
                        Communicator1.input.close();
                        Communicator1.output.close();
                        Communicator1.serialPort.close();
                        Communicator2.input.close();
                        Communicator2.output.close();
                        Communicator2.serialPort.close();
                    } catch (IOException ex) {
                        Logger.getLogger(GUInew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Communicator1.initialize();
                Communicator2.initialize();
                stop = false;
                stopbtn.setDisable(false);
                startorderbtn.setDisable(false);
                startmachinebtn.setDisable(true);
                Logboek.addRule(System.currentTimeMillis(), "Start knop ingedrukt");
                try {
                    Thread.sleep(2000);
                    Communicator1.output.write(200);
                    Communicator2.output.write(201);
                } catch (IOException ex) {
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUInew.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event.getSource() == stopbtn) {
                try {
                    Communicator1.output.write(255);
                    Communicator2.output.write(255);
                    stop = true;
                    stopbtn.setDisable(true);
                    startmachinebtn.setDisable(false);
                } catch (IOException ex) {
                    Logger.getLogger(GUInew.class.getName()).log(Level.SEVERE, null, ex);
                }
                Logboek.addRule(System.currentTimeMillis(), "Stop knop ingedrukt");
            } else if (event.getSource() == startorderbtn) {
                boolean disposevalue = dispose.isSelected();
                if (yellowcount > -1 && redcount > -1 && greencount > -1 && bluecount > -1 && quantitycount > -1) {
                    try {
                        Logboek.addRule(System.currentTimeMillis(), "Sending quantities to Arduino2");
                        Communicator2.YellowBalls(yellowcount);
                        Communicator2.RedBalls(redcount);
                        Communicator2.GreenBalls(greencount);
                        Communicator2.BlueBalls(bluecount);
                        Communicator2.QuantityPackage(quantitycount);
                        if (disposevalue == false) {
                            Communicator1.ColorDisposeOff();
                        }
                        if (disposevalue == true) {
                            Communicator1.ColorDisposeOn();
                        }
                        Communicator1.output.write(yellowcount);
                        Communicator1.output.write(redcount);
                        Communicator1.output.write(greencount);
                        Communicator1.output.write(bluecount);
                        Communicator1.output.write(quantitycount);

                        Logboek.addRule(System.currentTimeMillis(), "Waarden gestuurd naar Arduino2");
                    } catch (Exception e) {
                        Logboek.addRule(System.currentTimeMillis(), "ERROR: geen connectie met Arduino2!");
                        showError("Fout tijdens opslaan", "De instellingen konden niet op de Arduino opgeslagen worden! Probeer het opnieuw.");
                    }
                    try {
                        Logboek.addRule(System.currentTimeMillis(), "Settings opslaan in Database");
                        Database.PrepQuery("INSERT INTO `transacties` (`Transactienummer`, `Geel`, `Rood`, `Groen`, `Blauw`, `Aantal pakketten`, `Afvoeren`, `Snelheid`) "
                                + "SELECT MAX(Transactienummer)+1,?,?,?,?,?,?,? FROM transacties", yellowcount, redcount, greencount, bluecount, quantitycount, disposevalue);
                        Logboek.addRule(System.currentTimeMillis(), "Settings successfully saved to Database");
                        showInfo("Opgeslagen", "De instellingen zijn succesvol in de Database opgeslagen!");
                    } catch (Exception e) {
                        Logboek.addRule(System.currentTimeMillis(), "ERROR: niet gelukt om instellingen op te slaan in database!");
                        showError("Fout tijdens opslaan", "De instellingen konden niet in de Database opgeslagen worden! Probeer het opnieuw.");
                    }
                } else {
                    showError("Kan bestelling niet starten", "Niet gelukt om bestelling te starten, sla eerst een bestelling op");
                }

            } else if (event.getSource() == resetbtn) {
                Database.UpdateQuery("Update aantal_ballen set Aantal = 0");
                setGeelcount(0);
                setGeelBak(0);
                setRoodcount(0);
                setRoodBak(0);
                setGroencount(0);
                setGroenBak(0);
                setBlauwcount(0);
                setBlauwBak(0);
                if (Communicator2.connection == true) {
                    try {
                        Communicator2.output.write(214);
                    } catch (IOException ex) {
                        Logger.getLogger(GUInew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (event.getSource() == logbtn) {
                showLogbookDialog();
            }
        }

        private void saveSettings() {
            try {
                yellowcount = Integer.parseInt(yellowballfield.getText());
                redcount = Integer.parseInt(redballfield.getText());
                greencount = Integer.parseInt(greenballfield.getText());
                bluecount = Integer.parseInt(blueballfield.getText());
                quantitycount = Integer.parseInt(packagesfield.getText());

                //Check if the input quantities meet the requirements: minimal 0 && maximal 99.
                if (yellowcount < 0 || yellowcount > 99 || redcount < 0 || redcount > 99 || greencount < 0 || greencount > 99
                        || bluecount < 0 || bluecount > 99 || quantitycount < 0 || quantitycount > 99) {
                    Logboek.addRule(System.currentTimeMillis(), "WAARSCHUWING: een of meer van de ingevulde waarden zijn niet geldig (<0 or >99)");
                    showWarning("Er is een fout opgetreden!",
                            "Een of meerdere van de ingevulde hoeveelheden is kleiner dan 0 of groter dan 99. Verbeter dit.");
                } else {
                }
            } catch (NumberFormatException e) {
                Logboek.addRule(System.currentTimeMillis(), "WARNING: Ongeldige input in een van de tekst velden");
                showWarning("Er is een fout opgetreden!",
                        "Een of meerdere van de instellingen was leeg of bevat niet-numerieke tekst. Verbeter dit.");
            }
        }

        private void revertSettings() {
            boolean dbdispose = false;
            int dbyellow = 0, dbred = 0, dbgreen = 0, dbblue = 0, dbpackages = 0;
            List<Integer> dbvalues = Database.QueryPrevSettings();
            dbyellow = dbvalues.get(0);
            dbred = dbvalues.get(1);
            dbgreen = dbvalues.get(2);
            dbblue = dbvalues.get(3);
            dbpackages = dbvalues.get(4);
            if (dbvalues.get(5) == 1) {
                dbdispose = true;
            }
            dispose.setSelected(dbdispose);
            yellowballfield.setText(String.valueOf(dbyellow));
            redballfield.setText(String.valueOf(dbred));
            greenballfield.setText(String.valueOf(dbgreen));
            blueballfield.setText(String.valueOf(dbblue));
            packagesfield.setText(String.valueOf(dbpackages));
        }

        private void showLogbookDialog() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logboek inhoud");
            alert.setHeaderText("Het logboek gesorteerd van nieuw naar oud");
            alert.setGraphic(null);

            Label amountlabel = new Label("Aantal regels:");
            TextField amountfield = new TextField("50");
            Button apply = new Button("Toepassen");
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(amountlabel, amountfield, apply);

            String dblogrules = Logboek.getRules(50);
            TextArea textArea = new TextArea(dblogrules);
            textArea.setPrefWidth(1000);
            textArea.setPrefHeight(500);
            textArea.setEditable(false);
            textArea.setWrapText(false);
            apply.setOnAction(event -> {
                try {
                    textArea.setText(Logboek.getRules(Integer.parseInt(amountfield.getText())));
                } catch (NumberFormatException e) {
                    showWarning("Er is een fout opgetreden!", "De invoer was leeg of bevat niet-numerieke tekst. Verbeter dit.");
                }
            });

            Button savelogbtn = new Button("Opslaan naar bestand");
            savelogbtn.setOnAction(event -> {
                try {
                    PrintWriter out = new PrintWriter("Logboek.txt");
                    out.println(Logboek.getRules(Integer.parseInt(amountfield.getText())));
                    out.close();
                    showInfo("Succesvol opgeslagen", "Het logboek met " + amountfield.getText() + " regels is succesvol naar "
                            + "het bestand 'Logboek.txt' opgeslagen. U kunt dit bestand in de project map vinden.");
                } catch (FileNotFoundException e) {
                    showWarning("Fout tijdens opslaan!", "Kon het logboek niet in een bestand opslaan. Probeer het later nog eens.");
                }
            });
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(hbox, textArea, savelogbtn);

            DialogPane pane = alert.getDialogPane();
            pane.setContent(vbox);
            alert.show();
        }
    }

    private static void setGeelBak(int geelBak) {
        Communicator2.geelBak = geelBak;
    }

    private static void setRoodBak(int roodBak) {
        Communicator2.roodBak = roodBak;
    }

    private static void setGroenBak(int groenBak) {
        Communicator2.groenBak = groenBak;
    }

    private static void setBlauwBak(int blauwBak) {
        Communicator2.blauwBak = blauwBak;
    }

    public static void setBlauwcount(int blauwcount) {
        Communicator1.blauwcount = blauwcount;
    }

    public static void setGeelcount(int geelcount) {
        Communicator1.geelcount = geelcount;
    }

    public static void setGroencount(int groencount) {
        Communicator1.groencount = groencount;
    }

    public static void setRoodcount(int roodcount) {
        Communicator1.roodcount = roodcount;
    }
}
