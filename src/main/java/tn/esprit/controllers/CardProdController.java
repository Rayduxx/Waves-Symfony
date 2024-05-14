package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Production;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceProduction;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;

public class CardProdController {
    @FXML
    private Pane Card;
    @FXML
    private Label carddesc;
    @FXML
    private Label cardgenre;
    @FXML
    private Label cardtag;
    @FXML
    private Label cardtitre;
    private final ServiceProduction ProdS = new ServiceProduction();
    int pid;
    String ptitre, pdesc, pgenre, ptag;
    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};
    public void setData(Production prod) {
        cardtitre.setText(prod.getNom());
        carddesc.setText(prod.getDesc());
        cardgenre.setText(prod.getGenre());
        cardtag.setText(prod.getTags());
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        ptitre = prod.getNom();
        pdesc = prod.getDesc();
        pgenre = prod.getGenre();
        pid = prod.getId();
        ptag = prod.getTags();
    }


    @FXML
    void modifprod(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProdAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            ProdAdminController PAC = loader.getController();
            PAC.idtf.setText(String.valueOf(pid));
            PAC.desctf.setText(String.valueOf(pdesc));
            PAC.titretf.setText(ptitre);
            PAC.genretf.setText(pgenre);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void suppprod(ActionEvent event) {
        ProdS.DeleteByID(pid);
    }
}
