package tn.esprit.controllers;

import tn.esprit.models.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.FormationService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierFormation implements Initializable {
    @FXML
    private TextField artisteE;

    @FXML
    private TextField descriptionE;

    @FXML
    private Button editE;

    @FXML
    private TextField imageE;

    @FXML
    private Button picE;

    @FXML
    private TextField titreE;


    public static Formation formation;

    private final FormationService service  =new FormationService();


    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Pré-remplissage des champs avec les informations de l'événement à modifier
        titreE.setText(formation.getTitre());
        artisteE.setText(formation.getVideo());
        imageE.setText(formation.getAffiche());
        descriptionE.setText(formation.getDescription());

    }







    @FXML
    public void modifier(ActionEvent event)  {
        String titre = titreE.getText();
        String artiste = artisteE.getText();
        String image = imageE.getText();
        String description = descriptionE.getText();

        // Input validation
        if (titre.isEmpty() || artiste.isEmpty() ||  image.isEmpty() ||  description.isEmpty()) {
            showAlert("Erreur", "Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        // Convertir la durée en un entier


        if (description.length() < 10) {
            showAlert("Erreur", "Description trop courte", "La description doit comporter au moins 10 caractères.");
            return;
        }

        // Si toutes les validations passent, mettez à jour le poste
        try {
            formation.setTitre(titre);
            formation.setVideo(artiste);
            formation.setAffiche(image);
            formation.setDescription(description);

            service.update(formation);

            showAlert("Succès", "Poste modifié", "Le poste a été modifié avec succès.");

            // Vider les champs
            titreE.clear();
            artisteE.clear();
            imageE.clear();
            descriptionE.clear();

            // Rediriger vers la vue des détails
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherPersonne.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception ex) {
            showAlert("Erreur", "Échec de la modification", "Une erreur s'est produite lors de la modification du poste : " + ex.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML

    public void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(picE.getScene().getWindow());

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Construire le chemin relatif à partir du répertoire htdocs
            String relativePath = "http://127.0.0.1/img/" + selectedFile.getName();
            // Mettre à jour le champ imageTf avec le chemin relatif de l'image sélectionnée
            imageE.setText(relativePath);
        }
    }


    public void retour(ActionEvent event) throws IOException {
        ModifierFormation.formation=formation;
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherSport.fxml"));
        Scene tableScene =new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}