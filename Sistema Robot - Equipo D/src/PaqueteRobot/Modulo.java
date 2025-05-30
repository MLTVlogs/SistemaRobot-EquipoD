package PaqueteRobot;

import java.util.List;

/**
 * Clase abstracta Módulo, superclase de ModuloEstatico y ModuloDinamico, discriminador
 * comportamiento.
 * Implementa las interfaces InterfazModulo (para operaciones básicas de encendido/apagado)
e InterfazSistemaControl para propagar operaciones a sistema de control, enviar.
 * respuesta y gestionar soluciones.
 * tiene una relacion de composicion con SistemaControl y SistemaComunicacion.
 * ademas una relacion uno a uno con sistema de control, le informa la accion
 */
public abstract class Modulo implements InterfazModulo, InterfazSistemaControl {
    // ATRIBUTOS
    private int id;
    private String referencia;
    private String descripcion;
    private int largo;
    private int ancho;
    private int profundidad;
    private SistemaControl sistemaControl; //composicion uno a uno
    private SistemaComunicacion sistemaComunicacion; //composicion uno a uno
    private String discriminador; //discrimina el comportamiento dinamico o estatico

    // CONSTRUCTOR
    public Modulo(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, String discriminador) {
        this.id = id;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.sistemaControl = new SistemaControl(id);
        this.sistemaComunicacion = new SistemaComunicacion(id);
        this.discriminador = discriminador;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLargo() {
        return largo;
    }

    public int getAncho() {
        return ancho;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public SistemaControl getSistemaControl() {
        return sistemaControl;
    }

    public SistemaComunicacion getSistemaComunicacion() {
        return sistemaComunicacion;
    }

    public String getDiscriminador() {
        return discriminador;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void setSistemaControl(SistemaControl sistemaControl) {
        this.sistemaControl = sistemaControl;
    }

    public void setSistemaComunicacion(SistemaComunicacion sistemaComunicacion) {
        this.sistemaComunicacion = sistemaComunicacion;
    }

    public void setDiscriminador(String discriminador) {
        this.discriminador = discriminador;
    }

    // IMPLEMENTACIÓN DE INTERFACES
    @Override
    public void encender() {
        System.out.println("El modulo ha sido encendido");
    }

    @Override
    public void apagar() {
        System.out.println("El modulo ha sido apagado");
    }

    @Override
    public boolean enviarRespuestaAccion() {
        return false; //Parte a revisar, como se propaga la operacion
    }

    @Override
    public List<String> gestionarSolucion(int idAccion) {
        return null;
    } //Parte a revisar, como se propaga la operacion

    // MÉTODOS
    public abstract void recibirInfoAccion(int idAccion);
}