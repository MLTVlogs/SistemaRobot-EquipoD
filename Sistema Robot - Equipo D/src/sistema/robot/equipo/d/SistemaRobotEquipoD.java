package sistema.robot.equipo.d;

import PaqueteRobot.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


public class SistemaRobotEquipoD {


    private static Scanner scanner = new Scanner(System.in);
    private static int posX;
    private static int posY;
    private static char direccion;
    private static int posXMascota;
    private static int posYMascota;
    private static final int TAMANO_MATRIZ = 10;
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Robot> robots = new ArrayList<>();




    public static void main(String[] args) {
        try{
            //PRUEBAS
            Usuario usuario1 = new Usuario(1, "Usuario1", "Admin");
            usuarios.add(usuario1);
            Robot robot1 = new Robot("AAaaa", "Robot1", "Robot", 1);
            ModuloDinamicoExtension extension1 = new ModuloDinamicoExtension(0, "AAaaa", "ModuloDinamicoExtension", 32, 32, 32);
            ModuloDinamicoRotacion rotacion1 = new ModuloDinamicoRotacion(0, "AAaaa", "ModuloDinamicoRotacion", 32, 32, 32);   
            ModuloDinamicoHelicoidal helicoidal1 = new ModuloDinamicoHelicoidal(0, "AAaaa", "ModuloDinamicoHelicoidal", 32, 32, 32);
            Camara camara1 = new Camara(0, "AAaaa", "Camara", 32, 32, 32);
            SensorProximidad sensorProximidad1 = new SensorProximidad(0, "AAaaa", "SensorProximidad", 32, 32, 32);
            Altavoz altavoz1 = new Altavoz(0, "AAaaa", "Altavoz", 32, 32, 32);
            
            //Agregar modulos al robot
            robot1.agregarModulo(extension1);
            robot1.agregarModulo(rotacion1);
            robot1.agregarModulo(helicoidal1);
            robot1.agregarModulo(camara1);
            robot1.agregarModulo(sensorProximidad1);
            robot1.agregarModulo(altavoz1);

            //Asignar sistemas de control a los modulos
            extension1.setSistemaControl(new SistemaControl(extension1.getId()));
            rotacion1.setSistemaControl(new SistemaControl(rotacion1.getId()));
            helicoidal1.setSistemaControl(new SistemaControl(helicoidal1.getId()));
            camara1.setSistemaControl(new SistemaControl(camara1.getId()));
            sensorProximidad1.setSistemaControl(new SistemaControl(sensorProximidad1.getId()));
            altavoz1.setSistemaControl(new SistemaControl(altavoz1.getId()));
            
            //Crear sistemas de comunicacion para cada modulo
            SistemaComunicacion sControlExtension = new SistemaComunicacion(extension1.getId());
            sControlExtension.setReceptor(true);
            SistemaComunicacion sControlRotacion = new SistemaComunicacion(rotacion1.getId());
            sControlRotacion.setReceptor(true);
            SistemaComunicacion sControlHelicoidal = new SistemaComunicacion(helicoidal1.getId());
            sControlHelicoidal.setReceptor(true);
            SistemaComunicacion sControlCamara = new SistemaComunicacion(camara1.getId());
            sControlCamara.setEmisor(true);
            SistemaComunicacion sControlSensorProximidad = new SistemaComunicacion(sensorProximidad1.getId());
            sControlCamara.setEmisor(true);
            SistemaComunicacion sControlAltavoz = new SistemaComunicacion(altavoz1.getId());
            sControlAltavoz.setEmisor(true);

            //Asociar usuario a los sistemas de comunicacion
            sControlExtension.asociarUsuario(usuario1.getId());
            sControlRotacion.asociarUsuario(usuario1.getId());
            sControlHelicoidal.asociarUsuario(usuario1.getId());
            sControlCamara.asociarUsuario(usuario1.getId());
            sControlSensorProximidad.asociarUsuario(usuario1.getId());
            sControlAltavoz.asociarUsuario(usuario1.getId());

            //Asignar sistemas de comunicacion a los modulos
            extension1.setSistemaComunicacion(sControlExtension);
            rotacion1.setSistemaComunicacion(sControlRotacion);
            helicoidal1.setSistemaComunicacion(sControlHelicoidal);
            camara1.setSistemaComunicacion(sControlCamara);
            sensorProximidad1.setSistemaComunicacion(sControlSensorProximidad);
            altavoz1.setSistemaComunicacion(sControlAltavoz);
            
            //añadir sensores
            Sensor sensorVisual = new Sensor(35654,"CAMARA","Sensor de vision");
            Sensor sensorInfrarrojo = new Sensor(23564,"PROXIMIDAD","Sensor de proximidad");

            camara1.agregarSensor(sensorVisual);
            camara1.setNumeroSensores(camara1.getSensores().size());

            sensorProximidad1.agregarSensor(sensorInfrarrojo);
            sensorProximidad1.setNumeroSensores(sensorProximidad1.getSensores().size());

            //añadir actuadores
            Actuador bocina = new Actuador(35654,"ALTAVOZ","Actuador de bocina");
            altavoz1.agregarActuador(bocina);
            altavoz1.setNumeroActuadores(altavoz1.getActuadores().size());
            robots.add(robot1);

            //numero de motores predeterminados
            extension1.setNumeroMotores(1);
            rotacion1.setNumeroMotores(1);
            helicoidal1.setNumeroMotores(1);

            char[][] miMatriz = new char[TAMANO_MATRIZ][TAMANO_MATRIZ];
            actualizarMatriz(robot1, miMatriz);

            //menuUsuario();
        } catch (Exception e) {
            // Manejo de excepciones para capturar errores inesperados
            System.out.println("Error: " + e.getMessage());
        } finally {
             // Asegura que el programa se cierre correctamente
            System.exit(0);
        }
        
    }

