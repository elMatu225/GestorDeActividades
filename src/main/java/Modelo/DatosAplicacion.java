/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author elMatu
 */
public class DatosAplicacion {
    private final ObservableList<Actividad> actividades = FXCollections.observableArrayList();

    public DatosAplicacion(){
        cargarActividadesPorDefecto();
    }
    
    private void cargarActividadesPorDefecto() {
        actividades.addAll(
            new Actividad(1, "Taller de JavaFX", "Tecnología", LocalDate.of(2026, 11, 12), 8, 20, "Abierta"),
            new Actividad(2, "Taller de Teatro", "Cultural", LocalDate.of(2026, 11, 18), 12, 25, "Abierta"),
            new Actividad(3, "Torneo de Fútbol Sala", "Deportiva", LocalDate.of(2026, 11, 21), 16, 16, "Completa"),
            new Actividad(4, "Voluntariado Ambiental", "Solidaria", LocalDate.of(2026, 11, 27), 5, 30, "Abierta"),
            new Actividad(5, "Concurso de Fotografía", "Cultural", LocalDate.of(2026, 12, 02), 9, 20, "Abierta"),
            new Actividad(6, "Robótica educativa", "Tecnología", LocalDate.of(2026, 12, 10), 0, 15, "Abierta")
        );
    }

    public ObservableList<Actividad> getActividades() {
        return actividades;
    }
}
