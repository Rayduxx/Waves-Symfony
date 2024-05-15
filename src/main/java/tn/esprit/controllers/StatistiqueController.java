package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tn.esprit.services.ServiceEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class StatistiqueController {
    @FXML
    private BarChart<?, ?> barchar;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    ServiceEvent se = new ServiceEvent();
    public void initialize() {
        try {
            Map<String, Integer> occurrencesParEvent = se.getOccurrencesParEvent();
            XYChart.Series series = new XYChart.Series<>();
            for (Map.Entry<String, Integer> entry : occurrencesParEvent.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barchar.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void AfficherEventAdmin(ActionEvent event ){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventAdmin.fxml"));
            Parent root = loader.load();
            barchar.getScene().setRoot(root); // Utilisez n'importe quel nœud de votre interface actuelle
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}