package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CardUser1Controller {
    @FXML
    private Pane userpane;
    @FXML
    private ImageView pdpimage;
    @FXML
    private Label nomprenomlb;
    @FXML
    private Label emaillb;
    @FXML
    private Label numtellb;
    @FXML
    private Label rolelb;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();
    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};
    public void setData1(Utilisateur user) {
        String imagePath = user.getImage();
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                pdpimage.setImage(image);
            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imagePath);
            }
        } else {
            System.err.println("Image path is null for user: " + user);
        }
        nomprenomlb.setText(user.getNom() + " " + user.getPrenom());
        emaillb.setText(user.getEmail());
        rolelb.setText("+216 "+user.getRole());
        numtellb.setText(String.valueOf(user.getNumtel()));
        userpane.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        userpane.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");
    }
}
