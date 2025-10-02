module co.edu.uniquindio.citycourier.citycourier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens co.edu.uniquindio.citycourier.citycourier to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier;
}