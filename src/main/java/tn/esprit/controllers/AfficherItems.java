package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import tn.esprit.models.Commande;
import tn.esprit.models.Item;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.*;
import tn.esprit.utils.SessionManager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AfficherItems {

    @FXML
    private TilePane MainTile;

    @FXML
    private TilePane TilePane;

    @FXML
    private ImageView cartButton;

    @FXML
    private Button addButton;

    private int selectedItemId;
    int userIdToFind;

    private List<Commande> userCart = new ArrayList<>();

    @FXML
    private int getUserFromDatabase() {
        userIdToFind = SessionManager.getId_user();
        System.out.println(userIdToFind);
        ServiceUtilisateur su = new ServiceUtilisateur();
        ArrayList<Utilisateur> users = su.getAll();
        boolean userFound = false;

        if (users != null) {
            for (Utilisateur user : users) {
                if (user.getId() == userIdToFind) {
                    userFound = true;
                    System.out.println("User found: " + user.getNom());
                    break;
                }
            }
        }
        if (!userFound) {
            System.out.println("User with ID " + userIdToFind + " not found");
        }
        return userIdToFind;
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
    @FXML
    private void initialize() {
        ServiceItem si = new ServiceItem();
        ArrayList<Item> items = si.getAll();
        getUserFromDatabase();

        SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId("a245d294c1cc4ff89fe2bec140c9be7c").setClientSecret("9f7fc970846c4a51a17265779c5e2754").build();

        for (Item item : items) {
            String title = item.getTitre();
            String artist = item.getAuteur();

            SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("track:" + title + " artist:" + artist).build();
            //System.out.println("Track " + title + " by " + artist);

            try {
                final Track[] tracksArray = searchTracksRequest.execute().getItems();
                List<Track> tracksList = new ArrayList<>();
                for (Track track : tracksArray) {
                    tracksList.add(track);
                }
                for (Track track : tracksList) {
                    String previewUrl = track.getPreviewUrl();
                }
            }  catch (IOException | SpotifyWebApiException | ParseException e) {
                //System.out.println("Error: " + e.getMessage());
            }
        }

        for (Item item : items) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomTilePane.fxml"));
            try {
                Parent root = loader.load();
                CTP controller = loader.getController();
                controller.initData(item);
                TilePane customTilePane = controller.getCustomTilePane();
                MainTile.setHgap(15);
                MainTile.setVgap(10);
                MainTile.setPadding(new Insets(15));
                MainTile.getChildren().add(customTilePane);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void addbtn(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterItem.fxml"));
        try {
            Parent root = loader.load();
            AjouterItem controller = loader.getController();
            controller.setIntValue(0);
            Scene scene = new Scene(root);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ViewCart(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommande.fxml"));
        try {
            Parent root = loader.load();
            AfficherCommande controller = loader.getController();
            ServiceCommande serviceCommande = new ServiceCommande();
            userCart = serviceCommande.getCommandesUtilisateur(getUserFromDatabase());
            createAndFillCommand();
            for(Commande commande : userCart){
                System.out.println(commande.getIdc());
            }
            controller.initCart(userCart);
            Scene scene = new Scene(root);
            Stage stage = (Stage) cartButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAndFillCommand() {
        System.out.println("selected items " + selectedItemId);
        int userId = userIdToFind;
        float totalPrice = 0.0f;
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Commande newCommande = new Commande(userId, selectedItemId, totalPrice, currentDate);
        userCart.add(newCommande);
    }
}
