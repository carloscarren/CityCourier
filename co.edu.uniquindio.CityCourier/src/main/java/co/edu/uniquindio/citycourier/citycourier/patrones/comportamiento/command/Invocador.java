package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.command;

public class Invocador {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void ejecutar() {
        if (command != null) {
            command.execute();
        }
    }
}