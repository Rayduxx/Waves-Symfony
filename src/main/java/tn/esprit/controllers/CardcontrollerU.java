package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.ServiceEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class CardcontrollerU {

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
    private VBox vbox;

    private int idEvent;


    private final ServiceEvent ps = new ServiceEvent();

    public void initData(Event event) {
        idEvent=event.getEid();

        nom.setText(event.getNomE());
        adr.setText(event.getAdrE());
        date.setText(event.getDate());
        desc.setText(event.getDesc());
        /*String ImagePath = "src/main/resources/Waves-Symfony/public/uploads/"+event.getImage();
        Image image = new Image(ImagePath);
        imageL.setImage(image);*/
        String imagePath = "src/main/resources/Waves-Symfony/public/uploads/"+event.getImage();
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                imageL.setImage(image);
            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imagePath);
            }
        }
    }

    @FXML
    void Reserver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            ajouterReservation ar = loader.getController();
            ar.setIdEvent(idEvent);
            // Obtenir la fenêtre principale depuis l'événement
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Changer la scène de la fenêtre principale
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        System.out.println("ID de l'événement : " + idEvent);
    }

}
