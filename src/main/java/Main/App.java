/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Main;

import Modelo.DatosAplicacion;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author DAM2
 */
public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    private static final DatosAplicacion datos = new DatosAplicacion();

    public static DatosAplicacion getDatosAplicacion() {
        return datos;
    }
    
    public static Window getVentanaPrincipal() {
    return scene.getWindow();
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        scene = new Scene(loadFXML("inicio"), 1100, 750);
        stage.setScene(scene);
        stage.setTitle("Gestor de actividades - v1.0");
        stage.show();
        stage.setOnCloseRequest(event -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Salir...");
            alerta.setHeaderText("¿Salir de la aplicación?");
            alerta.getButtonTypes().setAll(
                    ButtonType.YES, ButtonType.NO
            );
            alerta.initOwner(stage);
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.isEmpty() || respuesta.get() != ButtonType.YES) {
                event.consume();
            }
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Vistas/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
