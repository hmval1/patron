public class Main {
    public static void main(String[] args) {
        GestorDeTareas gestor = new GestorDeTareas();

        // Observador
        UsuarioObservador usuario1 = new UsuarioObservador("Carlos");

        // Tareas
        Tarea t1 = new Tarea("Entregar reporte", 3);
        Tarea t2 = new Tarea("Hacer examen", 1);
        Tarea t3 = new Tarea("Estudiar patrones", 2);

        // Asociar observador a cada tarea
        t1.agregarObservador(usuario1);
        t2.agregarObservador(usuario1);
        t3.agregarObservador(usuario1);

        // Agregar tareas al gestor
        gestor.agregarTarea(t1);
        gestor.agregarTarea(t2);
        gestor.agregarTarea(t3);

        // Mostrar tareas sin ordenar
        System.out.println("Tareas antes de ordenar:");
        gestor.mostrarTareas();

        // Ordenar por prioridad
        gestor.setEstrategia(new OrdenarPorPrioridad());
        gestor.ordenarTareas();
        System.out.println("\nTareas ordenadas por prioridad:");
        gestor.mostrarTareas();

        // Cambiar estado de una tarea
        t1.setEstado("En progreso");
        t2.setEstado("Completada");

        // Ordenar por estado
        gestor.setEstrategia(new OrdenarPorEstado());
        gestor.ordenarTareas();
        System.out.println("\nTareas ordenadas por estado:");
        gestor.mostrarTareas();