    private static void menuUsuario() {
        System.out.println("BIENVENIDO AL SISTEMA DE ROBOT");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.println("Ingrese una opción:");
        int opcion = scanner.nextInt();
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
                System.out.println("Opción inválida");
                menuUsuario();
                break;
        }
    }
    
//---------------------------------------------------------------------------------------------------------------------------


    private static void iniciarSesion() {
        System.out.println("INICIAR SESIÓN");
        // Verifica si hay usuarios registrados antes de solicitar el inicio de sesión
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            System.out.println("Presione enter para volver al menú...");
            scanner.nextLine(); // Espera a que el usuario presione enter
            menuUsuario(); // Regresa al menú de usuario
            return;
        }
        System.out.println("Ingrese su Alias:");
        String nombreUsuario = scanner.next();
        System.out.println("Ingrese su id:");
        int idUsuario = scanner.nextInt();
        if (verificarUsuario(nombreUsuario,idUsuario)){
            System.out.println("Inicio de sesión exitoso");
            menuRobot(idUsuario);
        } else {
            System.out.println("Usuario no encontrado");
            menuUsuario();
        }
    }

    private static boolean verificarUsuario(String aliasUsuario, int idUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getAlias().equals(aliasUsuario) && usuario.getId() == idUsuario) {
                return true; // Retorna true solo si encuentra una coincidencia.
            }
        }
        return false; // Retorna false si no encontró coincidencias después de recorrer toda la lista.
    }

    private static void registrarse() {
        System.out.println("REGISTRARSE");
        System.out.println("Ingrese su Alias:");
        String aliasUsuario = scanner.next();
        System.out.println("Ingrese su id:");
        int idUsuario = scanner.nextInt();
        System.out.println("Ingrese su tipo de usuario");
        String tipoUsuario = scanner.next();
        usuarios.add(new Usuario(idUsuario,aliasUsuario, tipoUsuario));
        System.out.println("Registro exitoso");
        asignarRobotUsuario(idUsuario);
        menuUsuario();
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void asignarRobotUsuario(int idUsuario) {
        System.out.println("Ingrese el alias del robot:");
        String alias = scanner.next();
        Robot robotUsuario = new Robot("AAaaa",alias,"Robot",idUsuario);

        ModuloDinamicoExtension extension = new ModuloDinamicoExtension(3,"AAaaa", "ModuloDinamicoExtension", 32, 32, 32);
        
        ModuloDinamicoRotacion rotacion = new ModuloDinamicoRotacion( 5,"AAaaa", "ModuloDinamicoRotacion", 32, 32, 32);
           
        ModuloDinamicoHelicoidal helicoidal = new ModuloDinamicoHelicoidal( 6,"AAaaa", "ModuloDinamicoHelicoidal", 32, 32, 32);
        
        Camara camara = new Camara( 6,"AAaaa", "Camara", 32, 32, 32);
        
        SensorProximidad sensorProximidad = new SensorProximidad( 3,"AAaaa", "SensorProximidad", 32, 32, 32);
        
        Altavoz altavoz = new Altavoz( 6,"AAaaa", "Altavoz", 32, 32, 32);

        //Agregar modulos al robot
        robotUsuario.agregarModulo(extension);
        robotUsuario.agregarModulo(rotacion);
        robotUsuario.agregarModulo(helicoidal);
        robotUsuario.agregarModulo(camara);
        robotUsuario.agregarModulo(sensorProximidad);
        robotUsuario.agregarModulo(altavoz);

        //Asignar sistemas de control a los modulos
        extension.setSistemaControl(new SistemaControl(extension.getId()));
        rotacion.setSistemaControl(new SistemaControl(rotacion.getId()));
        helicoidal.setSistemaControl(new SistemaControl(helicoidal.getId()));
        camara.setSistemaControl(new SistemaControl(camara.getId()));
        sensorProximidad.setSistemaControl(new SistemaControl(sensorProximidad.getId()));
        altavoz.setSistemaControl(new SistemaControl(altavoz.getId()));
        
        //Crear sistemas de comunicacion para cada modulo
        SistemaComunicacion sControlExtension = new SistemaComunicacion(extension.getId());
        sControlExtension.setReceptor(true);
        SistemaComunicacion sControlRotacion = new SistemaComunicacion(rotacion.getId());
        sControlRotacion.setReceptor(true);
        SistemaComunicacion sControlHelicoidal = new SistemaComunicacion(helicoidal.getId());
        sControlHelicoidal.setReceptor(true);
        SistemaComunicacion sControlCamara = new SistemaComunicacion(camara.getId());
        sControlCamara.setEmisor(true);
        SistemaComunicacion sControlSensorProximidad = new SistemaComunicacion(sensorProximidad.getId());
        sControlCamara.setEmisor(true);
        SistemaComunicacion sControlAltavoz = new SistemaComunicacion(altavoz.getId());
        sControlAltavoz.setEmisor(true);

        //Asociar usuario a los sistemas de comunicacion
        sControlExtension.asociarUsuario(idUsuario);
        sControlRotacion.asociarUsuario(idUsuario);
        sControlHelicoidal.asociarUsuario(idUsuario);
        sControlCamara.asociarUsuario(idUsuario);
        sControlSensorProximidad.asociarUsuario(idUsuario);
        sControlAltavoz.asociarUsuario(idUsuario);

        //Asignar sistemas de comunicacion a los modulos
        extension.setSistemaComunicacion(sControlExtension);
        rotacion.setSistemaComunicacion(sControlRotacion);
        helicoidal.setSistemaComunicacion(sControlHelicoidal);
        camara.setSistemaComunicacion(sControlCamara);
        sensorProximidad.setSistemaComunicacion(sControlSensorProximidad);
        altavoz.setSistemaComunicacion(sControlAltavoz);
        
        //añadir sensores
        Sensor sensorVisual = new Sensor(35654,"CAMARA","Sensor de vision");
        Sensor sensorInfrarrojo = new Sensor(23564,"PROXIMIDAD","Sensor de proximidad");

        camara.agregarSensor(sensorVisual);
        camara.setNumeroSensores(camara.getSensores().size());

        sensorProximidad.agregarSensor(sensorInfrarrojo);
        sensorProximidad.setNumeroSensores(sensorProximidad.getSensores().size());

        //añadir actuadores
        Actuador bocina = new Actuador(35654,"ALTAVOZ","Actuador de bocina");
        altavoz.agregarActuador(bocina);
        altavoz.setNumeroActuadores(altavoz.getActuadores().size());

        //numero de motores predeterminados
        extension.setNumeroMotores(1);
        rotacion.setNumeroMotores(1);
        helicoidal.setNumeroMotores(1);

        robots.add(robotUsuario);
        
        System.out.println("Robot asignado exitosamente");
    }

    private static void menuRobot(int idUsuario) {
        System.out.println("Bienvenido al menú del robot");
        System.out.println("1. Cambiar alias del robot");
        System.out.println("2. Ingresar a la simulación");
        System.out.println("3. Cerrar sesión");
        System.out.println("4. Salir del programa");
        System.out.println("Ingrese una opción:");
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                cambiarAlias(idUsuario);
                break;
            case 2:
                ingresarSimulacion(idUsuario);
                break;
            case 3:
                menuUsuario();
                break;
            case 4:
                System.out.println("Saliendo del programa...");
                System.exit(0);
                break;
        }
    }

    private static void cambiarAlias(int idUsuario) {
        System.out.println("Ingrese el nuevo alias:");
        String alias = scanner.next();
        for (Robot robot : robots) {
            if (robot.getIdUsuario() == idUsuario) {
                robot.setAlias(alias);
                System.out.println("Alias cambiado exitosamente");
                menuRobot(idUsuario);
                break;
            }
        }
    }

    private static void ingresarSimulacion(int idUsuario) {
        System.out.println("Ingresando a la simulación...");
        char[][] miMatriz = new char[TAMANO_MATRIZ][TAMANO_MATRIZ];
        for (Robot robot : robots) {
            if (robot.getIdUsuario() == idUsuario) {
                System.out.println("Bienvenido, " + robot.getAlias() + "!");
                robot.encender();
                actualizarMatriz(robot,miMatriz);
                break; // Sale del bucle una vez que encuentra el robot del usuario
            }
        }
    }


