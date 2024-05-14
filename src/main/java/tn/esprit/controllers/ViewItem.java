package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Item;

public class ViewItem {

    @FXML
    private Label TAuteur;

    @FXML
    private Label TDescription;

    @FXML
    private Label TPrix;

    @FXML
    private Label TTitre;
    public void setTitre(String titre) {
        TTitre.setText(titre);
    }

    public void setDescription(String description) {
        TDescription.setText(description);
    }

    public void setAuteur(String auteur) {
        TAuteur.setText(auteur);
    }

    public void setPrix(double prix) {
        TPrix.setText(String.valueOf(prix) + " DT");
    }

    public void initData(Item item) {
        setTitre(item.getTitre());
        setDescription(item.getDescription());
        setAuteur(item.getAuteur());
        setPrix(item.getPrix());
    }

}
