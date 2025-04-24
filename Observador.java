interface Observador {
    void actualizar(Tarea tarea);
}

// Observador concreto
class UsuarioObservador implements Observador {
    private String nombre;

    public UsuarioObservador(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(Tarea tarea) {
        System.out.println("[Notificación a " + nombre + "]: La tarea '" + tarea.getNombre() + "' cambió su estado a " + tarea.getEstado());
    }
}

