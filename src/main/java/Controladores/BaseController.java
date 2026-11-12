/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Main.App;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author elMatu
 */
public abstract class BaseController {
    @FXML
    private void onIrInicio(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }

    @FXML
    private void onIrInscripcion(ActionEvent event) throws IOException {
        App.setRoot("inscripcion");
    }

    @FXML
    private void onIrActividades(ActionEvent event) throws IOException {
        App.setRoot("actividades");
    }

    @FXML
    private void onIrConfiguracion(ActionEvent event) throws IOException {
        App.setRoot("configuracion");
    }
    
    @FXML
    private void onSalir(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Salir...");
        alerta.setHeaderText("¿Salir de la aplicación?");
        alerta.getButtonTypes().setAll(
                ButtonType.YES, ButtonType.NO
        );
        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            Platform.exit();
        }
    }

    @FXML
    private void onAcercade(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de...");
        alerta.setHeaderText("Gestor de actividades - v1.0 | IES Jose Luis Martínez Palomo");
        alerta.setContentText("Desarrollado por: Matías Gabriel Bottino Cuña."
        + "\n \nTodos los derechos reservados."
        );
        alerta.show();
    }  
}
