/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Main.App;
import Modelo.Actividad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author elMatu
 */
public class EditarOCrearActividadesController implements Initializable {

    ActividadesController controladorActividades; // Para enlazar ventanas
    Actividad actividadSeleccionada; // Para enlazar la actividad que se seleccione
    @FXML
    private TextField txtActividad;
    @FXML
    private DatePicker dtFecha;
    @FXML
    private TextField txtPlOcup;
    @FXML
    private TextField txtPlTot;
    @FXML
    private TextField txtCategoria;

    public void setControladorActividades(ActividadesController controladorActividades) {
        this.controladorActividades = controladorActividades;

    }

    public void setActividadSeleccionada(Actividad actividadSeleccionada) {
        this.actividadSeleccionada = actividadSeleccionada;

        txtActividad.setText(actividadSeleccionada.getNombre());
        txtCategoria.setText(actividadSeleccionada.getCategoria());
        dtFecha.setValue(actividadSeleccionada.getFecha());
        txtPlOcup.setText(String.valueOf(actividadSeleccionada.getPlazasOcupadas()));
        txtPlTot.setText(String.valueOf(actividadSeleccionada.getPlazasTotales()));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private boolean comprobarFormulario() {

        // Comprobamos que todos los campos estén completos
        if (txtActividad.getText().trim().isEmpty()
                || txtCategoria.getText().trim().isEmpty()
                || dtFecha.getValue() == null
                || txtPlOcup.getText().trim().isEmpty()
                || txtPlTot.getText().trim().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Formulario incompleto");
            alerta.setContentText("Debes completar todos los campos.");
            alerta.showAndWait();

            return false;
        }

        // Comprobamos que las plazas sean números enteros
        int plazasOcupadas;
        int plazasTotales;

        try {
            plazasOcupadas = Integer.parseInt(txtPlOcup.getText().trim());
            plazasTotales = Integer.parseInt(txtPlTot.getText().trim());
        } catch (NumberFormatException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Plazas incorrectas");
            alerta.setContentText("Las plazas deben ser números enteros válidos.");
            alerta.showAndWait();

            return false;
        }

        // Comprobamos que las plazas no sean negativas
        if (plazasOcupadas < 0 || plazasTotales <= 0) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Plazas incorrectas");
            alerta.setContentText("Las plazas ocupadas no pueden ser negativas y las totales deben ser mayores que cero.");
            alerta.showAndWait();

            return false;
        }

        // Comprobamos que las plazas ocupadas no superen las totales
        if (plazasOcupadas > plazasTotales) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Número de plazas incorrecto");
            alerta.setContentText("Las plazas ocupadas no pueden superar las plazas totales.");
            alerta.showAndWait();

            return false;
        }

        // Si todo está correcto
        return true;
    }

    @FXML
    private void onGuardar(ActionEvent event) {

        // Comprobamos el formulario antes de guardar
        if (!comprobarFormulario()) {
            return;
        }

        if (actividadSeleccionada != null) {
            actividadSeleccionada.setNombre(txtActividad.getText());
            actividadSeleccionada.setCategoria(txtCategoria.getText());
            actividadSeleccionada.setFecha(dtFecha.getValue());
            actividadSeleccionada.setPlazasOcupadas(Integer.parseInt(txtPlOcup.getText()));
            actividadSeleccionada.setPlazasTotales(Integer.parseInt(txtPlTot.getText()));

            // Actualizamos el estado dependiendo de las plazas
            if (actividadSeleccionada.getPlazasOcupadas() >= actividadSeleccionada.getPlazasTotales()) {
                actividadSeleccionada.setEstado("Completa");
            } else {
                actividadSeleccionada.setEstado("Abierta");
            }

        } else {
            int nuevoId = App.getDatosAplicacion().getActividades().stream() // ID Auto incremental
                    .mapToInt(Actividad::getId)
                    .max()
                    .orElse(0) + 1;

            int plazasOcupadas = Integer.parseInt(txtPlOcup.getText());
            int plazasTotales = Integer.parseInt(txtPlTot.getText());

            // Comprobamos que las plazas sean correctas antes de crear la actividad
            if (plazasOcupadas > plazasTotales) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Número de plazas incorrecto");
                alerta.setContentText("Las plazas ocupadas no pueden superar las plazas totales.");
                alerta.showAndWait();
                return;
            }

            // Comprobamos el estado inicial
            String estado;

            if (plazasOcupadas >= plazasTotales) {
                estado = "Completa";
            } else {
                estado = "Abierta";
            }

            // Creamos la actividad
            Actividad nueva = new Actividad(nuevoId, txtActividad.getText(), txtCategoria.getText(), dtFecha.getValue(), plazasTotales, plazasOcupadas, estado);
            App.getDatosAplicacion().getActividades().add(nueva);
        }

        controladorActividades.refrescar();

        Stage stage = (Stage) txtActividad.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        Stage stage = (Stage) txtActividad.getScene().getWindow();

        stage.close();
    }

}
