package sistema.robot.equipo.d;

import PaqueteRobot.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


public class SistemaRobotEquipoD {

    private static final int TAMANO_MATRIZ = 10;
    private static char[][] matriz = new char[TAMANO_MATRIZ][TAMANO_MATRIZ];
    private static Scanner scanner = new Scanner(System.in);
    private static int posX = TAMANO_MATRIZ / 2;  // Posición inicial central
    private static int posY = TAMANO_MATRIZ / 2;
    private static int posXMascota = 3; // Posición de la mascota
    private static int posYMascota = 6; // Posición de la mascota
    private static char direccion = '^';  // '^': arriba, 'v': abajo, '<': izquierda, '>': derecha
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

            ingresarSimulacion(usuario1.getId());

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
        SistemaComunicacion sControlRotacion = new SistemaComunicacion(rotacion.getId());
        SistemaComunicacion sControlHelicoidal = new SistemaComunicacion(helicoidal.getId());
        SistemaComunicacion sControlCamara = new SistemaComunicacion(camara.getId());
        SistemaComunicacion sControlSensorProximidad = new SistemaComunicacion(sensorProximidad.getId());
        SistemaComunicacion sControlAltavoz = new SistemaComunicacion(altavoz.getId());

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
        for (Robot robot : robots) {
            if (robot.getIdUsuario() == idUsuario) {
                System.out.println("Bienvenido, " + robot.getAlias() + "!");
                robot.encender();
                actualizarMatriz(robot,matriz);
                break; // Sale del bucle una vez que encuentra el robot del usuario
            }
        }
    }


