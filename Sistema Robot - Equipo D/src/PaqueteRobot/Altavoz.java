package PaqueteRobot;
/*
NOTA: No est� terminada la clase
*/
import java.util.ArrayList;
import java.util.List;

public class Altavoz extends Actuacion implements InterfazActuador{
    private List<Actuador> Actuadores = new ArrayList<>(); // Lista - Ensamblado de Agregaci�n
    
    // CONSTRUCTOR 1
    public Altavoz(){
    }
    
    // AGREGAR COMPONENTES
    public void agregar_Actuador(Actuador act){
        Actuadores.add(act); // Agregar Actuador a la lista
    }
    
    // OPERACIONES
    @Override
    public int realizar_accion(){
        return 0;
    }
}
