package PaqueteRobot;
import java.util.Scanner;

/**
 * Clase Usuario, este le envía y recibe mensajes al sistema de comunicación
 * además, enciende y apaga el robot
 */
public class Usuario {
    // ATRIBUTOS
    private int id;
    private String alias;
    private String tipo;

    // CONSTRUCTOR
    public Usuario(int id, String alias, String tipo) {
        this.id = id;
        this.alias = alias;
        this.tipo = tipo;
    }

    // GETTERS
    public int getId() { return id; }
    public String getAlias() { return alias; }
    public String getTipo() { return tipo; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // OPERACIONES
    
    public String enviarComando(Scanner scanner) {
        System.out.print("Ingrese su opción ('w': avanzar, 'a': girar izq, 'd': girar der, 'q': salir, o ENTER para continuar): ");
        String inputLine = "";
        try {
            // Check if there's input available without blocking
            if (System.in.available() > 0) {
                inputLine = scanner.nextLine().trim();
            }
        } catch (java.io.IOException e) {
            System.err.println("Error de lectura de entrada: " + e.getMessage());
        }
        return inputLine;
    }

    public void recibirMensaje(String mensaje) {
        System.out.println("[Usuario " + alias + "] Mensaje recibido: " + mensaje);
    }
}