package PaqueteRobot;

/**
 * SistemaComunicacion maneja la comunicación entre el usuario y los módulos,
 * y entre los propios módulos.
 */
public class SistemaComunicacion {

    private int idModulo;
    private Boolean emisor; // Para indicar si puede enviar mensajes
    private Boolean receptor; // Para indicar si puede recibir mensajes

    // CONSTRUCTOR
    public SistemaComunicacion(int idModulo) {
        this.idModulo = idModulo;
        this.emisor = false;
        this.receptor = false;
    }

    // GETTERS
    public int getIdModulo() {
        return idModulo;
    }

    public Boolean getEmisor() {
        return emisor;
    }

    public Boolean getReceptor() {
        return receptor;
    }

    // SETTERS
    public void setEmisor(Boolean emisor) {
        this.emisor = emisor;
    }

    public void setReceptor(Boolean receptor) {
        this.receptor = receptor;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    // OPERACIONES
    public boolean enviarMensaje(String mensaje, int remitenteId, int destinatarioId) {
        if (emisor) {
            System.out.println("[SC Módulo " + remitenteId + " -> " + destinatarioId + "] Enviando: '" + mensaje + "'");
            return true;
        } else {
            System.out.println("[SC Módulo " + idModulo + "] El sistema de comunicación no está configurado como emisor.");
            return false;
        }
    }

    public boolean recibirMensaje(String mensaje, int remitenteId) {
        if (receptor) {
            System.out.println("[SC Módulo " + idModulo + " <- " + remitenteId + "] Mensaje recibido: '" + mensaje + "'");
            return true;
        } else {
            System.out.println("[SC Módulo " + idModulo + "] El sistema de comunicación no está configurado como receptor.");
            return false;
        }
    }
}