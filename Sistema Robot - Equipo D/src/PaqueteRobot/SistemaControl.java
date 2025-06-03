package PaqueteRobot;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Sistema de control que implementa la interfaz InterfazSistemaControl.
 * Interpreta mensajes y realiza acciones, con toda la lógica de movimiento y sensor
 * encapsulada directamente en el método interpretarMensaje.
 */
public class SistemaControl implements InterfazSistemaControl {

    private int idModulo;

    public SistemaControl(int idModulo) {
        this.idModulo = idModulo;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public void interpretarMensaje(String comando,
                                   int[] robotPos, char[] robotDir, char[][] matrizEntorno, int[] mascotaPos,
                                   SensorProximidad sensorProximidad, Camara camara, Altavoz altavoz,
                                   ModuloDinamicoExtension extension, ModuloDinamicoRotacion rotacion, ModuloDinamicoHelicoidal helicoidal) {
        System.out.println("[SistemaControl Módulo " + idModulo + "] Interpretando comando: " + comando);

        int currentPosX = robotPos[0];
        int currentPosY = robotPos[1];
        char currentDir = robotDir[0];
        Random rand = new Random();
        int TAMANO_MATRIZ = matrizEntorno.length; 

        if (comando.equalsIgnoreCase("w") || comando.equalsIgnoreCase("avanzar")) {
           
            int xprox = currentPosX;
            int yprox = currentPosY;
            switch (currentDir) {
                case '^': yprox = currentPosY - 1; break;
                case 'v': yprox = currentPosY + 1; break;
                case '<': xprox = currentPosX - 1; break;
                case '>': xprox = currentPosX + 1; break;
            }

            // Lógica de sensado
            int resProximidad = sensorProximidad.procesarDatos(sensorProximidad.captarInformacion(yprox, xprox, matrizEntorno));
            int resCamara = camara.procesarDatos(camara.captarInformacion(yprox, xprox, matrizEntorno));

            System.out.println("DEBUG: Posición actual del robot: " + currentPosX + ", " + currentPosY);
            System.out.println("DEBUG: Nueva posición calculada: " + xprox + ", " + yprox);
            System.out.println("DEBUG: Resultado del sensor de proximidad: " + resProximidad);
            System.out.println("DEBUG: Resultado de la cámara: " + resCamara);

            if (resProximidad == 0 && resCamara == 3) { 
                if (xprox >= 0 && xprox < TAMANO_MATRIZ && yprox >= 0 && yprox < TAMANO_MATRIZ) {
                    matrizEntorno[robotPos[1]][robotPos[0]] = '.'; 
                    robotPos[0] = xprox;
                    robotPos[1] = yprox;
                    matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
                    System.out.println("El robot se ha movido.");
                } else {
                    System.out.println("Movimiento bloqueado: Fuera de los límites del mapa.");
                    System.out.println("SENSOR DE PROXIMIDAD: No hay obstáculo adelante. CÁMARA: Sensor fuera de los límites.");
                    if (helicoidal != null) {
                        int[] rotaciones = {1, -1};
                        int giro = helicoidal.moverse(new float[]{rotaciones[rand.nextInt(rotaciones.length)]});
                        if (giro == 1) {
                            switch (robotDir[0]) {
                                case '^': robotDir[0] = '>'; break;
                                case '>': robotDir[0] = 'v'; break;
                                case 'v': robotDir[0] = '<'; break;
                                case '<': robotDir[0] = '^'; break;
                            }
                            System.out.println("El robot ha girado a la derecha. Nueva dirección: " + robotDir[0]);
                            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
                        } else if (giro == -1) {
                            switch (robotDir[0]) {
                                case '^': robotDir[0] = '<'; break;
                                case '<': robotDir[0] = 'v'; break;
                                case 'v': robotDir[0] = '>'; break;
                                case '>': robotDir[0] = '^'; break;
                            }
                            System.out.println("El robot ha girado a la izquierda. Nueva dirección: " + robotDir[0]);
                            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
                        } else {
                            System.out.println("Giro no válido.");
                        }
                    } else {
                        System.out.println("Módulo helicoidal no disponible para evadir límites.");
                    }
                }
            } else { 
                if (resProximidad == 1 && resCamara == 1) { 
                    System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante. CÁMARA: Mascota detectada.");
                    if (altavoz != null && altavoz.realizarAccion() == 1) {
                        System.out.println("El robot ha emitido un sonido para alejar a la mascota.");
                        if (mascotaPos != null) {
                            matrizEntorno[mascotaPos[1]][mascotaPos[0]] = '.'; 
                            int newXMascota, newYMascota;
                            do {
                                newXMascota = rand.nextInt(TAMANO_MATRIZ);
                                newYMascota = rand.nextInt(TAMANO_MATRIZ);
                            } while ((newXMascota == robotPos[0] && newYMascota == robotPos[1]) || matrizEntorno[newYMascota][newXMascota] == 'X');
                            mascotaPos[0] = newXMascota;
                            mascotaPos[1] = newYMascota;
                            matrizEntorno[mascotaPos[1]][mascotaPos[0]] = 'P';
                            System.out.println("Mascota reubicada.");
                        }
                    } else {
                        System.out.println("No se pudo emitir el sonido o altavoz no disponible.");
                    }
                } else if ((resProximidad == 1 && resCamara == 2) || (resProximidad == 0 && resCamara == 4)) {
                    System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante. CÁMARA: Obstáculo detectado.");
                    if (rotacion != null) {
                        int[] rotaciones = {1, -1};
                        int giro = rotacion.moverse(new float[]{rotaciones[rand.nextInt(rotaciones.length)]});
                        if (giro == 1) {
                            switch (robotDir[0]) {
                                case '^': robotDir[0] = '>'; break;
                                case '>': robotDir[0] = 'v'; break;
                                case 'v': robotDir[0] = '<'; break;
                                case '<': robotDir[0] = '^'; break;
                            }
                            System.out.println("El robot ha girado a la derecha. Nueva dirección: " + robotDir[0]);
                            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0]; 
                        } else if (giro == -1) { 
                            switch (robotDir[0]) {
                                case '^': robotDir[0] = '<'; break;
                                case '<': robotDir[0] = 'v'; break;
                                case 'v': robotDir[0] = '>'; break;
                                case '>': robotDir[0] = '^'; break;
                            }
                            System.out.println("El robot ha girado a la izquierda. Nueva dirección: " + robotDir[0]);
                            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0]; 
                        } else {
                            System.out.println("Giro no válido.");
                        }
                    } else {
                        System.out.println("Módulo de rotación no disponible para evadir obstáculo.");
                    }
                } else {
                    System.out.println("Resultados inesperados de los sensores. El robot no puede avanzar.");
                }
            }
        } else if (comando.equalsIgnoreCase("a") || comando.equalsIgnoreCase("girar izquierda")) {

            switch (robotDir[0]) {
                case '^': robotDir[0] = '<'; break;
                case '<': robotDir[0] = 'v'; break;
                case 'v': robotDir[0] = '>'; break;
                case '>': robotDir[0] = '^'; break;
            }
            System.out.println("El robot ha girado a la izquierda. Nueva dirección: " + robotDir[0]);
            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
        } else if (comando.equalsIgnoreCase("d") || comando.equalsIgnoreCase("girar derecha")) {

            switch (robotDir[0]) {
                case '^': robotDir[0] = '>'; break;
                case '>': robotDir[0] = 'v'; break;
                case 'v': robotDir[0] = '<'; break;
                case '<': robotDir[0] = '^'; break;
            }
            System.out.println("El robot ha girado a la derecha. Nueva dirección: " + robotDir[0]);
            matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
        } else {
            System.out.println("[SistemaControl Módulo " + idModulo + "] Comando '" + comando + "' no reconocido.");
        }
    }

    @Override
    public boolean enviarRespuestaAccion() {
        System.out.println("[SistemaControl Módulo " + idModulo + "] Respuesta de acción enviada.");
        return true;
    }

    @Override
    public List<String> gestionarSolucion(int idProblema) {
        System.out.println("[SistemaControl Módulo " + idModulo + "] Gestionando solución para ID de problema: " + idProblema);
        List<String> soluciones = new ArrayList<>();

        if (idProblema == 56754) {
            soluciones.add("Avanzar");
            soluciones.add(Integer.toString(idModulo));
        }
        return soluciones;
    }
}