//Parte que controla la simulacion del robot y la matriz donde se mueve el robot, imprime mascota y obstaculos
//----------------------------------------------------------------------------------------------------------------------------------------
private static void inicializarMatriz(char[][] matriz) {
    for (int i = 0; i < TAMANO_MATRIZ; i++) {
        for (int j = 0; j < TAMANO_MATRIZ; j++) {
            matriz[i][j] = '.'; // Inicializa la matriz con espacios vacíos
        }
    }
    // Posición inicial del robot
    posX = TAMANO_MATRIZ / 2;
    posY = TAMANO_MATRIZ / 2;
    direccion = '^';
    matriz[posY][posX] = direccion;
}

private static void colocarObstaculos(char[][] matriz) {
    Random rand = new Random();
    // Colocar algunos obstáculos aleatorios
    for (int i = 0; i < 5; i++) {
        matriz[rand.nextInt(TAMANO_MATRIZ)][rand.nextInt(TAMANO_MATRIZ)] = 'O';
    }
    // Colocar la mascota
    posXMascota = rand.nextInt(TAMANO_MATRIZ);
    posYMascota = rand.nextInt(TAMANO_MATRIZ);
    matriz[posYMascota][posXMascota] = 'P';
}

private static void imprimirMatriz(char[][] matriz) {
    System.out.println("--- Mapa del Robot ---");
    for (int i = 0; i < TAMANO_MATRIZ; i++) {
        for (int j = 0; j < TAMANO_MATRIZ; j++) {
            System.out.print(matriz[i][j] + " ");
        }
        System.out.println();
    }
    System.out.println("----------------------");
}

