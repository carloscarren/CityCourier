module co.edu.uniquindio.citycourier.citycourier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    // UI controllers for FXML
    opens co.edu.uniquindio.citycourier.citycourier.viewcontroller to javafx.fxml;

    // App entry
    exports co.edu.uniquindio.citycourier.citycourier;

    // Expose business layer if needed from other modules (optional here)
    exports co.edu.uniquindio.citycourier.citycourier.controller;
}