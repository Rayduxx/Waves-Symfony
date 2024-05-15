package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.ServiceEvent;

import java.io.IOException;
import java.nio.file.Paths;

public class CardControllerA {

    @FXML
    private Label adr;

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Button edit;

    @FXML
    private ImageView imageL;

    @FXML
    private Label nom;

    @FXML
    private Button supprimer;
    private int idEvent;

    private final ServiceEvent ps = new ServiceEvent();

    public void initData(Event event) {
        idEvent=event.getEid();
        nom.setText(event.getNomE());
        adr.setText(event.getAdrE());
        date.setText(event.getDate());
        desc.setText(event.getDesc());
        String formattedImagePath = Paths.get(event.getImage()).toUri().toString();
        Image image = new Image(formattedImagePath);
        imageL.setImage(image);
    }

    @FXML
    void modifier(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();
            modifierEvent me=loader.getController();
            me.setID(idEvent);
            me.setNOM(nom.getText());
            me.setADR(adr.getText());
            me.setDate(date.getText());
            me.setDESC(desc.getText());
            Scene scene = new Scene(root);
            // Obtenir la fenêtre principale depuis l'événement
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Changer la scène de la fenêtre principale
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Etes vous sur de vouloir supprimer cet evenement?");
        alert.setContentText("cette action ne peut pas etre annulée.");
        ButtonType ok=new ButtonType("Confirmer");
        ButtonType annuler=new ButtonType("Annuler");

        alert.getButtonTypes().setAll(ok,annuler);
        alert.showAndWait().ifPresent(response->{
            if (response==ok){
                ps.supprimerParId(idEvent);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventAdmin.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                // Obtenir la fenêtre principale depuis l'événement
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Changer la scène de la fenêtre principale
                primaryStage.setScene(scene);
                primaryStage.show();
            }else{
                alert.close();
            }

        });

    }

}
