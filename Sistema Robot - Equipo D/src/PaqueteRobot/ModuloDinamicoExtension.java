package PaqueteRobot;

public class ModuloDinamicoExtension extends ModuloDinamico {
    
    public ModuloDinamicoExtension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad) {
        super(id, referencia, descripcion, largo, ancho, profundidad);
    }

    public int moverse(float[] movimiento){
        // Implementar la logica de movimiento
        float x = movimiento[0];
        float y = movimiento[1];

        if (x == 0 && y < 0) {
            return 1; // Avanzar
        } else if (x == 0 && y > 0) {
            return -1; // Retroceder
        } else if (x > 0 && y == 0) {
            return 2; // Girar a la derecha
        } else if (x < 0 && y == 0) {
            return -2; // Girar a la izquierda
        } else {
            return 0; // Movimiento no válido
        }
    }
    
    @Override
    public void recibirInfoAccion(int idAccion) {
        //Logica a implementar
    }
}
