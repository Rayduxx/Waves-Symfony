package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Production;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceProduction;
import tn.esprit.utils.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProdAdminController implements Initializable {
    @FXML
    public TextField covertf;
    @FXML
    public ImageView imageCover;
    @FXML
    public TextField idtf;
    @FXML
    public TextArea desctf;
    @FXML
    public TextField genretf;
    @FXML
    public RadioButton radbonh;
    @FXML
    public RadioButton radcalm;
    @FXML
    public RadioButton radenergie;
    @FXML
    public RadioButton radsent;
    @FXML
    private TextField recherche;
    @FXML
    public TextField titretf;
    @FXML
    private GridPane userContainer;
    @FXML
    private Label uinfolabel;
    private final ServiceProduction ProdS = new ServiceProduction();

    public void initialize(URL url, ResourceBundle rb) {
    load();
    }
    @FXML
    void uploadPDP(ActionEvent event) {
        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) titretf.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destinationFolder = Paths.get("src/main/resources/Waves-Symfony/public/uploads");
                if (!Files.exists(destinationFolder)) {
                    Files.createDirectories(destinationFolder);
                }
                String fileName = UUID.randomUUID().toString() + "_" + selectedFile.getName();
                Path destinationPath = destinationFolder.resolve(fileName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destinationPath.toString();
                System.out.println("Image uploaded successfully: " + imagePath);
                covertf.setText(fileName);
                if (imagePath != null) {
                    try {
                        File file = new File(imagePath);
                        FileInputStream inputStream = new FileInputStream(file);
                        Image image = new Image(inputStream);
                        imageCover.setImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void AjouterProd(ActionEvent actionEvent) {
        String TITRE = titretf.getText();
        String DESC = desctf.getText();
        String GENRE = genretf.getText();
        String TAG = null;
        String COVER = covertf.getText();
        if (radbonh.isPickOnBounds()){TAG="Bonheur";}
        if (radcalm.isPickOnBounds()){TAG="Calme";}
        if (radsent.isPickOnBounds()){TAG="Sentimental";}
        if (radenergie.isPickOnBounds()){TAG="Energie";}
        if (TITRE.matches("^[_A-Za-z-\\+ ]+$") && GENRE.matches("^[_A-Za-z-\\+ ]+$")){
            ProdS.Add(new Production(0, SessionManager.getId_user(), TITRE, GENRE, DESC, TAG, COVER));
            uinfolabel.setText("Ajout d'un projet avec success");
        }else{
            uinfolabel.setText("Titre doit pas etre avec des chiffres");
        }
    }
    @FXML
    public void ModifierProd(ActionEvent actionEvent) {
        int ID = Integer.parseInt(idtf.getText());
        String TITRE = titretf.getText();
        String DESC = desctf.getText();
        String GENRE = genretf.getText();
        String TAG = null;
        String COVER = covertf.getText();
        if (radbonh.isPickOnBounds()){TAG="Bonheur";}
        if (radcalm.isPickOnBounds()){TAG="Calme";}
        if (radsent.isPickOnBounds()){TAG="Sentimental";}
        if (radenergie.isPickOnBounds()){TAG="Energie";}
        if (TITRE.matches("^[_A-Za-z-\\+ ]+$") && GENRE.matches("^[_A-Za-z-\\+ ]+$")){
            ProdS.Update2(new Production(ID, TITRE, GENRE, DESC, TAG,COVER));
        }else{
            uinfolabel.setText("Titre doit pas etre avec des chiffres");
        }
    }
    @FXML
    public void refresh(ActionEvent actionEvent) {
        load();
    }

    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Production Prod : ProdS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProd.fxml"));
                Pane userBox = fxmlLoader.load();
                CardProdController cardC = fxmlLoader.getController();
                cardC.setData(Prod);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Recherche(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        String rech = recherche.getText();
        try {
            userContainer.getChildren().clear();
            for (Production prod : ProdS.Rechreche(rech)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProd.fxml"));
                Pane userBox = fxmlLoader.load();
                CardProdController cardC = fxmlLoader.getController();
                cardC.setData(prod);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void TriNom(ActionEvent actionEvent){
        int column = 0;
        int row = 1;
        try {
            for (Production prod : ProdS.TriparTitre()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProd.fxml"));
                Pane userBox = fxmlLoader.load();
                CardProdController cardC = fxmlLoader.getController();
                cardC.setData(prod);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void TriGenre(ActionEvent actionEvent){
        int column = 0;
        int row = 1;
        try {
            for (Production prod : ProdS.TriparTitre()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProd.fxml"));
                Pane userBox = fxmlLoader.load();
                CardProdController cardC = fxmlLoader.getController();
                cardC.setData(prod);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Menu1(ActionEvent actionEvent) {
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
    @FXML
    public void Deconnection(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Connection");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toContAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminPoste.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toProdAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProdAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toMarAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherItemsAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toEventAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toFormAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toUserAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
