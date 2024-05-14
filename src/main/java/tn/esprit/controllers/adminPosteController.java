package tn.esprit.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Poste;
import tn.esprit.services.ServicePoste;
import tn.esprit.utils.SessionManager;
import com.itextpdf.layout.Document;

import com.itextpdf.kernel.pdf.PdfDocument;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminPosteController {
    ServicePoste servicePoste=new ServicePoste();
    @FXML
    private TableColumn<Poste, String> Cartiste;

    @FXML
    private TableColumn<Poste, String> Cdescription;

    @FXML
    private TableColumn<Poste, String> Cgenre;

    @FXML
    private TableColumn<Poste, String> Cimage;

    @FXML
    private Button Cmodif;

    @FXML
    private TableColumn<Poste, String> Cmorceau;

    @FXML
    private Button Crecherche;

    @FXML
    private Button Csupp;

    @FXML
    private TableView<Poste> Ctable;

    @FXML
    private TableColumn<Poste, String> Ctitre;

    @FXML
    private Button nomrech;

    @FXML
    private Button Ctrier;

    @FXML
    private TextField artisteT;

    @FXML
    private TextField descriptionT;

    @FXML
    private ChoiceBox<String> genreT;

    @FXML
    private TextField imageT;

    @FXML
    private TextField morceauT;

    @FXML
    private TextField recherche;

    @FXML
    private TextField titreT;

    @FXML
    private BarChart<String, Integer> genreChart;

    private final ServicePoste ps = new ServicePoste();

    @FXML
    void initialize() {
        List<Poste> postes = ps.getAll();
        ObservableList<Poste> observableList = FXCollections.observableList(postes);
        Ctable.setItems(observableList);

        Ctitre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        Cartiste.setCellValueFactory(new PropertyValueFactory<>("Artiste"));
        Cgenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        Cimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        Cmorceau.setCellValueFactory(new PropertyValueFactory<>("morceau"));
        Cdescription.setCellValueFactory(new PropertyValueFactory<>("Desscription"));

        // Afficher les statistiques sur les genres
        afficherStatistiquesGenres(postes);
    }

    private void afficherStatistiquesGenres(List<Poste> postes) {
        Map<String, Integer> genreCounts = new HashMap<>();
        for (Poste poste : postes) {
            String genre = poste.getGenre();
            genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        genreCounts.forEach((genre, count) -> series.getData().add(new XYChart.Data<>(genre, count)));
        genreChart.getData().add(series);

        // Définir la largeur des barres de statistiques
        for (XYChart.Data<String, Integer> data : series.getData()) {
            data.getNode().setStyle(data.getNode().getStyle() + "-fx-bar-width: 5px;");
            data.getNode().setStyle(data.getNode().getStyle() + "-fx-bar-fill: #B469FF;");
            genreChart.setBarGap(50);
            genreChart.setCategoryGap(10);
            genreChart.setLegendSide(Side.TOP);
        }
    }
    @FXML
    void supprimer(ActionEvent actionEvent) {
        Poste poste = Ctable.getSelectionModel().getSelectedItem();

        if (poste != null) {
            supprimerLigne(poste);
        } else {
            afficherAlerteErreur("Veuillez sélectionner une ligne à supprimer.");
        }
    }

    private void supprimerLigne(Poste poste) {
        Ctable.getItems().remove(poste);
        ps.delete(poste);
    }

    @FXML
    void modifier(ActionEvent actionEvent) {
        Poste posteSelectionne = Ctable.getSelectionModel().getSelectedItem();
        if (posteSelectionne != null) {
            String titre = titreT.getText();
            String artiste = artisteT.getText();
            String genre = genreT.getValue();
            String image = imageT.getText();
            String morceau = morceauT.getText();
            String description = descriptionT.getText();

            posteSelectionne.setTitre(titre);
            posteSelectionne.setArtiste(artiste);
            posteSelectionne.setGenre(genre);
            posteSelectionne.setImage(image);
            posteSelectionne.setDescription(description);

            Ctable.refresh();

            ps.update(posteSelectionne);

            titreT.clear();
            artisteT.clear();
            genreT.getSelectionModel().clearSelection();
            imageT.clear();
            morceauT.clear();
            descriptionT.clear();
        } else {
            afficherAlerteErreur("Veuillez sélectionner un élément à modifier.");
        }
    }

    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void selection(javafx.scene.input.MouseEvent mouseEvent) {
        Poste selectedPoste = Ctable.getSelectionModel().getSelectedItem();
        if (selectedPoste != null) {
            titreT.setText(selectedPoste.getTitre());
            artisteT.setText(selectedPoste.getArtiste());
            genreT.setValue(selectedPoste.getGenre());
            imageT.setText(selectedPoste.getImage());
            morceauT.setText(selectedPoste.getMorceau());
            descriptionT.setText(selectedPoste.getDescription());
        }
    }

    public void trier(ActionEvent actionEvent) {
        List<Poste> postes = ps.triPostetBytitre();
        ObservableList<Poste> observableList = FXCollections.observableList(postes);
        Ctable.setItems(observableList);

        Ctitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        Cartiste.setCellValueFactory(new PropertyValueFactory<>("artiste"));
        Cgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        Cimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        Cmorceau.setCellValueFactory(new PropertyValueFactory<>("morceau"));
        Cdescription.setCellValueFactory(new PropertyValueFactory<>("description"));


    }
    @FXML
    public void recherche(ActionEvent actionEvent) {
        String nomRecherche = recherche.getText(); // Obtenir le nom à rechercher depuis le champ de texte
        List<Poste> resultats = ps.recherchePoste(String.valueOf(nomRecherche)); // Appel de la méthode rechercheParNom
        ObservableList<Poste> observableResultats = FXCollections.observableList(resultats);
        Ctable.setItems(observableResultats);
    }


    private void loadScene(String scenePath,ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(scenePath));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void Menu1(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
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
            stage.setTitle("Waves - Menu");
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
            SessionManager.cleanUserSession();
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
            stage.setTitle("Waves - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void PDF(ActionEvent actionEvent) {
        // Créer un sélecteur de fichiers pour enregistrer le PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            try {
                // Initialisez le writer PDF
                PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf, PageSize.A4);



                // Créer une page de fond rouge
                pdf.addNewPage().getPdfObject().put(PdfName.BackgroundColor, new PdfArray(new float[]{1, 0, 0}));

                // Ajouter un titre au document
                PdfFont font = PdfFontFactory.createFont();
                document.add(new Paragraph("LISTE DES POSTES").setFont(font).setFontSize(16).setTextAlignment(TextAlignment.CENTER));

                // Créer une table pour afficher les postes
                Table table = new Table(4);
                table.setWidth(400);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                table.setMarginTop(50);

                // Ajouter les en-têtes de colonnes
                table.addHeaderCell("Titre");
                table.addHeaderCell("Artiste");
                table.addHeaderCell("Genre");
                table.addHeaderCell("Description");


                // Récupérer les postes depuis la base de données
                ServicePoste servicePoste = new ServicePoste();
                List<Poste> postes = servicePoste.getAll();

                // Ajouter les données des postes à la table
                for (Poste poste : postes) {
                    table.addCell(poste.getTitre());
                    table.addCell(poste.getArtiste());
                    table.addCell(poste.getGenre());
                    table.addCell(poste.getDescription());
                }

                // Ajouter la table au document
                document.add(table);

                // Fermer le document
                document.close();

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                // En cas d'erreur, afficher une alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la génération du PDF : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
