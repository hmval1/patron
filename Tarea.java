import java.util.ArrayList;
import java.util.List;

class Tarea {
    private String nombre;
    private int prioridad;
    private String estado; // Pendiente, En progreso, Completada
    private List<Observador> observadores = new ArrayList<>();

    public Tarea(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.estado = "Pendiente";
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores();
    }

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar(this);
        }
    }

    @Override
    public String toString() {
        return nombre + " (Prioridad: " + prioridad + ", Estado: " + estado + ")";
    }
}

