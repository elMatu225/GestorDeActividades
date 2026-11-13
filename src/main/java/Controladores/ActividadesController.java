/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Main.App;
import Modelo.Actividad;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAM2
 */
public class ActividadesController extends BaseController implements Initializable {

    @FXML
    private MenuItem onSalir;
    @FXML
    private MenuItem onAcercade;
    @FXML
    private Button btnInicio;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnNueva;
    @FXML
    private TableView<Actividad> listaActividades;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Label lblResumen;
    @FXML
    private Label lblVisibles;

    ObservableList<Actividad> actividades;
    FilteredList<Actividad> actividadesFiltradas;

    @FXML
    private TableColumn<Actividad, String> colActividad;
    @FXML
    private TableColumn<Actividad, String> colCategoria;
    @FXML
    private TableColumn<Actividad, LocalDate> colFecha;
    @FXML
    private TableColumn<Actividad, String> colPlazas;
    @FXML
    private TableColumn<Actividad, String> colEstado;
    @FXML
    private Button btnRefresh;
    @FXML
    private Tab tabTodas;
    @FXML
    private Tab tabCulturales;
    @FXML
    private Tab tabDeportivas;
    @FXML
    private Tab tabTecnologicas;
    @FXML
    private TabPane tabFiltrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colActividad.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        colCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria")
        );
        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );
        colPlazas.setCellValueFactory(celda -> {
            Actividad actividad = celda.getValue();

            String plazas = actividad.getPlazasOcupadas() + "/" + actividad.getPlazasTotales();

            return new SimpleStringProperty(plazas);
        });
        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );

        actividades = App.getDatosAplicacion().getActividades();

        actividadesFiltradas
                = new FilteredList<>(actividades);
        listaActividades.setItems(actividadesFiltradas); // Lista filtrada de las actividades

        cmbCategoria.getItems().add("Todas");
        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            String categoria = actividad.getCategoria();
            if (!cmbCategoria.getItems().contains(categoria)) {
                cmbCategoria.getItems().add(categoria);
            }
        }
        cmbCategoria.setValue("Todas"); // Filtrado por categoria

        cmbEstado.getItems().add("Todas");
        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            String estado = actividad.getEstado();
            if (!cmbEstado.getItems().contains(estado)) {
                cmbEstado.getItems().add(estado);
            }
        }
        cmbEstado.setValue("Todas"); // Filtrado por estado

        listaActividades.getSelectionModel() // Activar el boton de eliminar y de editar cuando se seleccione una opcion.
                .selectedItemProperty()
                .addListener((observable, anterior, actual) -> {
                    if (actual != null) {
                        btnEditar.setDisable(false);
                        btnBorrar.setDisable(false);
                    }
                });

        // Listener del filtered list del cmbCategoria
        cmbCategoria.valueProperty().addListener(
                (obs, anterior, actual) -> {
                    aplicarFiltros();
                }
        );

        // Listener del filtered list del cmbEstado
        cmbEstado.valueProperty().addListener(
                (obs, anterior, actual) -> {
                    aplicarFiltros();
                }
        );

        // Listener para filtrar por categoría al cambiar de pestaña
        tabFiltrar.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> {

                    if (actual == tabTodas) {
                        cmbCategoria.setValue("Todas");

                    } else if (actual == tabCulturales) {
                        cmbCategoria.setValue("Cultural");

                    } else if (actual == tabDeportivas) {
                        cmbCategoria.setValue("Deportiva");

                    } else if (actual == tabTecnologicas) {
                        cmbCategoria.setValue("Tecnología");
                    }
                }
        );

    }

    private void actualizarResumen() { // Actualiza lblResumen y lblVisibles

        int total = actividades.size();
        int abiertas = 0;
        int completas = 0;

        // Recorremos todas las actividades
        for (Actividad actividad : actividades) {

            if (actividad.getEstado().equals("Abierta")) {
                abiertas++;
            } else if (actividad.getEstado().equals("Completa")) {
                completas++;
            }
        }

        // Actualizamos lblResumen
        lblResumen.setText(total + " en total - "
                + abiertas + " abiertas - "
                + completas + " completadas");

        // Actualizamos lblVisibles
        lblVisibles.setText(actividadesFiltradas.size() + " actividades visibles");
    }

    private void aplicarFiltros() {

        String busqueda = txtBuscar.getText().trim().toLowerCase();
        String categoria = cmbCategoria.getValue();
        String estado = cmbEstado.getValue();

        actividadesFiltradas.setPredicate(actividad -> {

            // Filtramos por nombre
            boolean coincideNombre = actividad.getNombre().toLowerCase().contains(busqueda);

            // Filtramos por categoría
            boolean coincideCategoria = categoria == null
                    || categoria.equals("Todas")
                    || actividad.getCategoria().equals(categoria);

            // Filtramos por estado
            boolean coincideEstado = estado == null
                    || estado.equals("Todas")
                    || actividad.getEstado().equals(estado);

            // Mostramos las actividades que cumplen los tres filtros
            return coincideNombre && coincideCategoria && coincideEstado;
        });
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        listaActividades.refresh();
    }

    public void refrescar() {
        listaActividades.refresh();
        cmbCategoria.getItems().clear();

        cmbCategoria.getItems().add("Todas");
        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            String categoria = actividad.getCategoria();
            if (!cmbCategoria.getItems().contains(categoria)) {
                cmbCategoria.getItems().add(categoria);
            }
        }
        cmbCategoria.setValue("Todas");
        actualizarResumen();
    }

    private void abrirDialogo(Actividad actividadSeleccionada) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Vistas/EditarOCrearActividades.fxml"));
        Parent root = fxmlLoader.load();

        // Comunicar Actividades con Editar
        EditarOCrearActividadesController VentanaEditar = fxmlLoader.getController(); // Seleccionamos la ventana del dialogo
        VentanaEditar.setControladorActividades(this); // Hacemos que se conecte

        if (actividadSeleccionada != null) {
            VentanaEditar.setActividadSeleccionada(actividadSeleccionada); // Pasa como parámetro la actividad seleccionada
        }

        Scene escena = new Scene(root, 600, 400);
        Stage stage = new Stage();

        // Le decimos al nuevo escenario quien es su padre
        stage.initOwner(btnBorrar.getScene().getWindow());
        stage.initModality(Modality.NONE); // Especificarle modalidad

        stage.setScene(escena);
        stage.show();
        actualizarResumen();
    }

    @FXML
    private void onBuscar(ActionEvent event) {
        aplicarFiltros();
    }

    @FXML
    private void onNuevaActividad(ActionEvent event) throws IOException {
        abrirDialogo(null);
    }

    @FXML
    private void onEditar(ActionEvent event) throws IOException {
        Actividad actividad = listaActividades.getSelectionModel().getSelectedItem(); // Selecciona la actividad seleccionada y la almacena en actividad
        abrirDialogo(actividad);
    }

    @FXML
    private void onBorrar(ActionEvent event) {
        Actividad seleccionada = listaActividades.getSelectionModel().getSelectedItem();
        App.getDatosAplicacion().getActividades().remove(seleccionada);
        actualizarResumen();
    }

}
