package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Formation;
import tn.esprit.services.FormationService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CardFormController {


    @FXML
    private Label descriptionL;

    @FXML
    private Button delete;

    @FXML
    private Label videoL;

    @FXML
    private ImageView imageL;

    @FXML
    private Label titreL;

    @FXML
    private VBox vbox;

    public Formation formation;

    private int id;


    private String[] colors = {"#FFB5E8", "#FF9CEE", "#FFCCF9", "#FCC2FF", "#F6A6FF", "#B28DFF", "#C5A3FF", "#D5AAFF", "#ECD4FF", "#FBE4FF", "#DCD3FF", "#A79AFF", "#B5B9FF", "#97A2FF",
            "#AFCBFF", "#AFF8DB", "C4FAF8", "#85E3FF", "#ACE7FF", "#6EB5FF", "#BFFCC6", "#DBFFD6", "#F3FFE3", "#E7FFAC", "#FFFFD1", "#FFC9DE", "#FFABAB", "#FFBEBC", "#FFCBC1", "#FFF5BA"};

    public void setData(Formation formation) {
        this.formation = formation;
        titreL.setText(formation.getTitre());
        videoL.setText(formation.getVideo());
        descriptionL.setText(formation.getDescription());
        Image image = new Image(formation.getAffiche());
        imageL.setImage(image);


        vbox.setBackground(Background.fill(Color.web(colors[(int) (Math.random() * colors.length)])));
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {

        ModifierFormation.formation=formation;
        Parent root = FXMLLoader.load(getClass().getResource("/ModifierFormation.fxml"));
        Scene tableScene =new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }

    public void delete(ActionEvent event) {
        FormationService service = new FormationService();

        // Confirmation de la suppression
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet Formation ?");

        Optional<ButtonType> result = alert.showAndWait(); // Attendre la réponse de l'utilisateur

        // Si l'utilisateur a confirmé la suppression
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer le sport
            service.delete(formation);

            // Charger la nouvelle vue
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/details.fxml"));
                Parent tableViewParent = loader.load();
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }















