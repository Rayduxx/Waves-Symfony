package tn.esprit.controllers;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.itextpdf.layout.Document;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Formation;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.FormationService;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherSport implements Initializable {
    @FXML
    public HBox boxes;
    @FXML
    private TextField rechercheF;
    @FXML
    private TextField recherche;
    @FXML
    private Button retour;
    @FXML
    FormationService sp = new FormationService();


    public void initialize(URL location, ResourceBundle resources) {
        List<Formation> formations = sp.getAll();
        setGridFormations(formations);


        rechercheF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                boxes.getChildren().clear();
                List<Formation> filteredSports = sp.rechercheSport(rechercheF.getText());
                setGridFormations(filteredSports);
            }
            else { // Si le champ de recherche est vide
                boxes.getChildren().clear(); // Effacez le contenu actuel
                List<Formation> postes1 = sp.getAll(); // Récupérez toutes les données initiales
                setGridFormations(postes1); // Affichez à nouveau toutes les données initiales
            }});
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
    public void setGridFormations(List<Formation>formations)
    {
        try {
            for (int i = 0; i < formations.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((getClass().getResource("/CardForm.fxml")));

                AnchorPane cardBox = loader.load();
                CardFormController cardController = loader.getController();
                cardController.setData(formations.get(i));

                boxes.getChildren().add(cardBox);


            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }


    public void pdf(ActionEvent event) {
        // Créer un sélecteur de fichiers pour choisir l'emplacement où sauvegarder le PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Initialisez le writer PDF
                PdfWriter writer = new PdfWriter(((File) selectedFile).getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf, PageSize.A4);

                // Ajouter un fond de couleur à la page
                pdf.addNewPage().getPdfObject().put(PdfName.BackgroundColor, new PdfArray(new float[]{1, 0, 0})); // Rouge




                // Ajoutez un titre au document
                PdfFont font = PdfFontFactory.createFont();
                document.add(new Paragraph("Liste des FORMATIONS").setFont(font).setFontSize(16).setTextAlignment(TextAlignment.CENTER));


                // Ajoutez une table pour afficher les événements
                Table table = new Table(5);
                table.setWidth(400);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER); // Centrer le tableau horizontalement
                table.setMarginTop(50);

                // Ajoutez les en-têtes de colonnes
                table.addHeaderCell("Titre");
                table.addHeaderCell("Description");
                table.addHeaderCell("Affiche");
                table.addHeaderCell("Video");


                // Récupérez les événements depuis la base de données
                FormationService formationService = new FormationService();
                List<Formation> formations = formationService.getAll();

                // Ajoutez les données des événements à la table
                for (Formation e : formations) {
                    table.addCell(e.getTitre());
                    table.addCell(e.getDescription());
                    table.addCell(e.getAffiche());
                    table.addCell(e.getVideo());

                }

                // Ajoutez la table au document
                document.add(table);

                // Fermez le document
                document.close();

                // Affichez un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                // En cas d'erreur, affichez une alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la génération du PDF : " + e.getMessage());
                alert.showAndWait();
            }

        }
    }
    @FXML
    public void ajouterform(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
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
}
