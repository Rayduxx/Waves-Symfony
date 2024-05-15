package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Event;
import tn.esprit.services.ServiceEvent;

import java.io.IOException;
import java.util.List;

public class afficherEvent {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> nomColumn;

    @FXML
    private TableColumn<Event, String> adresseColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, String> descColumn;

    @FXML
    private TableColumn<Event, String> EditColumn;

    private final ServiceEvent serviceEvent = new ServiceEvent();
    private ComboBox Actionscol;

    @FXML
    void initialize() {
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomE()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdrE()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        descColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));
        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFoctory = (TableColumn<Event, String> param) ->
        {
            final TableCell<Event, String> cell = new TableCell<Event, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);//tableau feregh
                        setText(null);
                    } else {
                        // tableau m3ebii
                        Button editbutton = new Button("modifier");
                        //chneya ta3ml l bouton modifier kifeh t3adi l'id lpage lokhra

                        editbutton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                Event evente = getTableRow().getItem();
                                // Charger la vue AjouterEquipe.fxml page jdida
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                                Parent root = loader.load();
                                modifierEvent mE = loader.getController();
                                // Passer l'ID de la partie au contrôleur de la vue chargée
                                mE.setNOM(evente.getNomE());
                                mE.setADR(evente.getAdrE());
                                mE.setDate(evente.getDate());
                                mE.setDESC(evente.getDesc());


                                // Créer une nouvelle scène avec la vue chargée
                                Scene scene = new Scene(root);
                                // Obtenir la fenêtre principale depuis l'événement
                                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                // Changer la scène de la fenêtre principale
                                primaryStage.setScene(scene);
                                primaryStage.show();
                            } catch (IOException e) {
                                e.printStackTrace(); // Gérer les exceptions liées au chargement de la vue
                            }
                        });

                        HBox managebtn = new HBox( editbutton);//box horizontale fih deux boutons
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(editbutton, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        EditColumn.setCellFactory(cellFoctory);

        // Charge les données des événements dans la table
        afficherEvent();
    }
    public void initData(String nom) {
        // Utilisez le nom pour configurer la vue, par exemple :
        // labelNom.setText(nom);
    }

    @FXML
    void ajouterEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();
            eventTable.getScene().setRoot(root); // Utilisez n'importe quel nœud de votre interface actuelle
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ajouterReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();
            eventTable.getScene().setRoot(root); // Utilisez n'importe quel nœud de votre interface actuelle
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Callback<TableColumn<Event, Void>, TableCell<Event, Void>> createButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");


                    // Ajoutez un gestionnaire d'événements pour le bouton "Edit"
                    //  editButton.setOnAction(event -> {
                    //   Event event = getTableView().getItems().get(getIndex());
                    // Implémentez ici votre logique pour ouvrir la vue de modification pour cet événement
                    // Vous pouvez utiliser event pour accéder aux données de l'événement sélectionné


                };
            }
        };
    }
    private void afficherEvent() {
        List<Event> events = serviceEvent.getAll();
        eventTable.getItems().addAll(events);
    }
}
