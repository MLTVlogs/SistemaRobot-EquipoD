package PaqueteRobot;

/**
 * Clase que representa un actuador
 * tiene una relacion de agregacion con la clase altavoz
 * altavoz le propaga la operacion realizarAccion
 */
public class Actuador implements InterfazActuador {

    private int id;
    private String tipo;
    private String descripcion;

    //CONSTRUCTOR
    public Actuador(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
    
    //GETTERS
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    
    //SETTERS
    public void setId(int id) { this.id = id; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    

    @Override
    public int realizarAccion() {
        if ("ALTAVOZ".equals(this.tipo)) {
            System.out.println("Actuador (ID: " + id + ", Tipo: " + tipo + "): Emitiendo sonido de alerta.");
            return 1;
        } return 0;
    }
}