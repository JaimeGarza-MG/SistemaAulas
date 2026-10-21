package edu.ufg.plantillaaulas.controladores;

import edu.ufg.plantillaaulas.dao.UsuarioDAO;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    private boolean comprobando;

    @FXML
    private void ingresar() {
        if (comprobando) {
            return;
        }

        String correo = txtCorreo.getText().trim();
        String clave = txtClave.getText();

        if (correo.isEmpty() || clave.isEmpty()) {
            mostrarMensaje("Ingresa tu correo y tu clave.");
            return;
        }

        comprobando = true;
        txtCorreo.setDisable(true);
        txtClave.setDisable(true);
        mostrarMensaje("Comprobando tus datos...");

        Task<Boolean> tarea = new Task<>() {
            @Override
            protected Boolean call() {
                UsuarioDAO usuarios = new UsuarioDAO();
                usuarios.prepararUsuarioDemo();

                return usuarios.autenticar(correo, clave);
            }
        };

        tarea.setOnSucceeded(evento -> {
            habilitarCampos();
            txtClave.clear();

            if (tarea.getValue()) {
                abrirHome();
            } else {
                mostrarMensaje("Correo o contraseña incorrectos.");
            }
        });

        tarea.setOnFailed(evento -> {
            habilitarCampos();
            txtClave.clear();

            mostrarMensaje(
                    "No se pudo consultar la base de datos. Revisa Output."
            );

            tarea.getException().printStackTrace();
        });

        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
    }

    private void abrirHome() {
        try {
            Parent home = FXMLLoader.load(
                    LoginController.class.getResource(
                            "/edu/ufg/plantillaaulas/Home.fxml"
                    )
            );

            Stage ventana = (Stage) lblMensaje.getScene().getWindow();
            ventana.getScene().setRoot(home);
            ventana.setTitle("Sistema de aulas - Registro");
            ventana.sizeToScene();
            ventana.centerOnScreen();

        } catch (IOException | RuntimeException e) {
            mostrarMensaje(
                    "No se pudo abrir el formulario. Revisa Output."
            );
            e.printStackTrace();
        }
    }

    private void habilitarCampos() {
        comprobando = false;
        txtCorreo.setDisable(false);
        txtClave.setDisable(false);
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #b3261e;");
    }
}