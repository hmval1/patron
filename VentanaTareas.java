import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaTareas extends JFrame {
    private GestorDeTareas gestor;
    private UsuarioObservador observador;

    private JTextField campoNombre;
    private JTextField campoPrioridad;
    private JComboBox<String> comboEstado;
    private JTextArea areaTareas;
    private JButton botonAgregar;
    private JButton botonOrdenarPrioridad;
    private JButton botonOrdenarEstado;

    public VentanaTareas() {
        super("Gestor de tareas");
        gestor = new GestorDeTareas();
        observador = new UsuarioObservador("Usuario");

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel para los campos de entrada y botón agregar
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(4, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Nombre de la tarea:"));
        campoNombre = new JTextField();
        panelEntrada.add(campoNombre);

        panelEntrada.add(new JLabel("Prioridad (número):"));
        campoPrioridad = new JTextField();
        panelEntrada.add(campoPrioridad);

        panelEntrada.add(new JLabel("Estado (Pendiente, En progreso, Completada):"));
        comboEstado = new JComboBox<>(new String[] {"Pendiente", "En progreso", "Completada"});
        panelEntrada.add(comboEstado);

        botonAgregar = new JButton("Agregar tarea");
        panelEntrada.add(botonAgregar);

        // Espacio vacío para alinear
        panelEntrada.add(new JLabel());

        // Área de tareas y scroll
        areaTareas = new JTextArea(10, 30);
        areaTareas.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTareas);

        // Panel para ordenar botones
        JPanel panelOrden = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonOrdenarPrioridad = new JButton("Ordenar por prioridad");
        botonOrdenarEstado = new JButton("Ordenar por estado");
        panelOrden.add(botonOrdenarPrioridad);
        panelOrden.add(botonOrdenarEstado);

        // Agregar a la ventana principal
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(scroll, BorderLayout.CENTER);
        panelCentro.add(panelOrden, BorderLayout.SOUTH);

        add(panelEntrada, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        // Acción botón agregar
        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes darle un nombre a la tarea.");
                    return;
                }
                int prioridad;
                try {
                    prioridad = Integer.parseInt(campoPrioridad.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prioridad debe ser un número entero.");
                    return;
                }

                String estado = (String) comboEstado.getSelectedItem();

                Tarea nueva = new Tarea(nombre, prioridad);
                nueva.setEstado(estado);
                nueva.agregarObservador(observador);
                gestor.agregarTarea(nueva);
                mostrarTareas();

                campoNombre.setText("");
                campoPrioridad.setText("");
                comboEstado.setSelectedIndex(0);
            }
        });

        botonOrdenarPrioridad.addActionListener(e -> {
            gestor.setEstrategia(new OrdenarPorPrioridad());
            gestor.ordenarTareas();
            mostrarTareas();
        });

        botonOrdenarEstado.addActionListener(e -> {
            gestor.setEstrategia(new OrdenarPorEstado());
            gestor.ordenarTareas();
            mostrarTareas();
        });

        setVisible(true);
    }

    private void mostrarTareas() {
        areaTareas.setText("");
        for (Tarea t : gestor.getTareas()) {
            areaTareas.append(t.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new VentanaTareas();
    }
}
