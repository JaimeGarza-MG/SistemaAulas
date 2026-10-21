package edu.ufg.plantillaaulas.controladores;

import edu.ufg.plantillaaulas.dao.AulaDAO;
import edu.ufg.plantillaaulas.entidades.Aula;
import edu.ufg.plantillaaulas.entidades.EstadoAula;
import edu.ufg.plantillaaulas.entidades.TipoAula;
import java.io.IOException;
import java.util.List;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

public class HomeController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdificio;
    @FXML private TextField txtCapacidad;
    @FXML private TextField txtResponsable;

    @FXML private ComboBox<Integer> cmbPlanta;
    @FXML private ComboBox<TipoAula> cmbTipoAula;
    @FXML private ComboBox<EstadoAula> cmbEstado;

    @FXML private Button btnGuardar;
    @FXML private Label lblMensaje;

    private boolean ocupado;

    private record Catalogos(
            List<Integer> plantas,
            List<TipoAula> tipos,
            List<EstadoAula> estados) {
    }

    @FXML
    private void initialize() {
        cambiarOcupado(true);
        mostrarMensaje("Cargando opciones de la base de datos...", false);

        Task<Catalogos> tarea = new Task<>() {
            @Override
            protected Catalogos call() {
                AulaDAO dao = new AulaDAO();

                return new Catalogos(
                        dao.obtenerPlantas(),
                        dao.obtenerTipos(),
                        dao.obtenerEstados()
                );
            }
        };

        tarea.setOnSucceeded(evento -> {
            Catalogos datos = tarea.getValue();

            cmbPlanta.getItems().setAll(datos.plantas());
            cmbTipoAula.getItems().setAll(datos.tipos());
            cmbEstado.getItems().setAll(datos.estados());

            cambiarOcupado(false);

            if (datos.plantas().isEmpty()
                    || datos.tipos().isEmpty()
                    || datos.estados().isEmpty()) {

                btnGuardar.setDisable(true);
                mostrarMensaje(
                        "Faltan opciones en las tablas de la base de datos.",
                        false
                );
            } else {
                mostrarMensaje("Formulario listo para registrar aulas.", true);
            }
        });

        tarea.setOnFailed(evento -> {
            cambiarOcupado(false);
            btnGuardar.setDisable(true);

            mostrarMensaje(
                    "No se pudieron cargar las opciones. Revisa Output.",
                    false
            );

            tarea.getException().printStackTrace();
        });

        iniciar(tarea);
    }

    @FXML
    private void guardar() {
        if (ocupado) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String edificio = txtEdificio.getText().trim();
        String capacidadTexto = txtCapacidad.getText().trim();
        String responsable = txtResponsable.getText().trim();

        Integer planta = cmbPlanta.getValue();
        TipoAula tipo = cmbTipoAula.getValue();
        EstadoAula estado = cmbEstado.getValue();

        if (nombre.isEmpty() || edificio.isEmpty()
                || capacidadTexto.isEmpty() || responsable.isEmpty()
                || planta == null || tipo == null || estado == null) {

            mostrarMensaje("Completa todos los campos.", false);
            return;
        }

        if (nombre.length() > 80 || edificio.length() > 80
                || responsable.length() > 150) {

            mostrarMensaje(
                    "Máximo: 80 caracteres en nombre y edificio, "
                    + "y 150 en responsable.",
                    false
            );
            return;
        }

        int capacidad;

        try {
            capacidad = Integer.parseInt(capacidadTexto);
        } catch (NumberFormatException e) {
            mostrarMensaje(
                    "La capacidad debe ser un número entero válido.",
                    false
            );
            return;
        }

        if (planta <= 0 || capacidad <= 0) {
            mostrarMensaje(
                    "La planta y la capacidad deben ser mayores que cero.",
                    false
            );
            return;
        }

        Aula aula = new Aula(
                nombre, edificio, planta, capacidad,
                tipo, responsable, estado
        );

        cambiarOcupado(true);
        mostrarMensaje("Guardando aula...", false);

        Task<Void> tarea = new Task<>() {
            @Override
            protected Void call() {
                new AulaDAO().guardar(aula);
                return null;
            }
        };

        tarea.setOnSucceeded(evento -> {
            cambiarOcupado(false);
            limpiar();
            mostrarMensaje("Aula guardada correctamente.", true);
        });

        tarea.setOnFailed(evento -> {
            cambiarOcupado(false);
            Throwable error = tarea.getException();

            if (error instanceof IllegalArgumentException) {
                mostrarMensaje(error.getMessage(), false);
            } else if (esRestriccion(error)) {
                mostrarMensaje(
                        "No se pudo guardar: comprueba que el aula "
                        + "no esté repetida y las opciones sean válidas.",
                        false
                );
            } else {
                mostrarMensaje(
                        "No se pudo guardar el aula. Revisa Output.",
                        false
                );
            }

            error.printStackTrace();
        });

        iniciar(tarea);
    }

    @FXML
    private void limpiar() {
        if (ocupado) {
            return;
        }

        txtNombre.clear();
        txtEdificio.clear();
        txtCapacidad.clear();
        txtResponsable.clear();

        cmbPlanta.getSelectionModel().clearSelection();
        cmbTipoAula.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();

        lblMensaje.setText("");
        txtNombre.requestFocus();
    }

    @FXML
    private void cerrarSesion() {
        if (ocupado) {
            return;
        }

        try {
            Parent login = FXMLLoader.load(
                    HomeController.class.getResource(
                            "/edu/ufg/plantillaaulas/primary.fxml"
                    )
            );

            Stage ventana = (Stage) lblMensaje.getScene().getWindow();
            ventana.getScene().setRoot(login);
            ventana.setTitle("Sistema de aulas");
            ventana.sizeToScene();

        } catch (IOException e) {
            mostrarMensaje("No se pudo abrir el inicio de sesión.", false);
            e.printStackTrace();
        }
    }

    private void cambiarOcupado(boolean valor) {
        ocupado = valor;

        btnGuardar.setDisable(valor);
        txtNombre.setDisable(valor);
        txtEdificio.setDisable(valor);
        txtCapacidad.setDisable(valor);
        txtResponsable.setDisable(valor);
        cmbPlanta.setDisable(valor);
        cmbTipoAula.setDisable(valor);
        cmbEstado.setDisable(valor);
    }

    private void mostrarMensaje(String mensaje, boolean correcto) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(
                correcto
                        ? "-fx-text-fill: #18723b;"
                        : "-fx-text-fill: #b3261e;"
        );
    }

    private boolean esRestriccion(Throwable error) {
        while (error != null) {
            if (error instanceof ConstraintViolationException) {
                return true;
            }
            error = error.getCause();
        }

        return false;
    }

    private void iniciar(Task<?> tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
    }
}