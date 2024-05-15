package tn.esprit.controllers;

import com.sun.mail.imap.protocol.ID;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Poste;
import tn.esprit.services.ServiceCommentaire;
import tn.esprit.services.ServicePoste;
import tn.esprit.utils.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class CardController implements Initializable {
    @FXML
    private Label artisteL;
    @FXML
    private AnchorPane anchp;
    @FXML
    private Label descriptionL;
    @FXML
    private Label genreL;
    @FXML
    private ImageView imageL;
    @FXML
    private Label titreL;
    @FXML
    private VBox vbox;
    @FXML
    private VBox Box;
    @FXML
    private Button upload;
    @FXML
    private TextField AJ;
    private Poste poste;

    // private final ServicePoste PosteS = new ServicePoste();
    private ServiceCommentaire sc = new ServiceCommentaire();
    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};

    public void setData(Poste poste) {
        this.poste = poste;
        anchp.setBackground(Background.fill(Color.web(colors[(int) (Math.random() * colors.length)])));
        titreL.setText(poste.getTitre());
        artisteL.setText(poste.getArtiste());
        genreL.setText(poste.getGenre());
        descriptionL.setText(poste.getDescription());
        Image image = new Image(poste.getImage());
        imageL.setImage(image);
        List<Commentaire> commentaires = sc.getAllCommentairesByPoste(poste);
        for (Commentaire commentaire : commentaires) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((getClass().getResource("/cardComm.fxml")));
                AnchorPane commentCard = loader.load();
                CardCommController CCC = loader.getController();
                CCC.setData(commentaire);
                Box.getChildren().add(commentCard);
            } catch (IOException e) {
                System.out.println("Erreur lors du chargement des cartes de commentaire : " + e.getMessage());
            }
        }
    }


    public void AjouterCom(ActionEvent actionEvent) {
        String commentaireText = AJ.getText().trim();
        int IDuser = SessionManager.getId_user();
        if (!commentaireText.isEmpty()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setComment(commentaireText);
            commentaire.setPoste(poste);
            ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
            serviceCommentaire.addComm2(commentaire, poste,IDuser);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PosteInterface.fxml"));
                Parent tableViewParent = loader.load();
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir votre commentaire.");
            alert.showAndWait();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void supprime(ActionEvent actionEvent) {
        ServicePoste service = new ServicePoste();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Sport ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.delete(poste);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PosteInterface.fxml"));
                Parent tableViewParent = loader.load();
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }







   /* private void afficherCommentaires(List<Commentaire> commentaires) {
       // Boxes.getChildren().clear(); // Effacer le contenu précédent (au cas où)
        for (Commentaire commentaire : commentaires) {
            // Créez une nouvelle instance de CardCommentaire
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardComm.fxml"));
            try {
                Parent cardCommentaire = loader.load();
                CardCommController controller = loader.getController();
                // Définissez les données du commentaire dans le CardCommentaire
                controller.setData(commentaire);
                // Ajoutez le CardCommentaire à votre VBox
                Box.getChildren().add(cardCommentaire);
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'erreur d'ouverture du fichier FXML
            }
        }}*/
}

