package javaarduino;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JCheckBox;

public class GUInew extends Application {

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
        //gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launch(args);
    }

    private GridPane root, left, right, quantities, buttonpane;
    private VBox vboxleft, vboxright;
    private Label settingstext, speedlabel, yellowballlabel, redballlabel, greenballlabel, blueballlabel, packageslabel,
            statuslabel, loglabel;
    private TextField yellowballfield, redballfield, greenballfield, blueballfield, packagesfield;
    private static TextArea logarea;
    private Line blackLine;
    private CheckBox dispose;
    private Slider slider;
    private Button savebutton, revertbutton;
    private GUIbuttonListener listener;
    private boolean OtherColorChecked = false;
    private Communicator1 Communicator1;
    private Communicator2 Communicator2;
    private Database Database;

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

        if (OtherColorChecked == true) {
            dispose = new CheckBox("Dispose unnecessary balls");
            dispose.setOnAction(listener);
        } else {
            dispose = new CheckBox("Dispose unnecessary balls");
            dispose.setOnAction(listener);
        }
        dispose.setSelected(OtherColorChecked);

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
        revertbutton = new Button("Revert changes");
        revertbutton.setOnAction(listener);

        buttonpane = new GridPane();
        buttonpane.setVgap(10);
        GridPane.setHalignment(revertbutton, HPos.RIGHT);
        ColumnConstraints butcol1 = new ColumnConstraints();
        butcol1.setPercentWidth(50);
        ColumnConstraints butcol2 = new ColumnConstraints();
        butcol2.setPercentWidth(50);
        buttonpane.getColumnConstraints().addAll(butcol1, butcol2);
        buttonpane.addColumn(0, savebutton);
        buttonpane.addColumn(1, revertbutton);

        vboxleft.getChildren().addAll(settingstext, blackLine, dispose, speedlabel, slider, quantities, buttonpane);
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

        //TODO: Log deel en button deel toevoegen
        vboxright.getChildren().addAll(statuslabel, holder, loglabel, logarea);
        right.getChildren().addAll(vboxright);
        //=============================== END LEFT =====================================

        final Scene scene = new Scene(root, 1000, 600);
        window.setScene(scene);
        window.setTitle("Kauwgomballen HMI");
        window.setMaximized(true);
        window.show();
    }

    public static TextArea getLogarea() {
        return logarea;
    }

    //============================== INTERNAL CLASSES ======================================
    private class GUIbuttonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == dispose) {
                if (OtherColorChecked == true) {
                    OtherColorChecked = false;
                    try {
                        Communicator1.ColorDisposeOff();
                    } catch (Exception e) {
                        System.out.println("Geen connectie met arduino");
                    }
                    Database.Query("UPDATE `settings` SET `Waarde`= 0 WHERE `Naam` = 'Afvoeren'");
                } else if (OtherColorChecked == false) {
                    OtherColorChecked = true;
                    try {
                        Communicator1.ColorDisposeOn();
                    } catch (Exception e) {
                        System.out.println("Geen connectie met arduino");
                    }
                    Database.Query("UPDATE `settings` SET `Waarde`= 1 WHERE `Naam` = 'Afvoeren'");
                }
            } else if (event.getSource() == savebutton) {
                //Pressed button was the save button, so get all setting values
                boolean disposevalue = dispose.isSelected();
                double speedvalue = slider.getValue();
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
                        //Dialog with warning will be showed
                        showWarning("Er is een fout opgetreden!",
                                "Een of meerdere van de ingevulde hoeveelheden is kleiner dan 0 of groter dan 99. Verbeter dit.");
                    } else {
                        try {
                            Communicator2.YellowBalls(yellowcount);
                            Communicator2.RedBalls(redcount);
                            Communicator2.GreenBalls(greencount);
                            Communicator2.Blueballs(bluecount);
                            Communicator2.QuantityPackage(quantitycount);
                        } catch (Exception e){
                            System.out.println("Geen verbinding met arduino");
                        }
                        Database.PrepQuery("INSERT INTO `transacties` (`Transactienummer`, `Geel`, `Rood`, `Groen`, `Blauw`, `Aantal pakketten`) SELECT MAX(Transactienummer)+1,?,?,?,?,? FROM transacties", yellowcount, redcount, greencount, bluecount, quantitycount);
                        Logboek.addRule(System.currentTimeMillis(), "Saving settings...");
                        Logboek.addRule(System.currentTimeMillis(), "Values are: " + disposevalue + " " + speedvalue + " " + yellowcount
                                + " " + redcount + " " + greencount + " " + bluecount + " " + quantitycount);
                        //TODO: sla op in Database
                        Logboek.addRule(System.currentTimeMillis(), "Settings saved successfully");
                        showInfo("Opgeslagen", "De instellingen zijn succesvol opgeslagen!");
                    }
                } catch (NumberFormatException e) {
                    //Dialog with warning will be showed
                    showWarning("Er is een fout opgetreden!",
                            "Een of meerdere van de instellingen was leeg of bevat niet-numerieke tekst. Verbeter dit.");
                }
            } else if (event.getSource() == revertbutton) {
                Logboek.addRule(System.currentTimeMillis(), "Reverting settings...");
                boolean dbdispose = false;
                double dbslider = 2.0;
                int dbyellow = 0, dbred = 0, dbgreen = 0, dbblue = 0, dbpackages = 0;
                //TODO: haal oude gegevens uit de Database op en stel ze terug in
                dispose.setSelected(dbdispose);
                slider.setValue(dbslider);
                yellowballfield.setText(String.valueOf(dbyellow));
                redballfield.setText(String.valueOf(dbred));
                greenballfield.setText(String.valueOf(dbgreen));
                blueballfield.setText(String.valueOf(dbblue));
                packagesfield.setText(String.valueOf(dbpackages));
                showInfo("Instellingen teruggezet", "De vorige instellingen zijn teruggezet.");
                Logboek.addRule(System.currentTimeMillis(), "Settings reverted");
            }
        }

        private void showWarning(String header, String text) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Waarschuwing!");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.showAndWait();
        }

        private void showInfo(String header, String text) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informatie");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.show();
        }
    }

    private class ResizableCanvas extends Canvas {

        public ResizableCanvas() {
            // Redraw canvas when size changes.
            widthProperty().addListener(evt -> draw());
            heightProperty().addListener(evt -> draw());
        }

        private void draw() {
            double width = getWidth();
            double height = getHeight();
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);
            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }
}
