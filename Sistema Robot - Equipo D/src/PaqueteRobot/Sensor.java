package PaqueteRobot;

public class Sensor implements InterfazSensor {
    
    private int id;
    private String tipo;
    private String descripcion;

    // CONSTRUCTOR
    public Sensor(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Object captarInformacion(int rowChar, int colChar, char[][] matriz) {
        int r = rowChar; 
        int c = colChar;

        if (matriz == null || r < 0 || r >= matriz.length || matriz.length == 0 || c < 0 || c >= matriz[0].length) {
            System.err.println("Sensor (ID: " + id + ", Tipo: " + tipo + "): Cordenadas (" + (int)r + "," + (int)c + ") estan fuera de los limites.");
            switch (this.tipo) {
                case "PROXIMIDAD":
                    return false;
                case "CAMARA": 
                    return "FUERA DE LOS LIMITES";
                default:
                    return null;
            }
        }

        char cellContent = matriz[r][c];

        switch (this.tipo) {
            case "PROXIMIDAD":

                if (cellContent == 'O' || cellContent == 'P') {
                    return true;
                } else {
                    return false;
                }
            case "CAMARA":
                
                if (cellContent == 'P') {
                    return "MASCOTA";
                } else if (cellContent == 'O') {
                    return "OBSTACULO";
                } else if (cellContent == '.') { 
                    return "LIBRE";
                } else {
                    return "CARACTER NO RECONOCIDO";
                }
            default:
                System.err.println("Sensor (ID: " + id + "): tipo de sensor desconocido: " + this.tipo);
                return null;
        }
    }
}