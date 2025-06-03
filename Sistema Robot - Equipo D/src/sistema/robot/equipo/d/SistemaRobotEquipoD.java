package sistema.robot.equipo.d;

import PaqueteRobot.*; 

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class SistemaRobotEquipoD {

    private static Scanner scanner = new Scanner(System.in);
    private static int[] robotPos = new int[2]; // [posX, posY]
    private static char[] robotDir = new char[1]; // [direccion]
    private static int[] mascotaPos = new int[2]; // [posXMascota, posYMascota]
    private static final int TAMANO_MATRIZ = 10;
    private static char[][] matrizEntorno = new char[TAMANO_MATRIZ][TAMANO_MATRIZ];
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Robot> robots = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Initialize the environment matrix and place obstacles/pet once
            inicializarMatriz();
            colocarObstaculos();

            // Initial setup for testing, as you had it
            Usuario usuario1 = new Usuario(1, "Usuario1", "Admin");
            usuarios.add(usuario1);

            Robot robot1 = new Robot("AAaaa", "Robot1", "Robot", 1);
            robots.add(robot1); // Add robot1 to the list

            // Assign modules to robot1 exactly as you specified
            asignarModulosYSistemas(robot1);

            menuUsuario();

        } catch (Exception e) {
            System.err.println("Error inesperado en la simulación: " + e.getMessage());
            e.printStackTrace(); // For debugging
        } finally {
            scanner.close(); // Close the scanner when the program finishes
            System.exit(0);
        }
    }

    // --- User Interface Methods ---
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
            iniciarSesion();
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
        
        // Call asignarRobotUsuario to assign a robot and its modules to the new user
        asignarRobotUsuario(idUsuario);

        menuUsuario();
    }

    // --- Robot Assignment and Module Setup ---
    private static void asignarRobotUsuario(int idUsuario) {
        System.out.println("\n--- ASIGNAR ROBOT ---");
        System.out.print("Ingrese el alias del robot para este usuario: ");
        String aliasRobot = scanner.nextLine();
        
        // Assuming Robot constructor is Robot(String ref, String alias, String tipo, int idUsuario)
        Robot nuevoRobot = new Robot("DEF001", aliasRobot, "Robot", idUsuario);
        robots.add(nuevoRobot); // Add the new robot to the global list

        // Assign modules and systems to the new robot exactly as you specified
        asignarModulosYSistemas(nuevoRobot);

        System.out.println("Robot '" + aliasRobot + "' asignado exitosamente al usuario con ID " + idUsuario);
    }

    /**
     * This method encapsulates the creation and assignment of modules,
     * their control systems, communication systems, sensors, and actuators
     * to a given Robot instance, exactly as per your specified structure.
     *
     * @param robot The Robot instance to which modules and systems will be assigned.
     */
    private static void asignarModulosYSistemas(Robot robot) {
        // Instantiate Modules with all their constructor parameters
        ModuloDinamicoExtension extension = new ModuloDinamicoExtension(3,"AAaaa", "ModuloDinamicoExtension", 32, 32, 32);
        ModuloDinamicoRotacion rotacion = new ModuloDinamicoRotacion(5,"AAaaa", "ModuloDinamicoRotacion", 32, 32, 32);
        ModuloDinamicoHelicoidal helicoidal = new ModuloDinamicoHelicoidal(6,"AAaaa", "ModuloDinamicoHelicoidal", 32, 32, 32);
        Camara camara = new Camara(6,"AAaaa", "Camara", 32, 32, 32);
        SensorProximidad sensorProximidad = new SensorProximidad(3,"AAaaa", "SensorProximidad", 32, 32, 32);
        Altavoz altavoz = new Altavoz(6,"AAaaa", "Altavoz", 32, 32, 32);

        // Add modules to the robot
        // This implies Robot.java must have an `agregarModulo(Modulo m)` method
        robot.agregarModulo(extension);
        robot.agregarModulo(rotacion);
        robot.agregarModulo(helicoidal);
        robot.agregarModulo(camara);
        robot.agregarModulo(sensorProximidad);
        robot.agregarModulo(altavoz);

        // Assign control systems to the modules
        extension.setSistemaControl(new SistemaControl(extension.getId()));
        rotacion.setSistemaControl(new SistemaControl(rotacion.getId()));
        helicoidal.setSistemaControl(new SistemaControl(helicoidal.getId()));
        camara.setSistemaControl(new SistemaControl(camara.getId()));
        sensorProximidad.setSistemaControl(new SistemaControl(sensorProximidad.getId()));
        altavoz.setSistemaControl(new SistemaControl(altavoz.getId()));
        
        // Create communication systems for each module
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

        // Associate modules to communication systems
        sControlExtension.setIdModulo(extension.getId());
        sControlRotacion.setIdModulo(rotacion.getId());
        sControlHelicoidal.setIdModulo(helicoidal.getId());
        sControlCamara.setIdModulo(camara.getId());
        sControlSensorProximidad.setIdModulo(sensorProximidad.getId());
        sControlAltavoz.setIdModulo(altavoz.getId());

        // Assign communication systems to the modules
        extension.setSistemaComunicacion(sControlExtension);
        rotacion.setSistemaComunicacion(sControlRotacion);
        helicoidal.setSistemaComunicacion(sControlHelicoidal);
        camara.setSistemaComunicacion(sControlCamara);
        sensorProximidad.setSistemaComunicacion(sControlSensorProximidad);
        altavoz.setSistemaComunicacion(sControlAltavoz);
        
        // Add sensors and actuators (assuming these methods exist in your PaqueteRobot classes)
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
        System.out.println("2. Ingresar a la simulación");
        System.out.println("3. Cerrar sesión");
        System.out.println("4. Salir del programa");
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
                menuRobot(usuario); // Return to the menu after action
                break;
            case 2:
                // Find the robot assigned to this user
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
                break;
            case 3:
                System.out.println("Cerrando sesión de " + usuario.getAlias() + ".");
                menuUsuario();
                break;
            case 4:
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

    // --- Robot Simulation and Environment ---
    private static void inicializarMatriz() {
        for (int i = 0; i < TAMANO_MATRIZ; i++) {
            for (int j = 0; j < TAMANO_MATRIZ; j++) {
                matrizEntorno[i][j] = '.';
            }
        }
        robotPos[0] = TAMANO_MATRIZ / 2; // Initial X position
        robotPos[1] = TAMANO_MATRIZ / 2; // Initial Y position
        robotDir[0] = '^'; // Initial direction
        matrizEntorno[robotPos[1]][robotPos[0]] = robotDir[0]; // Represent robot on matrix
    }

    private static void colocarObstaculos() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) { // Place 5 obstacles
            int obsX, obsY;
            do {
                obsX = rand.nextInt(TAMANO_MATRIZ);
                obsY = rand.nextInt(TAMANO_MATRIZ);
            } while ((obsX == robotPos[0] && obsY == robotPos[1]) || matrizEntorno[obsY][obsX] == 'X'); // Avoid robot and other obstacles
            matrizEntorno[obsY][obsX] = 'X'; // 'X' for obstacles
        }

        // Place the pet
        do {
            mascotaPos[0] = rand.nextInt(TAMANO_MATRIZ);
            mascotaPos[1] = rand.nextInt(TAMANO_MATRIZ);
        } while ((mascotaPos[0] == robotPos[0] && mascotaPos[1] == robotPos[1]) || matrizEntorno[mascotaPos[1]][mascotaPos[0]] == 'X'); // Avoid robot and obstacles
        matrizEntorno[mascotaPos[1]][mascotaPos[0]] = 'P'; // 'P' for pet
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
            for (int i = 0; i < 50; ++i) System.out.println(); // Fallback for unsupported systems
        }
    }

    /**
     * Handles user interaction and robot communication during simulation.
     * Delegates advance/turn/evasion logic to CommunicationSystems and ControlSystem.
     *
     * @param usuario The user sending the command.
     * @param robot The robot processing the command.
     * @param scanner The scanner for user input.
     */
    public static void interaccionUsuarioEnSimulacion(Usuario usuario, Robot robot, Scanner scanner) {
        String comandoUsuario = usuario.enviarComando(scanner);

        // NO se establece un comando por defecto como "w" (avanzar) si el usuario solo presiona Enter.
        // El robot solo avanzará si se le da el comando "w" explícitamente.
        if (comandoUsuario.equalsIgnoreCase("q")) {
            usuario.recibirMensaje("Orden de apagado recibida.");
            robot.apagar();
            return; // Exit simulation
        }

        if (robot.getModulos() != null && !robot.getModulos().isEmpty()) {
            // Find specific module instances from the robot's modules array
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

            // This assumes the first module is the primary control module.
            // You might need a more robust way to select the control module if your design allows multiple.
            Modulo moduloControl = robot.getModulos().get(0);

            SistemaComunicacion scModulo = moduloControl.getSistemaComunicacion();
            if (scModulo != null) {
                scModulo.setReceptor(true);
                scModulo.recibirMensaje(comandoUsuario, usuario.getId());
                scModulo.setReceptor(false);
            }

            // Pass all parameters explicitly to Modulo.procesarComando using the retrieved instances
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
        
        do {
            limpiarConsola();
            imprimirMatriz();
            System.out.println("----------------------");
            // Se especifica que el comando 'w' es para avanzar, no hay avance automático.
            System.out.println("Ingrese un comando para el robot (w: avanzar, a: girar izq, d: girar der, q: salir de simulación):");

            interaccionUsuarioEnSimulacion(usuario, robotActual, scanner);

            // If command was 'q', interaccionUsuarioEnSimulacion already exited.
            // If the robot turned off (indicated by SistemaControl's response), exit the loop.
            if (robotActual.getModulos() != null && !robotActual.getModulos().isEmpty()) {
                // Check if the control module is still active.
                if (robotActual.getModulos().get(0).getSistemaControl() != null &&
                    !robotActual.getModulos().get(0).getSistemaControl().enviarRespuestaAccion()) {
                    System.out.println("Simulación terminada por apagado del robot.");
                    break;
                }
            } else {
                System.out.println("Simulación terminada: No hay módulos para el robot.");
                break;
            }
            
            try {
                Thread.sleep(500); // Small pause to see movement
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("La simulación fue interrumpida.");
                break;
            }

        } while (true); // Infinite loop until 'q' is received or robot turns off

        System.out.println("Volviendo al menú del robot...");
        menuRobot(usuario);
    }
}