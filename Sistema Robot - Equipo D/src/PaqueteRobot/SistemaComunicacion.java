package PaqueteRobot;

import java.util.List;
import java.util.ArrayList;


/**
 * tiene una relacion uno a uno con sistema de control, el sistema de control le pasa el mensaje
 * es una composicion con modulo
 * tiene una relacion uno a uno con usuario, el usuario recibe el mensaje
 * tiene otra relacion uno a uno con usuario, el usuario envia el mensaje
 * ademas tiene una relacion reflexiva consigo misma, esta el sistema de comunicacion que
 * recibe el mensaje y otro sistema de comunicacion lo recibe.
 */
public class SistemaComunicacion{

    private int idUsuario;
    private int idModulo;
    private Boolean emisor; //Para resolver la reflexividad
    private Boolean receptor; //Para resolver la reflexividad

    // CONSTRUCTOR
    public SistemaComunicacion(int idModulo) {
        this.idUsuario = 0; // Inicializado a 0, puede ser modificado posteriormente
        this.idModulo = idModulo;
        this.emisor = false;
        this.receptor = false;
    }

    // GETTERS
    public int getIdUsuario(){
        return idUsuario;
    }


    public Boolean getEmisor(){
        return emisor;
    }

    public Boolean getReceptor(){
        return receptor;
    }

    public int getIdModulo(){
        return idModulo;
    }

    // SETTERS
    public void setEmisor(Boolean emisor){
        this.emisor = emisor;
    }

    public void setReceptor(Boolean receptor){
        this.receptor = receptor;
    }

    public void setIdModulo(int idModulo){
        this.idModulo = idModulo;
    }

    // ASOCIACION CON USUARIO
    public void asociarUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    
    // OPERACIONES
    public List<String> enviarMensaje(String mensaje,int idModulo){
        if (emisor == true){
            List<String> mensajes = new ArrayList<>();
            mensajes.add(mensaje);
            mensajes.add(String.valueOf(idModulo));
            return mensajes;
        } else {
            System.out.println("El mensaje no se pudo enviar");
            return null;
        }
    }

    public void recibirMensaje(String mensaje,int idUsuario){
        if (receptor == true){
            System.out.println("El mensaje es: " + mensaje + " y el id del usuario es: " + idUsuario );
        } else {
            System.out.println("El mensaje no se pudo recibir");
        }
    }
}