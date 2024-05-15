package tn.esprit.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.Cours;
import tn.esprit.models.Formation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.services.CoursService;
import tn.esprit.services.FormationService;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AfficherCours1Controller {
    private final CoursService cs=new CoursService();


    @FXML
    private TableColumn<Cours, String> dureeC;

    @FXML
    private TableColumn<Cours, Integer> idC;

    @FXML
    private TableView<Cours> tableC;

    @FXML
    private TableColumn<Cours, String> titreC;
    @FXML
    private TextField Tduree_cours;

    @FXML
    private TextField Ttitre_cours;
    @FXML
    private TableColumn<Cours, Formation> formationC;
    @FXML
    private ChoiceBox<String> Tformation;

    @FXML
    void Modifier(ActionEvent event) {
// Vérifier si un élément est sélectionné dans la TableView
        Cours coursSelectionne = tableC.getSelectionModel().getSelectedItem();
        if (coursSelectionne != null) {
            // Récupérer les valeurs des champs de texte
            String titre_cours = Ttitre_cours.getText();
            String duree_cours= Tduree_cours.getText();
           String formation= Tformation.getValue();





            // Mettre à jour les informations du tournoi sélectionné

            coursSelectionne.setTitre_cours(titre_cours);
            coursSelectionne.setDuree_cours(duree_cours);
            coursSelectionne.getFormation().setTitre(String.valueOf(formation));



            // Mettre à jour la TableView avec les modifications
            tableC.refresh();

            // Vous pouvez également appeler votre méthode de mise à jour dans le service ici
            CoursService ss = new CoursService();
            ss.update(coursSelectionne);

            // Réinitialiser les champs de texte après la modification

            Ttitre_cours.clear();
            Tduree_cours.clear();
            Tformation.getItems().clear();



        } else {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }


    public void selection(MouseEvent mouseEvent) {
        Cours selectedCours;
        selectedCours = tableC.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {

            Ttitre_cours.setText(selectedCours.getTitre_cours());
            Tduree_cours.setText(selectedCours.getDuree_cours());
           //Tformation.setItems(String.valueOf(selectedCours.toString()));

        }
    }


    @FXML
    void Supprimer(ActionEvent event) {
        Cours cours = tableC.getSelectionModel().getSelectedItem();

        if (cours != null) {
            supprimerLigne(cours);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }

    }
    @FXML
    void initialize() {
        List<Cours> cours = cs.getAll();

        ObservableList<Cours> observableList = FXCollections.observableList(cours);
        tableC.setItems(observableList);
        System.out.println(observableList);
        idC.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        titreC.setCellValueFactory(new PropertyValueFactory<>("titre_cours"));
        dureeC.setCellValueFactory(new PropertyValueFactory<>("duree_cours"));
        formationC.setCellValueFactory(new PropertyValueFactory<>("formation"));

    }


    @FXML
    void supprimerLigne(Cours cours) {
        tableC.getItems().remove(cours);
        cs.delete(cours);


    }
    public void trier(ActionEvent actionEvent) {
        List<Cours> cours = cs.triFormationtBytitre_cours();
        ObservableList<Cours> observableList = FXCollections.observableList(cours);
        tableC.setItems(observableList);
        idC.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        titreC.setCellValueFactory(new PropertyValueFactory<>("titre_cours"));
        dureeC.setCellValueFactory(new PropertyValueFactory<>("duree_cours"));
    }
    @FXML
    public void retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAdmin.fxml"));
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


