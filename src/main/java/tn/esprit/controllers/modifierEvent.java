package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.ServiceEvent;

import java.io.File;
import java.io.IOException;

public class modifierEvent  {

    @FXML
    private TextField adresse;

    @FXML
    private TextField date;

    @FXML
    private TextField desc;

    @FXML
    private TextField nom;
    @FXML
    private TextField image;

    private String cheminImage ;
    private int iD;
    private String NOM;
    private String Date;
    private String ADR;
    private String DESC;
    private String IMAGE;

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
        image.setText(IMAGE);
    }
    public String getNOM() {
        return NOM;
    }

    @FXML
    void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            cheminImage = selectedFile.getAbsolutePath();
        }
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
        nom.setText(NOM);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date=Date;
        date.setText(Date);

    }

    public String getADR() {
        return ADR;
    }

    public void setADR(String ADR) {
        this.ADR = ADR;
        adresse.setText(ADR);
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
        desc.setText(DESC);
    }

    private final ServiceEvent ps = new ServiceEvent();


    // @FXML
    // void modifierEvent(ActionEvent event) {
    // ps.Update(new Event(0,nom.getText(),adresse.getText(),date.getText(),desc.getText()));

    //     Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle("succes");
    //     alert.setHeaderText("succes");
    //     alert.setContentText("Evenement ajouter avec succes");
    //    alert.showAndWait();

    //}




    // Dans votre méthode modifierEvent
    @FXML
    void modifierEvent(ActionEvent event) {

        ps.Update(new Event(getID(), nom.getText() ,adresse.getText(), getDate(), desc.getText(),cheminImage));

        // Affichez un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Succès");
        alert.setContentText("Événement modifié avec succès");
        alert.showAndWait();

        // Redirection vers la page d'affichage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventAdmin.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception si le chargement de la page échoue
        }
    }



    void initialize() {
        nom.setText(NOM);
        date.setText(Date);
        adresse.setText(ADR);
        desc.setText(DESC);
        image.setText(IMAGE);

    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setID(int idEvent) {
        this.iD=idEvent;
    }
    public int  getID(){
        return iD;
    }
    @FXML
    void retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Evenements");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
