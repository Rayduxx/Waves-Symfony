package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import tn.esprit.application.Main;
import tn.esprit.models.Production;
import tn.esprit.services.ServiceProduction;

import java.io.IOException;

public class ProdContoller {
    @FXML
    private TextArea desctf;
    @FXML
    private TextField genretf;
    @FXML
    private TextField nomtf;
    @FXML
    private RadioButton radbonh;
    @FXML
    private RadioButton radcalm;
    @FXML
    private RadioButton radenergie;
    @FXML
    private RadioButton radsent;
    private final ServiceProduction ProdS = new ServiceProduction();
    @FXML
    private static Stage StudioStage;
    public void ajouter(ActionEvent actionEvent) {
        String NOM = nomtf.getText();
        String DESC = desctf.getText();
        String GENRE = genretf.getText();
        String TAG = null;
        if (radbonh.isPickOnBounds()){TAG="Bonheur";}
        if (radcalm.isPickOnBounds()){TAG="Calme";}
        if (radsent.isPickOnBounds()){TAG="Sentimental";}
        if (radenergie.isPickOnBounds()){TAG="Energie";}
        if (NOM.matches("^[_A-Za-z-\\+ ]+$") && GENRE.matches("^[_A-Za-z-\\+ ]+$")){
            ProdS.Add(new Production(0, NOM, GENRE, DESC, TAG));
        }
    }

    private static Stage primaryStage;

    @FXML
    public void ProdP(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/main_layout.fxml"));
        Parent root = fxmlLoader.load();
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        MainController mainController = fxmlLoader.getController();
        mainController.setLightTheme();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Waves Studio");

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(root.minWidth(-1));
        primaryStage.setMinHeight(root.minHeight(-1) + 50);
        primaryStage.setHeight(root.prefHeight(-1));
        primaryStage.setWidth(root.prefWidth(-1));
        primaryStage.setX((bounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((bounds.getHeight() - primaryStage.getHeight()) / 4);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent ->    {
            mainController.exit();
            System.exit(0);
        });
        scene.setOnKeyPressed(ke -> mainController.setKeyPressed(ke.getCode()) );
    }
    public static Stage getPrimaryStage(){return primaryStage;}
    private void setPrimaryStage(Stage p){primaryStage = p;}

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
