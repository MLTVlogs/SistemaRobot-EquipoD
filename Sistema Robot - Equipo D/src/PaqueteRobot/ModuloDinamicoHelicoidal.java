package PaqueteRobot;

public class ModuloDinamicoHelicoidal extends ModuloDinamico {

    public ModuloDinamicoHelicoidal(int id, String referencia, String descripcion, int largo, int ancho, int profundidad) {
        super(id, referencia, descripcion, largo, ancho, profundidad);
    }

    public int moverse(float[] movimiento){
        // Implementar la logica de rotacion
        float r = movimiento[0];
        if (r == 1){
            return 1; // Rotar a la derecha y avanzar
        } else if (r == -1) {
            return -1; // Rotar a la izquierda y avanzar
        } else {
            return 0; // Movimiento no válido
        }
    }

    @Override
    public void recibirInfoAccion(int idAccion) {
    }
}