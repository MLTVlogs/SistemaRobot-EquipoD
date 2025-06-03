package PaqueteRobot;

/**
 * 
 */

public class ModuloDinamicoRotacion extends ModuloDinamico {
    
    public ModuloDinamicoRotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad) {
        super(id, referencia, descripcion, largo, ancho, profundidad);
    }

    public int moverse(float[] movimiento){
        // Implementar la logica de rotacion
        float r = movimiento[0];
        if (r == 1){
            return 1; // Rotar a la derecha
        } else if (r == -1) {
            return -1; // Rotar a la izquierda
        } else {
            return 0; // Movimiento no válido
        }
    }

    @Override
    public void recibirInfoAccion(int idAccion) {
    }
}