package PaqueteRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un robot compuesto por múltiples módulos.
 * Implementa la interfaz InterfazModulo para operaciones básicas de encendido/apagado
 * que se propagan a los módulos.
 * La clase Usuario lo puede encender y apagar. (Relacion uno a uno)
 * tiene una relacion de composicion con los modulos.
 */
public class Robot implements InterfazModulo {
    
    // ATRIBUTOS
    private List<Modulo> modulos; // Relacion de composicion uno a muchos
    private String serie;
    private String alias;
    private String descripcion;
    private int idUsuario; // Relacion uno a uno

    // CONSTRUCTOR
    public Robot(String serie, String alias, String descripcion, int idUsuario) {
        this.serie = serie;
        this.alias = alias;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.modulos = new ArrayList<>();
    }
    
    // GETTERS
    public String getSerie() {
        return serie;
    }

    public String getAlias() {
        return alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Modulo> getModulos() {
        return new ArrayList<>(modulos);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    // SETTERS
    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // MÉTODOS PARA GESTIÓN DE MÓDULOS
    public void agregarModulo(Modulo modulo) {
        modulos.add(modulo);
    }

    public boolean removerModulo(Modulo modulo) {
        return modulos.remove(modulo);
    }

    // IMPLEMENTACIÓN DE INTERFAZ
    @Override
    public void encender() {
        System.out.println("Encendiendo módulos dinámicos del robot " + alias + "...");
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                System.out.print("  Encendiendo " + modulo.getDescripcion() + ": ");
                modulo.encender();
            }
        }
        System.out.println("Encendiendo módulos estáticos del robot " + alias + "...");
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                System.out.print("  Encendiendo " + modulo.getDescripcion() + ": ");
                modulo.encender();
            }
        }
        System.out.println("Robot " + alias + " encendido completamente.");
    }

    @Override
    public void apagar() {
        System.out.println("Apagando módulos dinámicos del robot " + alias + "...");
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                System.out.print("  Apagando " + modulo.getDescripcion() + ": ");
                modulo.apagar();
            }
        }
        System.out.println("Apagando módulos estáticos del robot " + alias + "...");
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                System.out.print("  Apagando " + modulo.getDescripcion() + ": ");
                modulo.apagar();
            }
        }
        System.out.println("Robot " + alias + " apagado completamente.");
    }
}