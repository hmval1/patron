import java.util.*;
import java.util.Comparator;

class OrdenarPorEstado implements EstrategiaOrdenamiento {
    public void ordenar(List<Tarea> tareas) {
        tareas.sort(Comparator.comparing(Tarea::getEstado));
    }
}

