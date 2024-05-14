package tn.esprit.controllers;

import atlantafx.base.controls.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

public class CardUserController {
    @FXML
    private Pane Card;
    @FXML
    private Label actvttf;
    @FXML
    private Label cardemail;
    @FXML
    private ImageView cardimage;
    @FXML
    private Label cardnameprename;
    @FXML
    private Label cardnumtel;
    @FXML
    private Label cardrole;
    @FXML
    private Label ddntf;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();

    int uid,unumtel;
    String unom, uprenom, uemail, umdp, urole,upays;
    boolean uverf;
    Date uddn;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};

    public void setData(Utilisateur user) {
        String imagePath = user.getImage();
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                cardimage.setImage(image);
            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imagePath);
            }
        } else {
            System.err.println("Image path is null for user: " + user);
        }
        if (user.isIs_verified()) {
            actvttf.setText("Verifié");
        }
        if (!user.isIs_verified()) {
            actvttf.setText("Non verifié");
        }
        ddntf.setText(String.valueOf(user.getBirthday()));
        cardnameprename.setText(user.getNom() + " " + user.getPrenom());
        cardemail.setText(user.getEmail());
        cardrole.setText(user.getRole());
        cardnumtel.setText(String.valueOf(user.getNumtel()));
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        uprenom = user.getPrenom();
        uid = user.getId();
        unom = user.getNom();
        uemail = user.getEmail();
        umdp = user.getPassword();
        urole = user.getRole();
        unumtel = user.getNumtel();
        uverf = user.isIs_verified();
        uddn = user.getBirthday();
        upays = user.getCountry();
    }

    public void suppuser(ActionEvent actionEvent) throws IOException {
        UserS.DeleteByID(uid);
    }

    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            AdminUserController AUC = loader.getController();
            AUC.idtf.setText(String.valueOf(uid));
            AUC.numteltf.setText(String.valueOf(unumtel));
            AUC.nomtf.setText(unom);
            AUC.prenomtf.setText(uprenom);
            AUC.emailtf.setText(uemail);
            AUC.mdptf.setText(umdp);
            AUC.ddntf.setValue(uddn.toLocalDate());
            AUC.paystf.setText(upays);
            if (uverf){AUC.activatedtf.setText("1");}
            if (!uverf){AUC.activatedtf.setText("0");}
            AUC.rolecb.setValue(urole);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
