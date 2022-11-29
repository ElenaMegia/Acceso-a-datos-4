package Ventana;


import base.Pelicula;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


import javax.swing.*;
import java.awt.event.*;


public class VentanaController implements ActionListener, MouseListener, KeyListener,
        FocusListener {

    private Ventana view;
    private VentanaModel model;
    private MongoDatabase db;

    private DefaultListModel dlmLibros;

    // Indica si se está creando o modificando la pelicula
    private boolean nuevaPelicula;
    // Siempre mantiene una referencia al libro seleccionado de la lista
    private Pelicula peliculaSeleccionada;

    public VentanaController(Ventana view, VentanaModel model) {
        this.view = view;
        this.model = model;

        model.conectar();
        addListeners();
        inicializar();
    }