private static int[] calcularPosicionProximidad() {
    int xprox = posX;
    int yprox = posY;
    switch (direccion) {
        case '^':
            yprox = posY - 1;
            break;
        case 'v':
            yprox = posY + 1;
            break;
        case '<':
            xprox = posX - 1;
            break;
        case '>':
            xprox = posX + 1;
            break;
    }
    return new int[]{xprox, yprox};
}

private static void moverRobotEnMatriz(int nuevoPosX, int nuevoPosY, char[][] matriz) {
    matriz[posY][posX] = '.'; // Limpia la posición anterior
    posX = nuevoPosX;
    posY = nuevoPosY;
    matriz[posY][posX] = direccion; // Actualiza la nueva posición
}

private static void girarIzquierda() {
    switch (direccion) {
        case '^':
            direccion = '<';
            break;
        case '<':
            direccion = 'v';
            break;
        case 'v':
            direccion = '>';
            break;
        case '>':
            direccion = '^';
            break;
    }
}

private static void girarDerecha() {
    switch (direccion) {
        case '^':
            direccion = '>';
            break;
        case '>':
            direccion = 'v';
            break;
        case 'v':
            direccion = '<';
            break;
        case '<':
            direccion = '^';
            break;
    }
}

// --- Lógica principal de la simulación ---

