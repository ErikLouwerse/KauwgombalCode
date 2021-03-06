package javaarduino;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class GUInew extends Application {

    private static Communicator1 Communicator1;
    private static Communicator2 Communicator2;
    private GridPane root, left, right, quantities, leftbuttonpane;
    private VBox vboxleft, vboxright;
    private Label settingstext, speedlabel, yellowballlabel, redballlabel, greenballlabel, blueballlabel, packageslabel,
            statuslabel, loglabel;
    private TextField yellowballfield, redballfield, greenballfield, blueballfield, packagesfield;
    private static TextArea logarea;
    private Line blackLine;
    private CheckBox dispose;
    private Slider slider;
    private Button savebutton, prevsettingsbutton, startbtn, stopbtn;
    private GUIbuttonListener listener;

    public static void main(String[] args) {
        Communicator1 = new Communicator1();
        Communicator2 = new Communicator2();
        Communicator1.initialize();
        Communicator2.initialize();
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

        settingstext = new Label("Settings");
        settingstext.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));

        blackLine = LineBuilder.create().startX(0).startY(0).endX(0)
                .endY(0).fill(Color.BLACK).strokeWidth(2.0f).translateY(-10).build();
        blackLine.endXProperty().bind(window.widthProperty().multiply(0.175));

        dispose = new CheckBox("Dispose unnecessary balls");
        dispose.setOnAction(listener);

        speedlabel = new Label("Speed:");

        slider = new Slider();
        slider.setMin(1);
        slider.setMax(3);
        slider.setValue(2);
        slider.setBlockIncrement(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);

        yellowballlabel = new Label("Yellow ball count");
        redballlabel = new Label("Red ball count");
        greenballlabel = new Label("Green ball count");
        blueballlabel = new Label("Blue ball count");
        packageslabel = new Label("Amount of packages");

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

        savebutton = new Button("  Save  ");
        savebutton.setFont(Font.font(null, FontWeight.BOLD, Font.getDefault().getSize()));
        savebutton.setOnAction(listener);
        //savebutton.setStyle("-fx-background-color:#5aed5a;");
        prevsettingsbutton = new Button("Previous settings");
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

        vboxleft.getChildren().addAll(settingstext, blackLine, dispose, speedlabel, slider, quantities, leftbuttonpane);
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
        //canvas.widthProperty().bind(window.widthProperty().multiply(0.8));
        //canvas.heightProperty().bind(window.heightProperty().multiply(1).subtract(50));
        holder.getChildren().add(canvas);
        holder.setStyle("-fx-background-color: white");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillOval(20, 20, 50, 50);

        loglabel = new Label("Logboek:");

        logarea = new TextArea("");
        logarea.setEditable(false);

        startbtn = new Button();
        startbtn.setText("  Start  ");
        startbtn.setOnAction(listener);

        stopbtn = new Button();
        stopbtn.setText("  Stop  ");
        stopbtn.setOnAction(listener);

        BorderPane startstopbuttons = new BorderPane();
        HBox hbButtons = new HBox();
        hbButtons.setSpacing(15);
        hbButtons.setAlignment(Pos.BOTTOM_RIGHT);
        hbButtons .getChildren().addAll(startbtn, stopbtn);
        startstopbuttons.setBottom(hbButtons);

        vboxright.getChildren().addAll(statuslabel, holder, loglabel, logarea, startstopbuttons);
        right.getChildren().addAll(vboxright);
        //=============================== END RIGHT =====================================

        final Scene scene = new Scene(root, 1000, 600);
        window.setScene(scene);
        window.setTitle("Kauwgomballen HMI");
        window.setMaximized(true);
        window.show();
    }

    static TextArea getLogarea() {
        return logarea;
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
                //Save button was pressed
                Logboek.addRule(System.currentTimeMillis(), "Saving settings...");
                boolean disposevalue = dispose.isSelected();
                int speedvalue = (int) slider.getValue();
                int yellowcount = -1, redcount = -1, greencount = -1, bluecount = -1, quantitycount = -1;
                try {
                    yellowcount = Integer.parseInt(yellowballfield.getText());
                    redcount = Integer.parseInt(redballfield.getText());
                    greencount = Integer.parseInt(greenballfield.getText());
                    bluecount = Integer.parseInt(blueballfield.getText());
                    quantitycount = Integer.parseInt(packagesfield.getText());

                    //Check if the input quantities meet the requirements: minimal 0 && maximal 99.
                    if (yellowcount < 0 || yellowcount > 99 || redcount < 0 || redcount > 99 || greencount < 0 || greencount > 99
                            || bluecount < 0 || bluecount > 99 || quantitycount < 0 || quantitycount > 99) {
                        Logboek.addRule(System.currentTimeMillis(), "WARNING: one or more of the values are invalid (<0 or >99)");
                        showWarning("Er is een fout opgetreden!",
                                "Een of meerdere van de ingevulde hoeveelheden is kleiner dan 0 of groter dan 99. Verbeter dit.");
                    } else {
                        try {
                            Logboek.addRule(System.currentTimeMillis(), "Sending quantities to Arduino2");
                            Communicator2.YellowBalls(yellowcount);
                            Communicator2.RedBalls(redcount);
                            Communicator2.GreenBalls(greencount);
                            Communicator2.Blueballs(bluecount);
                            Communicator2.QuantityPackage(quantitycount);
                            Logboek.addRule(System.currentTimeMillis(), "Quantities successfully sent to Arduino2");
                        } catch (Exception e){
                            Logboek.addRule(System.currentTimeMillis(), "ERROR: No connection with Arduino2!");
                            showError("Fout tijdens opslaan", "De instellingen konden niet op de Arduino opgeslagen worden! Probeer het opnieuw.");
                        }
                        try {
                            Logboek.addRule(System.currentTimeMillis(), "Saving settings to Database");
                            Database.PrepQuery("INSERT INTO `transacties` (`Transactienummer`, `Geel`, `Rood`, `Groen`, `Blauw`, `Aantal pakketten`, `Afvoeren`, `Snelheid`) " +
                                    "SELECT MAX(Transactienummer)+1,?,?,?,?,?,?,? FROM transacties", yellowcount, redcount, greencount, bluecount, quantitycount, disposevalue, speedvalue);
                            Logboek.addRule(System.currentTimeMillis(), "Settings successfully saved to Database");
                            showInfo("Opgeslagen", "De instellingen zijn succesvol in de Database opgeslagen!");
                        } catch (Exception e) {
                            Logboek.addRule(System.currentTimeMillis(), "ERROR: Settings can't be saved to Database!");
                            showError("Fout tijdens opslaan", "De instellingen konden niet in de Database opgeslagen worden! Probeer het opnieuw.");
                        }
                    }
                } catch (NumberFormatException e) {
                    Logboek.addRule(System.currentTimeMillis(), "WARNING: Invalid input in one of the text fields");
                    showWarning("Er is een fout opgetreden!",
                            "Een of meerdere van de instellingen was leeg of bevat niet-numerieke tekst. Verbeter dit.");
                }
            }
            else if (event.getSource() == prevsettingsbutton) {
                Logboek.addRule(System.currentTimeMillis(), "Reverting previous settings...");
                boolean dbdispose = false;
                double dbspeed = 2.0;
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
                dbspeed = dbvalues.get(6);
                dispose.setSelected(dbdispose);
                slider.setValue(dbspeed);
                yellowballfield.setText(String.valueOf(dbyellow));
                redballfield.setText(String.valueOf(dbred));
                greenballfield.setText(String.valueOf(dbgreen));
                blueballfield.setText(String.valueOf(dbblue));
                packagesfield.setText(String.valueOf(dbpackages));
                Logboek.addRule(System.currentTimeMillis(), "Previous settings reverted");
                showInfo("Instellingen teruggezet", "De vorige instellingen zijn teruggezet. Vergeet niet om nog op Save te klikken!");
            }
            else if (event.getSource() == startbtn) {
                Logboek.addRule(System.currentTimeMillis(), "Start button pressed");
            }
            else if (event.getSource() == stopbtn) {
                Logboek.addRule(System.currentTimeMillis(), "Stop button pressed");
            }
        }
    }
}
