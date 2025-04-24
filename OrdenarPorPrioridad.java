import java.util.Comparator;
import java.util.List;

public class OrdenarPorPrioridad implements EstrategiaOrdenamiento {
public void ordenar(List<Tarea> tareas) {
        tareas.sort(Comparator.comparingInt(Tarea::getPrioridad));
    }

}
