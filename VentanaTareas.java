import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.*;

public class VentanaTareas extends JFrame {
private GestorDeTareas gestor;
    private UsuarioObservador observador;

    // Componentes
    private JTextField campoNombre;
    private JTextField campoPrioridad;
    private JTextField campoEstado;
    private JTextArea areaTareas;
    private JButton botonAgregar;
    private JButton botonOrdenarPrioridad;
    private JButton botonOrdenarEstado;

    public VentanaTareas() {
        super("Gestor de tareas");

        gestor = new GestorDeTareas();
        observador = new UsuarioObservador("Usuario");

        // Configuramos la ventana
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para los campos de texto y botones
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(4, 2));

        // Campos
        panelEntrada.add(new JLabel("Nombre de tarea:"));
        campoNombre = new JTextField();
        panelEntrada.add(campoNombre);

        panelEntrada.add(new JLabel("Prioridad (número):"));
        campoPrioridad = new JTextField();
        panelEntrada.add(campoPrioridad);

        panelEntrada.add(new JLabel("Estado (Pendiente, En progreso, Completada):"));
        campoEstado = new JTextField();
        panelEntrada.add(campoEstado);

        botonAgregar = new JButton("Agregar tarea");
        panelEntrada.add(botonAgregar);

        // Panel de botones de ordenamiento
        JPanel panelBotones = new JPanel();
        botonOrdenarPrioridad = new JButton("Ordenar por prioridad");
        botonOrdenarEstado = new JButton("Ordenar por estado");
        panelBotones.add(botonOrdenarPrioridad);
        panelBotones.add(botonOrdenarEstado);

        // Área de texto para mostrar tareas
        areaTareas = new JTextArea();
        areaTareas.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTareas);

        // Agregamos todo a la ventana
        add(panelEntrada, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción del botón de agregar
        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                int prioridad;
                try {
                    prioridad = Integer.parseInt(campoPrioridad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prioridad debe ser un número entero.");
                    return;
                }
                String estado = campoEstado.getText();
                if (!estado.equals("Pendiente") && !estado.equals("En progreso") && !estado.equals("Completada")) {
                    JOptionPane.showMessageDialog(null, "Estado inválido. Usa: Pendiente, En progreso o Completada.");
                    return;
                }
                Tarea nueva = new Tarea(nombre, prioridad);
                nueva.setEstado(estado);
                nueva.agregarObservador(observador);
                gestor.agregarTarea(nueva);
                mostrarTareas();
            }
        });

        // Botón para ordenar por prioridad
        botonOrdenarPrioridad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestor.setEstrategia(new OrdenarPorPrioridad());
                gestor.ordenarTareas();
                mostrarTareas();
            }
        });

        // Botón para ordenar por estado
        botonOrdenarEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestor.setEstrategia(new OrdenarPorEstado());
                gestor.ordenarTareas();
                mostrarTareas();
            }
        });

        setVisible(true);
    }

    // Método para mostrar todas las tareas en el área de texto
    private void mostrarTareas() {
        areaTareas.setText("");
        for (Tarea t : gestor.getTareas()) {
            areaTareas.append(t.toString() + "\n");
        }
    }

    // Método principal para lanzar la interfaz
    public static void main(String[] args) {
        new VentanaTareas();
    }
}
