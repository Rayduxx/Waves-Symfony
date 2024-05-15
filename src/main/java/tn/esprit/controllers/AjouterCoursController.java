package tn.esprit.controllers;

import tn.esprit.models.Cours;
import tn.esprit.models.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.services.CoursService;
import tn.esprit.services.FormationService;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AjouterCoursController implements Initializable {
    private static Cours cours;
    private final CoursService fs=new CoursService();

    @FXML
    private ChoiceBox<Formation> idTF;
    @FXML
    private Button Afficher;

    @FXML
    private Button Ajouter;

    @FXML
    private TextField dureeTF;

    @FXML
    private TextField titreTF;
    private Formation formation;


    List<Formation> formations;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formations = new FormationService().getAll();

        List<String> Formationtitre = new ArrayList<>();
        formations.forEach(t -> Formationtitre.add(t.getTitre()));
        idTF.getItems().addAll(formations);

        idTF.setConverter(new StringConverter<Formation>() {

            @Override
            public String toString(Formation formation) {
                if (formation != null) {
                    return formation.getTitre();
                } else {
                    return "";
                }
            }

            @Override
            public Formation fromString(String titre) {
                return null;
            }
        });


    }
    public void getTitre(ActionEvent event){String titre= String.valueOf(idTF.getValue());}

    public void addC(ActionEvent event) throws SQLException {
        String titre = titreTF.getText();
        String duree = dureeTF.getText();

        formation= idTF.getValue();
        Cours nouveauCours = new Cours(titre,duree,formation);

        fs.addCours(nouveauCours,formation);

    }
    public void AfficherC1(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours1.fxml"));
            dureeTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    }





