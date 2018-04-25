package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUInew extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage window) {
        //=============== START Create root, left and right ================
        final GridPane root = new GridPane();
        root.setHgap(10);

        final GridPane left = new GridPane();
        left.setStyle("-fx-border-color: black; -fx-border-insets: 0; -fx-border-width: 1;");
        left.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        left.prefWidthProperty().bind(window.widthProperty().multiply(0.20));
        root.add(left,0,0);

        final GridPane right = new GridPane();
        //right.setVgap(10);
        right.prefHeightProperty().bind(window.heightProperty().multiply(1.00));
        right.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        root.add(right,1,0);
        //================= END Create root, left and right ================

        //=============================== START LEFT ==================================
        final VBox vbox = new VBox(25);
        vbox.setPadding(new Insets(10));
        vbox.prefWidthProperty().bind(window.widthProperty().multiply(1.00));
        vbox.prefHeightProperty().bind(window.heightProperty().multiply(1.00));

        final Label settingstext = new Label("Settings");
        settingstext.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));

        final Line blackLine = LineBuilder.create().startX(0).startY(0).endX(0)
                .endY(0).fill(Color.BLACK).strokeWidth(2.0f).translateY(-10).build();
        blackLine.endXProperty().bind(window.widthProperty().multiply(0.175));

        final CheckBox checkbox1 = new CheckBox("Dispose unnecessary balls");

        final Label speedlabel = new Label("Speed:");

        final Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(3);
        slider.setValue(2);
        slider.setBlockIncrement(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);

        final Label yellowballlabel = new Label("Yellow ball count");
        final Label redballlabel = new Label("Red ball count");
        final Label greenballlabel = new Label("Green ball count");
        final Label blueballlabel = new Label("Blue ball count");
        final Label packageslabel = new Label("Amount of packages");

        final TextField yellowballfield = new TextField();
        final TextField redballfield = new TextField();
        final TextField greenballfield = new TextField();
        final TextField blueballfield = new TextField();
        final TextField packagesfield = new TextField();

        final GridPane quantities = new GridPane();
        quantities.setVgap(15);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(80);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        quantities.getColumnConstraints().addAll(col1,col2);
        quantities.addColumn(0, yellowballlabel, redballlabel, greenballlabel, blueballlabel, packageslabel);
        quantities.addColumn(1, yellowballfield, redballfield, greenballfield, blueballfield, packagesfield);

        final Button savebutton = new Button("  Save  ");
        savebutton.setFont(Font.font(null, FontWeight.BOLD, Font.getDefault().getSize()));
        //TODO: savebutton.addEventHandler(MouseEvent.MOUSE_ENTERED, new GUIeventListener());
        //savebutton.setStyle("-fx-background-color:#5aed5a;");
        final Button revertbutton = new Button("Revert changes");

        final GridPane buttonpane = new GridPane();
        buttonpane.setVgap(10);
        GridPane.setHalignment(revertbutton, HPos.RIGHT);
        ColumnConstraints butcol1 = new ColumnConstraints();
        butcol1.setPercentWidth(50);
        ColumnConstraints butcol2 = new ColumnConstraints();
        butcol2.setPercentWidth(50);
        buttonpane.getColumnConstraints().addAll(butcol1,butcol2);
        buttonpane.addColumn(0, savebutton);
        buttonpane.addColumn(1, revertbutton);

        vbox.getChildren().addAll(settingstext, blackLine, checkbox1, speedlabel, slider, quantities, buttonpane);
        left.getChildren().addAll(vbox);
        //=============================== END LEFT =====================================

        //=============================== START RIGHT ==================================
        final Label text1 = new Label("Dikke vette test");
        right.add(text1, 0, 0);
        //=============================== END LEFT =====================================

        final Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Kauwgomballen HMI");
        //window.setMaximized(true);
        window.show();
    }
}