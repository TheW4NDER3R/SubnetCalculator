module de.mars.subnetcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.mars.subnetcalculator to javafx.fxml;
    exports de.mars.subnetcalculator;
}