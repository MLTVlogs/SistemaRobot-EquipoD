package PaqueteRobot;

/**
 * subclase de modulo, agrega los atributos numero de motores y el metodo moverse
 * El metodo moverse recibe un array de float con el movimiento a realizar por el Robot
 */

public abstract class ModuloDinamico extends Modulo {
    
    private int numeroMotores;

    public ModuloDinamico(int id, String referencia, String descripcion, int largo, int ancho, int profundidad) {
        super(id, referencia, descripcion, largo, ancho, profundidad, "Dinamico");
    }

    // GETTER Y SETTER
    public int getNumeroMotores() {
        return numeroMotores;
    }

    public void setNumeroMotores(int numeroMotores) {
        this.numeroMotores = numeroMotores;
    }

    // METODO
    public abstract int moverse(float[] movimiento);
}