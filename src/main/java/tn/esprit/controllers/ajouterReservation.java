package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceEvent;
import tn.esprit.services.ServiceReservation;
import tn.esprit.utils.MailController;
import tn.esprit.utils.PdfController;

import javax.mail.MessagingException;
import java.io.IOException;

import static java.lang.Float.parseFloat;

public class ajouterReservation {
    @FXML
    private TextField date;

    @FXML
    private TextField statut;
    @FXML
    private ComboBox<Integer> nb;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField email;

    @FXML
    private Button exportPDFButton;
    @FXML
    private Button payer;
    @FXML
    private Button AJ;
    private  int IdUser;
    private  int Eid;
    private  int Id;

    public MailController mail = new MailController() ;

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getIdEvent() {
        return Eid;
    }

    public void setIdEvent(int Eid) {
        this.Eid = Eid;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private final ServiceReservation ps = new ServiceReservation();
    private final ServiceEvent rs = new ServiceEvent();

    public void handleExportPDF() {
        int eventId = getIdEvent();

        String event = rs.getNomById(eventId);
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        String Email = email.getText();
        String dateReservation = date.getText();
        String statutReservation = statut.getText();
        PdfController pdfController = new PdfController();
        pdfController.exportToPDF2(Nom, Prenom, Email, event, dateReservation, statutReservation);
    }
    @FXML
    void ajouterReservation(ActionEvent event) throws IOException, MessagingException {

        int eventId = getIdEvent();
        ps.Add(new Reservation(0,0,eventId,date.getText(),statut.getText(),nom.getText(),prenom.getText(),nb.getValue(),email.getText() ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("succes");
        alert.setHeaderText("succes");
        alert.setContentText("Reservation ajouter avec succes");
        alert.showAndWait();
        System.out.println(eventId);
        exportPDFButton.setVisible(true);
        payer.setVisible(true);
        AJ.setVisible(false);
        mail.SendMailEvent(email.getText());
    }
    @FXML
    void AfficherEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventUser.fxml"));
            Parent root = loader.load();
            DetailsUser controller = loader.getController();
            controller.initData(date.getText());
            statut.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void afficherReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent root = loader.load();
            afficherReservation controller = loader.getController();
            controller.initData(date.getText());
            statut.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Evenements");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        ObservableList<Integer> numeros = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            numeros.add(i);
        }
        nb.setItems(numeros);
    }
    public void processPayment() {
        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Paiment");
            confirmationAlert.setHeaderText("Confirmation de paiment");
            confirmationAlert.setContentText("Voulez-vous payer avec cette carte");
            confirmationAlert.showAndWait();
            Stripe.apiKey = "sk_test_51Or0FWIG2J3RtgQuCc7dZ4rAAiapfB42HPWC43wraAo8UKiE52xyjb3DtTFwZ7UJcYBGjKiKtRjw3Hl9LUmTyvvh00aeSs2Lw0";
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) parseFloat("100"))
                    .setCurrency("usd")
                    .build();
            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}
