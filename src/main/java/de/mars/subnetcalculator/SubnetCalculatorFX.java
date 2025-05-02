package de.mars.subnetcalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Hauptklasse der JavaFX-Anwendung. Diese Klasse initialisiert die grafische Benutzeroberfläche
 * für den Subnetzrechner, inklusive Eingabefelder, Ergebnisanzeige, Theme-Umschalter
 * und Speicherung von Berechnungen im JSON-Format.
 */
public class SubnetCalculatorFX extends Application {

    private TextField ipField;
    private TextField prefixField;
    private TextField subnetCountField;
    private TextArea resultArea;
    /** Die Szene der Anwendung, wird für Theme-Umschaltung verwendet. */
    private Scene scene;

    /**
     * Startet die JavaFX-Anwendung und erstellt das Hauptfenster mit Eingabefeldern,
     * Ergebnisanzeige, einem Button zur Subnetzberechnung, einem Button zum Laden vorheriger
     * Berechnungen sowie einem ToggleButton für den Dark-/Light-Mode.
     *
     * @param primaryStage Die Hauptbühne (Fenster) der Anwendung.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Subnet Calculator - JavaFX");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Subnet Calculator");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#333"));

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setAlignment(Pos.CENTER);

        Label ipLabel = new Label("IP-Adresse:");
        ipLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        ipField = new TextField();
        ipField.setPromptText("z.B. 192.168.1.0");

        Label prefixLabel = new Label("Präfix (z.B. /24):");
        prefixLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        prefixField = new TextField();
        prefixField.setPromptText("z.B. /24");

        Label subnetCountLabel = new Label("Anzahl Subnetze (optional):");
        subnetCountLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        subnetCountField = new TextField();
        subnetCountField.setPromptText("z.B. 4");

        Button calculateBtn = new Button("Berechnen");
        calculateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        calculateBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        calculateBtn.setDefaultButton(true);

        Button loadBtn = new Button("Berechnungen laden");
        loadBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        loadBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        loadBtn.setOnAction(e -> {
            resultArea.setText(JsonStorage.loadAllCalculations());
        });

        ToggleButton themeToggle = new ToggleButton("🌙 Dark Mode");
        themeToggle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        themeToggle.setStyle("-fx-background-radius: 6;");

        themeToggle.setOnAction(e -> {
            scene.getStylesheets().clear();
            if (themeToggle.isSelected()) {
                scene.getStylesheets().add(getClass().getResource("/dark-style.css").toExternalForm());
                themeToggle.setText("☀️ Light Mode");
            } else {
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                themeToggle.setText("🌙 Dark Mode");
            }
        });

        HBox buttonBox = new HBox(10, calculateBtn, loadBtn, themeToggle);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setFont(Font.font("Consolas", 13));
        resultArea.setPrefHeight(300);
        resultArea.setWrapText(true);

        inputGrid.add(ipLabel, 0, 0);
        inputGrid.add(ipField, 1, 0);
        inputGrid.add(prefixLabel, 0, 1);
        inputGrid.add(prefixField, 1, 1);
        inputGrid.add(subnetCountLabel, 0, 2);
        inputGrid.add(subnetCountField, 1, 2);

        root.getChildren().addAll(titleLabel, inputGrid, buttonBox, resultArea);

        // Wenn der Button geklickt wird, starte die Berechnung
        calculateBtn.setOnAction(e -> calculateSubnet());

        scene = new Scene(root, 550, 550);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/dark-style.css").toExternalForm());
        themeToggle.setSelected(true);
        themeToggle.setText("☀️ Light Mode");
        primaryStage.show();
    }

    /**
     * Liest die Eingaben des Benutzers und startet die passende Subnetz-Berechnung.
     */
    private void calculateSubnet() {
        resultArea.clear();
        String ip = ipField.getText().trim();
        String prefixInput = prefixField.getText().trim();
        String subnetCountInput = subnetCountField.getText().trim();

        if (ip.isEmpty() || prefixInput.isEmpty()) {
            resultArea.setText("Bitte IP-Adresse und Präfix ausfüllen!");
            return;
        }

        int prefixLength = IpUtils.extractPrefixLength(prefixInput);
        int subnetCount = 0;

        try {
            if (!subnetCountInput.isEmpty()) {
                subnetCount = Integer.parseInt(subnetCountInput);
            }

            if (prefixLength < 0 || prefixLength > 32 || subnetCount < 0) {
                resultArea.setText("Ungültige Eingabewerte!");
                return;
            }

            String result;
            if (subnetCount > 0) {
                result = SubnetCalculator.calculateMultipleSubnets(ip, prefixLength, subnetCount);
            } else {
                result = SubnetCalculator.calculateSingleSubnet(ip, prefixLength);
            }
            resultArea.setText(result);
            JsonStorage.saveCalculation(ip, prefixLength, subnetCount, result);


        } catch (Exception ex) {
            resultArea.setText("Fehlerhafte Eingabe!");
        }
    }

    /**
     * Startpunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (nicht verwendet).
     */
    public static void main(String[] args) {
        launch(args);
    }
}