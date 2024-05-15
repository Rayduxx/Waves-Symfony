package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.models.Item;
import tn.esprit.services.ServiceItem;

import java.io.IOException;

public class AjouterItem {

    ServiceItem si = new ServiceItem();

    private int x;

    @FXML
    private TextField Auth;

    @FXML
    private TextField Desc;

    @FXML
    private TextField Titre;

    @FXML
    private TextField prix;

    @FXML
    private Button mpButton;

    @FXML
    private Label titreErrorLabel;

    @FXML
    private Label descErrorLabel;

    @FXML
    private Label authErrorLabel;

    @FXML
    private Label prixErrorLabel;

    public void setIntValue(int intValue) {
        this.x = intValue;
    }
    @FXML
    void Add(ActionEvent event) {
        boolean error = false;
        if (Titre.getText().isEmpty()) {
            titreErrorLabel.setVisible(true);
            error = true;
        } else {
            titreErrorLabel.setVisible(false);
        }
        if (Desc.getText().isEmpty()) {
            descErrorLabel.setVisible(true);
            error = true;
        } else {
            descErrorLabel.setVisible(false);
        }
        if (Auth.getText().isEmpty()) {
            authErrorLabel.setVisible(true);
            error = true;
        } else {
            authErrorLabel.setVisible(false);
        }
        if (prix.getText().isEmpty()) {
            prixErrorLabel.setVisible(true);
            error = true;
        } else {
            prixErrorLabel.setVisible(false);
        }

        if (!error) {
            si.Add(new Item(Titre.getText(), Desc.getText(), Auth.getText(), Float.parseFloat(prix.getText())));
        }
    }

    @FXML
    void mp(ActionEvent event) {
        if(x == 1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherItemsAdmin.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) mpButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherItems.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) mpButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
