/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author DAM2
 */
public class ConfiguracionController extends BaseController implements Initializable {

    @FXML
    private Button btnInicio;
    @FXML
    private ComboBox<String> cmbTema;
    @FXML
    private Slider sldTamaño;
    @FXML
    private ColorPicker clpkColor;
    @FXML
    private Slider sldZoom;
    @FXML
    private Label lblTamanio;
    @FXML
    private Label lblZoom;
    @FXML
    private CheckBox chkImagenes;
    @FXML
    private CheckBox chkDescripciones;
    @FXML
    private CheckBox chkDiseño;
    @FXML
    private CheckBox chkAnimaciones;
    @FXML
    private CheckBox chkConsejos;
    @FXML
    private ComboBox<String> cmbIdioma;
    
        // Datos para guardar los datos predefinidos en variables
    int tema = 0; // Posicion por defecto en la lista
    int idioma = 0; // Posicion por defecto en la lista
    Color color;
    double tamanio;
    double zoom;
    boolean imagenes;
    boolean descripciones;
    boolean diseño;
    boolean animaciones;
    boolean consejos;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Metemos 2 opciones en el comboBox de tema y de idiomas
        cmbTema.getItems().addAll("Claro (por defecto)", "Oscuro");
        cmbIdioma.getItems().addAll("Español (España)", "Inglés (US)");

        sldTamaño.valueProperty().addListener((obs, anterior, nuevo) -> {
            lblTamanio.setText(nuevo.intValue() + " px");
        });
        
        sldZoom.valueProperty().addListener((obs, anterior, nuevo) -> {
            lblZoom.setText(nuevo.intValue() + "%");
        });
        
        // Guardamos los valores por defecto
        color = clpkColor.getValue();
        tamanio = sldTamaño.getValue();
        zoom = sldZoom.getValue();
        imagenes = chkImagenes.isSelected();
        descripciones = chkDescripciones.isSelected();
        diseño = chkDiseño.isSelected();
        animaciones = chkAnimaciones.isSelected();
        consejos = chkConsejos.isSelected();
    }
    
    
    private String translation(boolean condicion){ // Para taducir de True,False a Si,No
        if (condicion) {
            return "Si";
        } else
            return "No";
    }
    
    @FXML
    private void onRestablecer(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Restablecer...");
            alerta.setHeaderText("¿Seguro que quieres restablecer los ajustes?");
            alerta.setContentText("Los ajustes volveran a sus valores por defecto.");
            alerta.getButtonTypes().setAll(
                    ButtonType.YES, ButtonType.NO
            );
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
                cmbTema.getSelectionModel().select(tema);
                cmbIdioma.getSelectionModel().select(idioma);
                clpkColor.setValue(color);
                sldTamaño.setValue(tamanio);
                sldZoom.setValue(zoom);
                chkImagenes.setSelected(imagenes);
                chkDescripciones.setSelected(descripciones);
                chkDiseño.setSelected(diseño);
                chkAnimaciones.setSelected(animaciones);
                chkConsejos.setSelected(consejos);
            }
    }
    
    @FXML
    private void onGuardar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Guardar...");
            alerta.setHeaderText("Datos guardados.");
            alerta.setContentText(
                    "Tema: " + cmbTema.getValue()
                    + "\nColor: " + clpkColor.getValue().toString()
                    + "\nTamaño de la fuente: " + sldTamaño.getValue() + " px" 
                    + "\nZoom: " + sldZoom.getValue() + "%"
                    + "\nImagenes: " + translation(chkImagenes.isSelected())
                    + "\nDescripciones: " + translation(chkDescripciones.isSelected())
                    + "\nDiseño compacto: " + translation(chkDiseño.isSelected())
                    + "\nAnimaciones: " + translation(chkAnimaciones.isSelected())
                    + "\nConsejos: " + translation(chkConsejos.isSelected())
                    + "\nIdioma: " + cmbIdioma.getValue()
            );
            alerta.show();
    }

}
