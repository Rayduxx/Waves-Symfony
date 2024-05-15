package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.ServiceEvent;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class DetailsUser {
    @FXML
    private GridPane eventGrid;   // Utilisez un GridPane pour organiser les cartes

    private final ServiceEvent serviceEvent = new ServiceEvent();

    // Utilisez un GridPane pour organiser les cartes

    @FXML
    void initialize() {
        List<Event> events = serviceEvent.getAll();
        events.sort(Comparator.comparing(Event::getNomE));
        int column = 0;
        int row = 0;
        for (Event event : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardU.fxml"));
                Node cardNode = loader.load();
                CardcontrollerU cardController = loader.getController();
                cardController.initData(event);
                eventGrid.add(cardNode, column, row);
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void initData(String text) {
    }
    @FXML
    public void Menu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
