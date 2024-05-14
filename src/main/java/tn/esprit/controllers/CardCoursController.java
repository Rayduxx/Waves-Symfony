package tn.esprit.controllers;

import tn.esprit.models.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CardCoursController {

    @FXML
    private Label formC;

    @FXML
    private Label localisationT;

    @FXML
    private Button supprime;

    @FXML
    private Label titreC;

    @FXML
    private VBox vbox;

    public Cours cours;


    public void setData(Cours cours) {
        this.cours = cours;
        titreC.setText(cours.getTitre_cours());
        localisationT.setText(cours.getDuree_cours());
        formC.setText(cours.getFormation().getTitre());
    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void modifier(ActionEvent event) {

    }

}
