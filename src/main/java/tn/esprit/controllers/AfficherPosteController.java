package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Poste;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServicePoste;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;

public class AfficherPosteController implements Initializable {
    @FXML
    public HBox Boxes;
    @FXML
    private TextField recherche;
    @FXML
    private GridPane posteContainer;

   private final ServicePoste sp = new ServicePoste();


    public void initialize(URL location, ResourceBundle resources) {
        setGridPostes();

        List<Poste> postes = sp.getAll();
        setGrid(postes);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                posteContainer.getChildren().clear();
                List<Poste> filteredPostes = sp.recherchePoste(recherche.getText());
                setGrid(filteredPostes);
            }
            else { // Si le champ de recherche est vide
                posteContainer.getChildren().clear(); // Effacez le contenu actuel
                List<Poste> postes1 = sp.getAll(); // Récupérez toutes les données initiales
                setGridPostes();
            }
        });



        }

    private void setGridPostes() {
        int c = 0;
        int row = 1;
        try {
            for (Poste poste : sp.getAll()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/test.fxml"));
                Pane posteBox = fxmlLoader.load();
                CardController cardC = fxmlLoader.getController();
                cardC.setData(poste);
                if (c == 4) {
                    c = 0;
                    ++row;
                }
                posteContainer.add(posteBox, c++, row);
                GridPane.setMargin(posteBox, new Insets(15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setGrid(List<Poste> postes) {
        int column = 0;
        int row = 1;
        {
            try {
                for (Poste poste : postes) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
                    AnchorPane posteBox = loader.load();
                    CardController cardController = loader.getController();
                    cardController.setData(poste);
                    if (column == 4) {
                        column = 0;
                        ++row;
                    }
                    posteContainer.add(posteBox, column++, row);
                    GridPane.setMargin(posteBox, new Insets(15));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}





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

    public void ajouterposte(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPoste.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Waves - Ajouter poste");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }







