package tn.esprit.utils;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.models.Reservation;

import java.io.File;
import java.io.IOException;

public class PdfController {

    // dependance , pdfcontroller , afficher Reservation ( bouton et code )

    public void exportToPDF(ObservableList<Reservation> reservations) {
        Stage stage = new Stage();

        try {
            // Créer un document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Définir le titre en rouge et en grand
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.setLeading(30); // Espacement entre les lignes
            contentStream.newLineAtOffset(200, 750); // Position du titre
            contentStream.setNonStrokingColor(255, 0, 0); // Couleur rouge
            contentStream.showText("Liste des Réservations");
            contentStream.endText();

            // Définir les coordonnées de départ pour l'écriture
            float y = page.getMediaBox().getHeight() - 100;

            // Écrire les en-têtes de colonnes
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(0, 0, 0); // Couleur noire pour les en-têtes
            contentStream.showText("ID");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Date");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Statut");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("ID de l'Événement");
            contentStream.endText();

            // Écrire les données des réservations
            y -= 30;
            for (Reservation reservation : reservations) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, y);
                contentStream.setNonStrokingColor(0, 0, 0); // Couleur noire pour les données des réservations
                contentStream.showText(String.valueOf(reservation.getId()));
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(reservation.getDate_reservation());
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(reservation.getStatut());
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(String.valueOf(reservation.getId_evenement()));
                contentStream.endText();
                y -= 20;
            }

            contentStream.close();

            // Enregistrer le document PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                document.save(file);
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void exportToPDF2(String nom, String prenom, String email, String event, String dateReservation, String statutReservation) {
        Stage stage = new Stage();

        try {
            // Créer un document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Titre du reçu de réservation
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("Reçu de réservation");
            contentStream.endText();

            // Informations sur la réservation
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Nom : " + nom);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Prénom : " + prenom);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("E-mail : " + email);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Événement : " + event);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Date de réservation : " + dateReservation);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Statut de réservation : " + statutReservation);
            contentStream.endText();

            contentStream.close();

            // Enregistrer le document PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                document.save(file);
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}