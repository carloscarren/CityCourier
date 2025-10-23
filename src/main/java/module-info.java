module co.edu.uniquindio.citycourier.citycourier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens co.edu.uniquindio.citycourier.citycourier to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier;

    opens co.edu.uniquindio.citycourier.citycourier.service.envio to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier.service.envio;

    opens co.edu.uniquindio.citycourier.citycourier.service.tarifa to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier.service.tarifa;

    opens co.edu.uniquindio.citycourier.citycourier.model;
    exports co.edu.uniquindio.citycourier.citycourier.model;

    opens co.edu.uniquindio.citycourier.citycourier.model.ENUMS;
    exports co.edu.uniquindio.citycourier.citycourier.model.ENUMS;

    opens co.edu.uniquindio.citycourier.citycourier.controller to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier.controller;

    opens co.edu.uniquindio.citycourier.citycourier.viewController to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier.viewController;
    exports co.edu.uniquindio.citycourier.citycourier.factory;
    opens co.edu.uniquindio.citycourier.citycourier.factory;

}