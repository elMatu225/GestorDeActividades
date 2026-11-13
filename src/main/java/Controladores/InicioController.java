/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Main.App;
import Modelo.Actividad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author DAM2
 */
public class InicioController extends BaseController implements Initializable {

    @FXML
    private Button btnInicio;
    @FXML
    private Label lblActividades;
    @FXML
    private Label lblInscritos;

    ObservableList<Actividad> actividades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        actividades = App.getDatosAplicacion().getActividades();
        int total = actividades.size();

        int inscritos = 0;

        for (Actividad actividad : actividades) {
            // Sumamos las plazas ocupadas de todas las actividades
            inscritos += actividad.getPlazasOcupadas();

        }

        // Actualizamos los Labels
        lblActividades.setText(String.valueOf(total));
        lblInscritos.setText(String.valueOf(inscritos));
    }

}
