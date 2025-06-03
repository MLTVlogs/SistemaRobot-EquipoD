package sistema.robot.equipo.d;

import PaqueteRobot.*; 

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class SistemaRobotEquipoD {

    private static Scanner scanner = new Scanner(System.in);
    private static int[] robotPos = new int[2];
    private static char[] robotDir = new char[1];
    private static int[] mascotaPos = new int[2];
    private static final int TAMANO_MATRIZ = 10;
    private static char[][] matrizEntorno = new char[TAMANO_MATRIZ][TAMANO_MATRIZ];
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Robot> robots = new ArrayList<>();

    public static void main(String[] args) {
        try {
            menuUsuario();

        } catch (Exception e) {
            System.err.println("Error inesperado en la simulación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.exit(0);
        }
    }

    // --- Interfaces ---
    private static void menuUsuario() {
        System.out.println("--- BIENVENIDO AL SISTEMA DE ROBOT ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Ingrese una opción: ");
        int opcion = -1;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            menuUsuario();
            return;
        }

        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarse();
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
                menuUsuario();
                break;
        }
    }

    private static void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Por favor, regístrese primero.");
            System.out.println("Presione ENTER para volver al menú...");
            scanner.nextLine();
            menuUsuario();
            return;
        }
        System.out.print("Ingrese su Alias: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese su ID: ");
        int idUsuario = -1;
        try {
            idUsuario = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, ingrese un número.");
            iniciarSesion();
            return;
        }

        Usuario usuarioLogueado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getAlias().equalsIgnoreCase(nombreUsuario) && usuario.getId() == idUsuario) {
                usuarioLogueado = usuario;
                break;
            }
        }

        if (usuarioLogueado != null) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuarioLogueado.getAlias() + "!");
            menuRobot(usuarioLogueado);
        } else {
            System.out.println("Usuario no encontrado o ID incorrecto.");
            menuUsuario();
        }
    }

    private static void registrarse() {
        System.out.println("\n--- REGISTRARSE ---");
        System.out.print("Ingrese su Alias: ");
        String aliasUsuario = scanner.nextLine();
        int idUsuario = 0;
        boolean idUnico = false;
        while (!idUnico) {
            System.out.print("Ingrese su ID (número entero único): ");
            try {
                idUsuario = Integer.parseInt(scanner.nextLine());
                idUnico = true;
                for (Usuario u : usuarios) {
                    if (u.getId() == idUsuario) {
                        System.out.println("Este ID ya está en uso. Por favor, elija otro.");
                        idUnico = false;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Por favor, ingrese un número.");
            }
        }
        System.out.print("Ingrese su tipo de usuario (ej. Operador, Admin): ");
        String tipoUsuario = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(idUsuario, aliasUsuario, tipoUsuario);
        usuarios.add(nuevoUsuario);
        System.out.println("Registro exitoso.");
        
        asignarRobotUsuario(idUsuario);

        menuUsuario();
    }


    private static void asignarRobotUsuario(int idUsuario) {
        System.out.println("\n--- ASIGNAR ROBOT ---");
        System.out.print("Ingrese el alias del robot para este usuario: ");
        String aliasRobot = scanner.nextLine();
        
        Robot nuevoRobot = new Robot("DEF001", aliasRobot, "Robot", idUsuario);
        robots.add(nuevoRobot); 

        asignarModulosYSistemas(nuevoRobot);

        System.out.println("Robot '" + aliasRobot + "' asignado exitosamente al usuario con ID " + idUsuario);
    }

    private static void asignarModulosYSistemas(Robot robot) {

        ModuloDinamicoExtension extension = new ModuloDinamicoExtension(3,"AAaaa", "ModuloDinamicoExtension", 32, 32, 32);
        ModuloDinamicoRotacion rotacion = new ModuloDinamicoRotacion(5,"AAaaa", "ModuloDinamicoRotacion", 32, 32, 32);
        ModuloDinamicoHelicoidal helicoidal = new ModuloDinamicoHelicoidal(6,"AAaaa", "ModuloDinamicoHelicoidal", 32, 32, 32);
        Camara camara = new Camara(6,"AAaaa", "Camara", 32, 32, 32);
        SensorProximidad sensorProximidad = new SensorProximidad(3,"AAaaa", "SensorProximidad", 32, 32, 32);
        Altavoz altavoz = new Altavoz(6,"AAaaa", "Altavoz", 32, 32, 32);

        robot.agregarModulo(extension);
        robot.agregarModulo(rotacion);
        robot.agregarModulo(helicoidal);
        robot.agregarModulo(camara);
        robot.agregarModulo(sensorProximidad);
        robot.agregarModulo(altavoz);

        extension.setSistemaControl(new SistemaControl(extension.getId()));
        rotacion.setSistemaControl(new SistemaControl(rotacion.getId()));
        helicoidal.setSistemaControl(new SistemaControl(helicoidal.getId()));
        camara.setSistemaControl(new SistemaControl(camara.getId()));
        sensorProximidad.setSistemaControl(new SistemaControl(sensorProximidad.getId()));
        altavoz.setSistemaControl(new SistemaControl(altavoz.getId()));
        
        SistemaComunicacion sControlExtension = new SistemaComunicacion(extension.getId());
        sControlExtension.setReceptor(true);
        SistemaComunicacion sControlRotacion = new SistemaComunicacion(rotacion.getId());
        sControlRotacion.setReceptor(true);
        SistemaComunicacion sControlHelicoidal = new SistemaComunicacion(helicoidal.getId());
        sControlHelicoidal.setReceptor(true);
        SistemaComunicacion sControlCamara = new SistemaComunicacion(camara.getId());
        sControlCamara.setEmisor(true);
        SistemaComunicacion sControlSensorProximidad = new SistemaComunicacion(sensorProximidad.getId());
        sControlSensorProximidad.setEmisor(true);
        SistemaComunicacion sControlAltavoz = new SistemaComunicacion(altavoz.getId());
        sControlAltavoz.setEmisor(true);

        sControlExtension.setIdModulo(extension.getId());
        sControlRotacion.setIdModulo(rotacion.getId());
        sControlHelicoidal.setIdModulo(helicoidal.getId());
        sControlCamara.setIdModulo(camara.getId());
        sControlSensorProximidad.setIdModulo(sensorProximidad.getId());
        sControlAltavoz.setIdModulo(altavoz.getId());

        extension.setSistemaComunicacion(sControlExtension);
        rotacion.setSistemaComunicacion(sControlRotacion);
        helicoidal.setSistemaComunicacion(sControlHelicoidal);
        camara.setSistemaComunicacion(sControlCamara);
        sensorProximidad.setSistemaComunicacion(sControlSensorProximidad);
        altavoz.setSistemaComunicacion(sControlAltavoz);

        Sensor sensorVisual = new Sensor(35654,"CAMARA","Sensor de vision");
        Sensor sensorInfrarrojo = new Sensor(23564,"PROXIMIDAD","Sensor de proximidad");

        camara.agregarSensor(sensorVisual);
        camara.setNumeroSensores(camara.getSensores().size());

        sensorProximidad.agregarSensor(sensorInfrarrojo);
        sensorProximidad.setNumeroSensores(sensorProximidad.getSensores().size());

        Actuador bocina = new Actuador(35654,"ALTAVOZ","Actuador de bocina");
        altavoz.agregarActuador(bocina);
        altavoz.setNumeroActuadores(altavoz.getActuadores().size());

        // Set default number of motors
        extension.setNumeroMotores(1);
        rotacion.setNumeroMotores(1);
        helicoidal.setNumeroMotores(1);
    }

    private static void menuRobot(Usuario usuario) {
        System.out.println("\n--- MENÚ DEL ROBOT PARA " + usuario.getAlias() + " ---");
        System.out.println("1. Cambiar alias del robot");
        System.out.println("2. Consultar información del robot");
        System.out.println("3. Ingresar a la simulación");
        System.out.println("4. Cerrar sesión");
        System.out.println("5. Salir del programa");
        System.out.print("Ingrese una opción: ");
        int opcion = -1;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            menuRobot(usuario);
            return;
        }

        switch (opcion) {
            case 1:
                cambiarAliasRobot(usuario.getId());
                menuRobot(usuario);
                break;
            case 2:
                for (Robot robot : robots) {
                    if (robot.getIdUsuario() == usuario.getId()) {
                        System.out.println("Información del robot:");
                        System.out.println(robot.toString());
                        break;
                    }
                }
                menuRobot(usuario);
                break;
            case 3:
                Robot robotAsignado = null;
                for(Robot r : robots) {
                    if (r.getIdUsuario() == usuario.getId()) {
                        robotAsignado = r;
                        break;
                    }
                }
                if (robotAsignado != null) {
                    simulacionRobot(usuario, robotAsignado);
                } else {
                    System.out.println("No hay un robot asignado a este usuario. Por favor, asigne uno primero.");
                    menuRobot(usuario);
                }
            case 4:
                System.out.println("Cerrando sesión de " + usuario.getAlias() + ".");
                menuUsuario();
                break;
            case 5:
                System.out.println("Saliendo del programa...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
                menuRobot(usuario);
                break;
        }
    }

    private static void cambiarAliasRobot(int idUsuario) {
        System.out.print("Ingrese el nuevo alias para su robot: ");
        String nuevoAlias = scanner.nextLine();
        boolean encontrado = false;
        for (Robot robot : robots) {
            if (robot.getIdUsuario() == idUsuario) {
                robot.setAlias(nuevoAlias);
                System.out.println("Alias del robot cambiado exitosamente a '" + nuevoAlias + "'.");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un robot asignado a su ID de usuario.");
        }
    }

    private static void inicializarMatriz() {
        for (int i = 0; i < TAMANO_MATRIZ; i++) {
            for (int j = 0; j < TAMANO_MATRIZ; j++) {
                matrizEntorno[i][j] = '.';
            }
        }
        robotPos[0] = TAMANO_MATRIZ / 2;
        robotPos[1] = TAMANO_MATRIZ / 2;
        robotDir[0] = '^';
        matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0];
    }

    private static void colocarObstaculos() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) { 
            int obsX, obsY;
            do {
                obsX = rand.nextInt(TAMANO_MATRIZ);
                obsY = rand.nextInt(TAMANO_MATRIZ);
            } while ((obsX == robotPos[0] && obsY == robotPos[1]) || matrizEntorno[obsY][obsX] == 'X'); 
            matrizEntorno[obsY][obsX] = 'X'; 
        }

        // Place the pet
        do {
            mascotaPos[0] = rand.nextInt(TAMANO_MATRIZ);
            mascotaPos[1] = rand.nextInt(TAMANO_MATRIZ);
        } while ((mascotaPos[0] == robotPos[0] && mascotaPos[1] == robotPos[1]) || matrizEntorno[mascotaPos[1]][mascotaPos[0]] == 'X');
        matrizEntorno[mascotaPos[1]][mascotaPos[0]] = 'P'; 
    }

    private static void imprimirMatriz() {
        System.out.println("\n--- MAPA DEL ROBOT ---");
        for (int i = 0; i < TAMANO_MATRIZ; i++) {
            for (int j = 0; j < TAMANO_MATRIZ; j++) {
                System.out.print(matrizEntorno[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
        System.out.println("Posición Robot: (" + robotPos[0] + ", " + robotPos[1] + ") Dirección: " + robotDir[0]);
        System.out.println("Posición Mascota: (" + mascotaPos[0] + ", " + mascotaPos[1] + ")");
    }

    public static void limpiarConsola() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    public static void interaccionUsuarioEnSimulacion(Usuario usuario, Robot robot, Scanner scanner) {
        String comandoUsuario = usuario.enviarComando(scanner);

        if (comandoUsuario.equalsIgnoreCase("q")) {
            usuario.recibirMensaje("Orden de apagado recibida.");
            robot.apagar();
            menuRobot(usuario);;
        }

        if (robot.getModulos() != null && !robot.getModulos().isEmpty()) {
            SensorProximidad sensorProximidad = null;
            Camara camara = null;
            Altavoz altavoz = null;
            ModuloDinamicoExtension extension = null;
            ModuloDinamicoRotacion rotacion = null;
            ModuloDinamicoHelicoidal helicoidal = null;

            for (Modulo m : robot.getModulos()) {
                if (m instanceof SensorProximidad) sensorProximidad = (SensorProximidad) m;
                else if (m instanceof Camara) camara = (Camara) m;
                else if (m instanceof Altavoz) altavoz = (Altavoz) m;
                else if (m instanceof ModuloDinamicoExtension) extension = (ModuloDinamicoExtension) m;
                else if (m instanceof ModuloDinamicoRotacion) rotacion = (ModuloDinamicoRotacion) m;
                else if (m instanceof ModuloDinamicoHelicoidal) helicoidal = (ModuloDinamicoHelicoidal) m;
            }

            Modulo moduloControl = robot.getModulos().get(0);

            SistemaComunicacion scModulo = moduloControl.getSistemaComunicacion();
            if (scModulo != null) {
                scModulo.setReceptor(true);
                scModulo.recibirMensaje(comandoUsuario, usuario.getId());
                scModulo.setReceptor(false);
            }

            moduloControl.procesarComando(comandoUsuario,
                                           robotPos, robotDir, matrizEntorno, mascotaPos,
                                           sensorProximidad, camara, altavoz,
                                           extension, rotacion, helicoidal);

        } else {
            System.out.println("Error: No hay módulos en el robot para procesar el comando.");
        }
    }

    private static void simulacionRobot(Usuario usuario, Robot robotActual) {
        System.out.println("\n--- INICIANDO SIMULACIÓN DEL ROBOT '" + robotActual.getAlias() + "' ---");
        robotActual.encender();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La simulación fue interrumpida.");
            return;
        }

        inicializarMatriz();
        colocarObstaculos();

        
        do {
            limpiarConsola();
            imprimirMatriz();
            System.out.println("----------------------");
            
            System.out.println("Ingrese un comando para el robot (w: avanzar, a: girar izq, d: girar der, q: salir de simulación):");

            interaccionUsuarioEnSimulacion(usuario, robotActual, scanner);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("La simulación fue interrumpida.");
                break;
            }
        } while (true);

        System.out.println("Volviendo al menú del robot...");
        menuRobot(usuario);
    }
}