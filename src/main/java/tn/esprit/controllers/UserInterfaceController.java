package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private GridPane userContainer;
    @FXML
    private TextField usersearch;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
    }

    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void TriNom(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparNom()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        load();
    }
    @FXML
    public void TriEmail(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparEmail()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void RechercheNom(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        String recherche = usersearch.getText();
        try {
            userContainer.getChildren().clear();
            for (Utilisateur user : UserS.Rechreche(recherche)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Menu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
