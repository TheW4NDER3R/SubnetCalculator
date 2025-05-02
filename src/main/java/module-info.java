module de.mars.subnetcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens de.mars.subnetcalculator to javafx.fxml;
    exports de.mars.subnetcalculator;
}