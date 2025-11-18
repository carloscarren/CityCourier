module co.edu.uniquindio.citycourier.citycourier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires itextpdf;

    opens co.edu.uniquindio.citycourier.citycourier to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier;

    opens co.edu.uniquindio.citycourier.citycourier.model;
    exports co.edu.uniquindio.citycourier.citycourier.model;

    opens co.edu.uniquindio.citycourier.citycourier.model.ENUMS;
    exports co.edu.uniquindio.citycourier.citycourier.model.ENUMS;

    opens co.edu.uniquindio.citycourier.citycourier.controller to javafx.fxml;
    exports co.edu.uniquindio.citycourier.citycourier.controller;

    exports co.edu.uniquindio.citycourier.citycourier.factory;
    opens co.edu.uniquindio.citycourier.citycourier.factory;
    exports co.edu.uniquindio.citycourier.citycourier.viewController.usuario;
    opens co.edu.uniquindio.citycourier.citycourier.viewController.usuario to javafx.fxml;
    
    exports co.edu.uniquindio.citycourier.citycourier.viewController.administrador;
    opens co.edu.uniquindio.citycourier.citycourier.viewController.administrador to javafx.fxml;
    
    exports co.edu.uniquindio.citycourier.citycourier.viewController.loggin;
    opens co.edu.uniquindio.citycourier.citycourier.viewController.loggin to javafx.fxml;
    
    exports co.edu.uniquindio.citycourier.citycourier.mapping.dto;
    opens co.edu.uniquindio.citycourier.citycourier.mapping.dto;
    
    exports co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.command;
    opens co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.command;
    
    exports co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;
    opens co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;
    
    exports co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;
    opens co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;
    
    exports co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;
    opens co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;

}