private static void manejarMovimiento(SensorProximidad sensorProximidad, Camara camara, ModuloDinamicoExtension extension, char[][] matriz, int xprox, int yprox) {
    int resProximidad = sensorProximidad.procesarDatos(sensorProximidad.captarInformacion(yprox, xprox, matriz));
    int resCamara = camara.procesarDatos(camara.captarInformacion(yprox, xprox, matriz));

    if (resProximidad == 0 && resCamara == 3) {
        System.out.println("SENSOR DE PROXIMIDAD: No hay obstáculo adelante.");
        System.out.println("CÁMARA: Camino libre.");
        int movimiento = extension.moverse(new float[]{(float) (xprox - posX), (float) (yprox - posY)});
        if (movimiento == 1) { // Arriba
            System.out.println("El robot se ha movido arriba.");
            moverRobotEnMatriz(posX, posY - 1, matriz);
        } else if (movimiento == -1) { // Abajo
            System.out.println("El robot se ha movido abajo.");
            moverRobotEnMatriz(posX, posY + 1, matriz);
        } else if (movimiento == 2) { // Derecha
            System.out.println("El robot se ha movido hacia la derecha.");
            moverRobotEnMatriz(posX + 1, posY, matriz);
        } else if (movimiento == -2) { // Izquierda
            System.out.println("El robot se ha movido hacia la izquierda.");
            moverRobotEnMatriz(posX - 1, posY, matriz);
        } else {
            System.out.println("Movimiento no válido.");
        }
    } else {
        System.out.println("Resultados de los sensores: Proximidad=" + resProximidad + ", Cámara=" + resCamara);
        System.out.println("El robot no puede avanzar en esta dirección.");
    }
}

private static void manejarInteraccionObstaculo(int resProximidad, int resCamara, Altavoz altavoz, ModuloDinamicoRotacion rotacion, ModuloDinamicoHelicoidal helicoidal, char[][] matriz) {
    if (resProximidad == 1 && resCamara == 1) {
        System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante.");
        System.out.println("CÁMARA: Mascota detectada.");
        if (altavoz.realizarAccion() == 1) {
            System.out.println("El robot ha emitido un sonido para alejar a la mascota.");
            matriz[posYMascota][posXMascota] = '.'; // Limpia la posición anterior de la mascota
            posXMascota = new Random().nextInt(TAMANO_MATRIZ);
            posYMascota = new Random().nextInt(TAMANO_MATRIZ);
            matriz[posYMascota][posXMascota] = 'P'; // Actualiza la posición de la mascota
        } else {
            System.out.println("No se pudo emitir el sonido.");
        }
    } else if (resProximidad == 1 && resCamara == 2) {
        System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante.");
        System.out.println("CÁMARA: Obstáculo detectado.");
        int[] rotaciones = {1, -1}; // 1 para girar derecha, -1 para girar izquierda
        int giro = rotacion.moverse(new float[]{rotaciones[new Random().nextInt(rotaciones.length)]});
        if (giro == 1) {
            System.out.println("El robot ha girado a la derecha.");
            girarDerecha();
            matriz[posY][posX] = direccion;
        } else if (giro == -1) {
            System.out.println("El robot ha girado a la izquierda.");
            girarIzquierda();
            matriz[posY][posX] = direccion;
        } else {
            System.out.println("Giro no válido.");
        }
    } else if (resProximidad == 0 && resCamara == 4) {
        System.out.println("SENSOR DE PROXIMIDAD: No hay obstáculo adelante.");
        System.out.println("CÁMARA: Sensor fuera de los límites.");
        int[] rotaciones = {1, -1}; // 1 para girar derecha, -1 para girar izquierda
        int giro = helicoidal.moverse(new float[]{rotaciones[new Random().nextInt(rotaciones.length)]});
        if (giro == 1) {
            System.out.println("El robot ha girado a la derecha.");
            girarDerecha();
            matriz[posY][posX] = direccion;
        } else if (giro == -1) {
            System.out.println("El robot ha girado a la izquierda.");
            girarIzquierda();
            matriz[posY][posX] = direccion;
        } else {
            System.out.println("Giro no válido.");
        }
    } else {
        System.out.println("Resultados inesperados de los sensores.");
    }
}

