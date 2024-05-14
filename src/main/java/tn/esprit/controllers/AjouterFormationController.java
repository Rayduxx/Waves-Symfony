package tn.esprit.controllers;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.Formation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import tn.esprit.services.FormationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterFormationController {

    private final FormationService fs=new FormationService();

    @FXML
    private TextField afficheTf;

    @FXML
    private TextField descriptionTf;

    @FXML
    private TextField titreTf;

    @FXML
    private TextField videoTf;
    @FXML
    private Button upload;

    @FXML
    void add(ActionEvent event) throws SQLException {
        String title = titreTf.getText();
        String description = descriptionTf.getText();
        String affiche = afficheTf.getText();
        String video = videoTf.getText();
        // Input validation
        if (title == null || title.isEmpty()) {
            // Notify the user that the title cannot be empty
            // You can display an alert, show a message, etc.
            System.out.println("Title cannot be empty.");
            return; // Exit the method if title is empty
        }

        if (description.length() < 10) {
            // Notify the user that the description should be at least 10 characters long
            // You can display an alert, show a message, etc.
            System.out.println("Description should be at least 10 characters long.");
            return; // Exit the method if description is too short
        }

        // If both title and description pass validation, proceed to add the Formation
        fs.add(new Formation(title, description, afficheTf.getText(), videoTf.getText()));
    }
    @FXML
    void handleUpload(ActionEvent event) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(upload.getScene().getWindow());

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Construire le chemin relatif à partir du répertoire htdocs
            String relativePath = "http://127.0.0.1/img/" + selectedFile.getName();
            // Mettre à jour le champ imageTf avec le chemin relatif de l'image sélectionnée
            afficheTf.setText(relativePath);
        }
    }

    @FXML
    void Afficher(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherSport.fxml"));
                titreTf.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }



}
