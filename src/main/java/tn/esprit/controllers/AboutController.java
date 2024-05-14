package tn.esprit.controllers;

import javafx.scene.control.Hyperlink;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController extends Controller {

    public Hyperlink link_github;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        link_github.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Rayduxx/Waves/tree/Gestion-Production"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
    }
}
