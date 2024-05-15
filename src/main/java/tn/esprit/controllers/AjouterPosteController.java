package tn.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Poste;
import tn.esprit.services.ServicePoste;
import tn.esprit.utils.MailController;

import javax.mail.MessagingException;

public class AjouterPosteController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private final ServicePoste sp = new ServicePoste();

    @FXML
    private TextField acteurTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField morceauTF;

    @FXML
    private ChoiceBox<String> genreTF;

    private String[] genre = {"Tech House" , "Funky Techno" , "Minimal" ,"Acid Techno" };

    @FXML
    private TextField imageTF;

    @FXML
    private TextField titreTF;

    @FXML
    private Button upload;
    private final ServicePoste ps = new ServicePoste();


    public MailController mail = new MailController();

    /*void afficherPersonne(ActionEvent event) {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
        try {
            Parent root=loader.load();
            AfficherPersonneController dc= loader.getController();
            dc.setLbname(nomtf.getText());
            ageTF.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/

    @FXML
    void initialize() {
        assert titreTF != null : "fx:id=\"titreTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
        assert acteurTF != null : "fx:id=\"acteurTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
        assert genreTF != null : "fx:id=\"genreTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
        assert imageTF != null : "fx:id=\"imageTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
        assert morceauTF != null : "fx:id=\"morceauTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
        assert descriptionTF != null : "fx:id=\"descriptionTF\" was not injected: check your FXML file 'AjouterPoste.fxml'.";
    }

    public void getGenre (ActionEvent event){
        String Genre = genreTF.getValue();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreTF.getItems().addAll(genre);
        genreTF.setOnAction(this::getGenre);
    }
    public void ajouter(ActionEvent actionEvent) throws MessagingException, IOException {
        if (titreTF.getText().isEmpty() || acteurTF.getText().isEmpty() || genreTF.getValue().isEmpty() || imageTF.getText().isEmpty() || descriptionTF.getText().isEmpty()) {
            showAlert("Erreur", "Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        // Convertir la durée en un entier

        if (descriptionTF.getText().length() < 10) {
            showAlert("Erreur", "Description trop courte", "La description doit comporter au moins 10 caractères.");
            return;
        }

        // Add the Poste
        ps.add(new Poste (titreTF.getText(), acteurTF.getText(), genreTF.getValue(), imageTF.getText(), morceauTF.getText(), descriptionTF.getText()));

        // Send email
        mail.SendMail("azizsalmi330@gmail.com");

        // Show success alert
        showSuccessAlert("Succès", "Poste ajouté", "Le poste a été ajouté avec succès !");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PosteInterface.fxml"));
            titreTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void showSuccessAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void Afficher(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PosteInterface.fxml"));
            titreTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML

    public void handleUpload (ActionEvent actionEvent) {
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
            imageTF.setText(relativePath);
        }
    }

    public void Retour(ActionEvent actionEvent) throws IOException {
        loadScene("/Menu.fxml",actionEvent);

    }
    public void loadScene(String scenePath,ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(scenePath));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
