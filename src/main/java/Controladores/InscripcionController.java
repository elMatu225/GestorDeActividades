/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author DAM2
 */
public class InscripcionController extends BaseController implements Initializable {

    @FXML
    private Button btnInicio;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ComboBox<String> cmbActividad;
    @FXML
    private DatePicker dtFecha;
    @FXML
    private ToggleGroup turno;
    @FXML
    private CheckBox chkNormas;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblApellidos;
    @FXML
    private Label lblCorreo;
    @FXML
    private Label lblActividad;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblTurno;
    @FXML
    private Label lblConfirmacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Como poner archivos en el combobox
        cmbActividad.getItems().addAll(
                "",
                "Curso de Swing",
                "Curso de JAVAFX",
                "Curso de CSS",
                "Curso de HTML"
        );
        
        // Listener que actualiza los datos conforme se escriben o seleccionan los datos.
        txtNombre.textProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null || nuevo.trim().isEmpty()) {
                lblNombre.setText("Nombre: Sin especificar");
            } else {
                lblNombre.setText("Nombre: " + nuevo);
            }
            
        });

        txtApellidos.textProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null || nuevo.trim().isEmpty()) {
                lblApellidos.setText("Apellidos: Sin especificar");
            } else {
                lblApellidos.setText("Apellidos: " + nuevo);
            }
            
        });

        txtCorreo.textProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null || nuevo.trim().isEmpty()) {
                lblApellidos.setText("Correo: Sin especificar");
            } else {
                lblApellidos.setText("Correo: " + nuevo);
            }
            
        });

        cmbActividad.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null || nuevo.isEmpty()) {
                lblActividad.setText("Actividad: Sin especificar");
            } else {
                lblActividad.setText("Actividad: " + nuevo);
            }
            
        });

        dtFecha.valueProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null) {
                lblFecha.setText("Fecha: Sin especificar");
            } else {
                lblFecha.setText("Fecha: " + nuevo);
            }
            
        });

        turno.selectedToggleProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo != null) {
                RadioButton seleccion = (RadioButton) nuevo;
                lblTurno.setText("Actividad: " + seleccion.getText());
            } else {
                lblTurno.setText("Actividad: Sin seleccionar");
            }
            
        });
    }

    private boolean comprobarFormulario() { // Método que compruba los datos.
        boolean nombreCorrecto = !txtNombre.getText().trim().isEmpty();
        boolean apellidosCorrecto = !txtApellidos.getText().trim().isEmpty();
        boolean correoCorrecto = txtCorreo.getText().trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

        boolean actividadCorrecta = cmbActividad.getValue() != null && cmbActividad.getValue() != "";

        boolean fechaCorrecta = dtFecha.getValue() != null;

        boolean turnoCorrecto = turno.getSelectedToggle() != null;

        boolean politicaAceptada = chkNormas.isSelected();

        if (nombreCorrecto && apellidosCorrecto && correoCorrecto && 
                actividadCorrecta && fechaCorrecta && 
                turnoCorrecto && politicaAceptada) {
            return true;
        } else {
            return false;
        }

    }

    @FXML
    private void onLimpiar(ActionEvent event) {  // Boton de limpiar formulario.
        if (comprobarFormulario()) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Limpiar...");
            alerta.setHeaderText("¿Seguro que quieres limpiar el formulario?");
            alerta.setContentText("Hay contenido sin introducido.");
            alerta.getButtonTypes().setAll(
                    ButtonType.YES, ButtonType.NO
            );
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
                txtNombre.setText("");
                txtApellidos.setText("");
                txtCorreo.setText("");
                cmbActividad.setValue("");
                dtFecha.setValue(null);
                turno.selectToggle(null);
                chkNormas.setSelected(false);
            }
        }

    }

    @FXML
    private void onEnviar(ActionEvent event) { // Boton para enviar formulario.
        if (comprobarFormulario()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Enviar...");
            alerta.setHeaderText("Inscripción registrada");
            String turnoSeleccionado = ((RadioButton) turno.getSelectedToggle()).getText();
            alerta.setContentText(
                    "Nombre: " + txtNombre.getText().trim()
                    + "\nApellidos: " + txtApellidos.getText().trim()
                    + "\nCorreo: " + txtCorreo.getText().trim()
                    + "\nActividad: " + cmbActividad.getValue()
                    + "\nFecha: " + dtFecha.getValue()
                    + "\nTurno: " + turnoSeleccionado
                    + "\nNormas y política de privacidad: Aceptada"
            );
            lblConfirmacion.setText("");

            alerta.show();
        } else {
            lblConfirmacion.setText("Formulario Incompleto o con errores");
            lblConfirmacion.setStyle("-fx-text-fill: red");
        }
    }

}