//Parte que controla la simulacion del robot y la matriz donde se mueve el robot, imprime mascota y obstaculos
//----------------------------------------------------------------------------------------------------------------------------------------
    private static void inicializarMatriz() {
        for (int i = 0; i < TAMANO_MATRIZ; i++) {
            for (int j = 0; j < TAMANO_MATRIZ; j++) {
                matriz[i][j] = '.';
            }
        }
        matriz[posY][posX] = direccion;  // Posición inicial del robot
    }

    private static void colocarObstaculos() {
        matriz[2][2] = 'O';  // Obstáculo
        matriz[5][7] = 'O';
        matriz[8][3] = 'O';
        matriz[posYMascota][posXMascota] = 'P';  // Mascota
    }

    private static void imprimirMatriz() {
        System.out.println("\nEstado del entorno:");
        for (int i = 0; i < TAMANO_MATRIZ; i++) {
            for (int j = 0; j < TAMANO_MATRIZ; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void girarDerecha() {
        switch (direccion) {
            case '^': direccion = '>'; break;
            case '>': direccion = 'v'; break;
            case 'v': direccion = '<'; break;
            case '<': direccion = '^'; break;
        }
        matriz[posY][posX] = direccion;
        System.out.println("Dirección actual: " + direccion);
    }

    private static void girarIzquierda() {
        switch (direccion) {
            case '^': direccion = '<'; break;
            case '<': direccion = 'v'; break;
            case 'v': direccion = '>'; break;
            case '>': direccion = '^'; break;
        }
        matriz[posY][posX] = direccion;
        System.out.println("Dirección actual: " + direccion);
    }

    private static void actualizarMatriz(Robot robot, char[][] matriz) {
        int id = 0, resProximidad = 0, resCamara = 0, xprox = 0, yprox = 0, movimiento = 0, giro = 0;
        String mensaje = null;
        SensorProximidad sensorProximidad = null;
        Camara camara = null;
        Altavoz altavoz = null;
        ModuloDinamicoExtension extension = null;
        ModuloDinamicoRotacion rotacion = null;
        ModuloDinamicoHelicoidal helicoidal = null;

        // Buscar los módulos del robot
        for(Modulo modulo : robot.getModulos()){
            if(modulo instanceof SensorProximidad){
                sensorProximidad = (SensorProximidad) modulo;
            }
            if (modulo instanceof Camara){
                camara = (Camara) modulo;
            }
            if (modulo instanceof Altavoz){
                altavoz = (Altavoz) modulo;
            }
            if (modulo instanceof ModuloDinamicoExtension){
                extension = (ModuloDinamicoExtension) modulo;
            }
            if (modulo instanceof ModuloDinamicoRotacion){
                rotacion = (ModuloDinamicoRotacion) modulo;
            }
            if (modulo instanceof ModuloDinamicoHelicoidal){
                helicoidal = (ModuloDinamicoHelicoidal) modulo;
            }
        }
        inicializarMatriz();
        colocarObstaculos();
        do{
            imprimirMatriz();
            if(direccion == '^'){
                xprox = posX; // Mantiene la posición X
                yprox = posY - 1; // Avanza hacia arriba
            } else if (direccion == 'v'){
                xprox = posX; // Mantiene la posición X
                yprox = posY + 1; // Avanza hacia abajo
            } else if (direccion == '<'){
                yprox = posY; // Mantiene la posición Y
                xprox = posX - 1; // Avanza hacia la izquierda
            } else if (direccion == '>'){
                yprox = posY; // Mantiene la posición Y
                xprox = posX + 1; // Avanza hacia la derecha
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Presione w para avanzar");
            System.out.println("Presione a para girar a la izquierda");
            System.out.println("Presione d para girar a la derecha");
            System.out.println("Presione q para salir de la simulación");
            char opcion = scanner.next().charAt(0);

            if (opcion == 'w'){
                // Capturar información de los sensores
                resProximidad = sensorProximidad.procesarDatos(sensorProximidad.captarInformacion(yprox, xprox, matriz));
                resCamara = camara.procesarDatos(camara.captarInformacion(yprox, xprox, matriz));

                if(resProximidad == 0 && resCamara == 3){
                    System.out.println("SENSOR DE PROXIMIDAD: No hay obstáculo adelante.");
                    System.out.println("CAMARA: Camino libre.");
                    // Avanzar el robot
                    movimiento = extension.moverse(new float[] {(float)(xprox-posX), (float)(yprox-posY)});
                    if(movimiento == 1){
                        System.out.println("El robot se ha movido arriba");
                        matriz[posY][posX] = '.'; // Limpia la posición anterior
                        posY-=1; // Mueve el robot hacia arriba
                        matriz[posY][posX] = direccion; // Actualiza la matriz
                    } else if(movimiento == -1){
                        System.out.println("El robot se ha movido abajo");
                        matriz[posY][posX] = '.'; // Limpia la posición anterior
                        posY+=1; // Mueve el robot hacia abajo
                        matriz[posY][posX] = direccion; // Actualiza la matriz
                    } else if(movimiento == 2){
                        System.out.println("El robot se ha movido hacia la derecha");
                        matriz[posY][posX] = '.'; // Limpia la posición anterior
                        posX+=1; // Mueve el robot hacia la derecha
                        matriz[posY][posX] = direccion; // Actualiza la matriz
                    } else if(movimiento == -2){
                        System.out.println("El robot se ha movido hacia la izquierda");
                        matriz[posY][posX] = '.'; // Limpia la posición anterior
                        posX-=1; // Mueve el robot hacia la izquierda
                        matriz[posY][posX] = direccion; // Actualiza la matriz
                    } else {
                        System.out.println("Movimiento no válido.");
                    }
                } else if (resProximidad == 1 && resCamara == 1) {
                    System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante.");
                    System.out.println("CAMARA: Mascota detectada.");
                    if(altavoz.realizarAccion() == 1){
                        System.out.println("El robot ha emitido un sonido para alejar a la mascota.");
                        // Logica para mover a la mascota
                        matriz[posYMascota][posXMascota] = '.'; // Limpia la posición anterior de la mascota
                        posXMascota = new Random().nextInt(TAMANO_MATRIZ);
                        posYMascota = new Random().nextInt(TAMANO_MATRIZ);
                        matriz[posYMascota][posXMascota] = 'P'; // Actualiza la posición de la mascota
                    } else {
                        System.out.println("No se pudo emitir el sonido.");
                    }
                } else if (resProximidad == 1 && resCamara == 2) {
                    System.out.println("SENSOR DE PROXIMIDAD: Hay un obstáculo adelante.");
                    System.out.println("CAMARA: Obstáculo detectado.");
                    // Algoritmo para evitar el obstáculo
                    int[] rotaciones = {1, -1}; // 1 para girar derecha, -1 para girar izquierda
                    giro = rotacion.moverse(new float[] {rotaciones[new Random().nextInt(rotaciones.length)]});
                    if(giro == 1) {
                        System.out.println("El robot ha girado a la derecha.");
                        girarDerecha();
                    } else if (giro == -1) {
                        System.out.println("El robot ha girado a la izquierda.");
                        girarIzquierda();
                    } else {
                        System.out.println("Giro no válido.");
                    }
                } else if (resProximidad == 0 && resCamara == 4) {
                    System.out.println("SENSOR DE PROXIMIDAD: No hay obstáculo adelante.");
                    System.out.println("CAMARA: Sensor fuera de los límites.");
                    // Algoritmo para evitar el obstáculo
                    int[] rotaciones = {1, -1}; // 1 para girar derecha, -1 para girar izquierda
                    giro = helicoidal.moverse(new float[] {rotaciones[new Random().nextInt(rotaciones.length)]});
                    if(giro == 1) {
                        System.out.println("El robot ha girado a la derecha.");
                        girarDerecha();
                    } else if (giro == -1) {
                        System.out.println("El robot ha girado a la izquierda.");
                        girarIzquierda();
                    } else {
                        System.out.println("Giro no válido.");
                    }
                } else {
                    System.out.println("Resultados inesperados de los sensores.");
                }
            } else if (opcion == 'a'){
                // Gira a la izquierda
                rotacion.moverse(new float[] {-1}); // -1 para girar a la izquierda
                System.out.println("El robot ha girado a la izquierda.");
                girarIzquierda();
            } else if (opcion == 'd'){
                // Gira a la derecha
                rotacion.moverse(new float[] {1}); // 1 para girar a la derecha
                System.out.println("El robot ha girado a la derecha.");
                girarDerecha();
            } else if (opcion == 'q'){
                scanner.close();
                System.out.println("Saliendo de la simulación...");
                robot.apagar();
                return; // Sale del bucle y termina la simulación
            
            }  else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while(true);
    }
    
    /*public static void actualizarMatriz(Robot robot) {
        inicializarMatriz();
        colocarObstaculos();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            imprimirMatriz();
            System.out.println("\nOpciones:");
            System.out.println("1. Mover adelante");
            System.out.println("2. Girar derecha");
            System.out.println("3. Girar izquierda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }*/
}
