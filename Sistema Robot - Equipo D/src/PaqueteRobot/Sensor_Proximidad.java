package PaqueteRobot;
/*
NOTA: No est� terminada la clase
*/
import java.util.ArrayList;
import java.util.List;

public class Sensor_Proximidad extends Percepcion implements InterfazSensor{
    /*private List<Sensor> Sensores = new ArrayList<>();*/ // Lista - Ensamblado de Agregaci�n
    
    // CONSTRUCTOR 1
    public Sensor_Proximidad(){  
    }
    
    // AGREGAR COMPONENTES
    /*public void agregar_Sensor(Sensor sen){
        Sensores.add(sen);
    }*/
    
    // OPERACIONES
    @Override
    public int procesar_datos(){
        return 0;
    }
    
}