private static void procesarEntradaUsuario(char opcion, Scanner scanner, Robot robot,
                                           SensorProximidad sensorProximidad, Camara camara, Altavoz altavoz,
                                           ModuloDinamicoExtension extension, ModuloDinamicoRotacion rotacion,
                                           ModuloDinamicoHelicoidal helicoidal, char[][] matriz, int xprox, int yprox) {
    switch (opcion) {
        case 'w':
            int resProximidad = sensorProximidad.procesarDatos(sensorProximidad.captarInformacion(yprox, xprox, matriz));
            int resCamara = camara.procesarDatos(camara.captarInformacion(yprox, xprox, matriz));

            if (resProximidad == 0 && resCamara == 3) {
                manejarMovimiento(sensorProximidad, camara, extension, matriz, xprox, yprox);
            } else {
                manejarInteraccionObstaculo(resProximidad, resCamara, altavoz, rotacion, helicoidal, matriz);
            }
            break;
        case 'a':
            rotacion.moverse(new float[]{-1});
            System.out.println("El robot ha girado a la izquierda.");
            girarIzquierda();
            matriz[posY][posX] = direccion;
            break;
        case 'd':
            rotacion.moverse(new float[]{1});
            System.out.println("El robot ha girado a la derecha.");
            girarDerecha();
            matriz[posY][posX] = direccion;
            break;
        case 'q':
            scanner.close();
            System.out.println("Saliendo de la simulación...");
            robot.apagar();
            System.exit(0); // Termina la aplicación
            break;
        default:
            System.out.println("Opción no válida. Intente de nuevo.");
            break;
    }
}

public static void actualizarMatriz(Robot robot, char[][] matriz) {
    // Encontrar los módulos del robot
    SensorProximidad sensorProximidad = null;
    Camara camara = null;
    Altavoz altavoz = null;
    ModuloDinamicoExtension extension = null;
    ModuloDinamicoRotacion rotacion = null;
    ModuloDinamicoHelicoidal helicoidal = null;

    for (Modulo modulo : robot.getModulos()) {
        if (modulo instanceof SensorProximidad) {
            sensorProximidad = (SensorProximidad) modulo;
        } else if (modulo instanceof Camara) {
            camara = (Camara) modulo;
        } else if (modulo instanceof Altavoz) {
            altavoz = (Altavoz) modulo;
        } else if (modulo instanceof ModuloDinamicoExtension) {
            extension = (ModuloDinamicoExtension) modulo;
        } else if (modulo instanceof ModuloDinamicoRotacion) {
            rotacion = (ModuloDinamicoRotacion) modulo;
        } else if (modulo instanceof ModuloDinamicoHelicoidal) {
            helicoidal = (ModuloDinamicoHelicoidal) modulo;
        }
    }

    if (sensorProximidad == null || camara == null || altavoz == null ||
        extension == null || rotacion == null || helicoidal == null) {
        System.err.println("Error: Faltan módulos esenciales en el robot.");
        return;
    }

    robot.encender();
    inicializarMatriz(matriz);
    colocarObstaculos(matriz);

    Scanner scanner = new Scanner(System.in);

    do {
        imprimirMatriz(matriz);
        int[] proxCoords = calcularPosicionProximidad();
        int xprox = proxCoords[0];
        int yprox = proxCoords[1];

        System.out.println("\nPresione 'w' para avanzar");
        System.out.println("Presione 'a' para girar a la izquierda");
        System.out.println("Presione 'd' para girar a la derecha");
        System.out.println("Presione 'q' para salir de la simulación");
        System.out.print("Ingrese su opción: ");

        char opcion = scanner.next().charAt(0);
        procesarEntradaUsuario(opcion, scanner, robot, sensorProximidad, camara, altavoz, extension, rotacion, helicoidal, matriz, xprox, yprox);

    } while (true);
    }
}