package tn.esprit.controllers;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.sql.*;
import javafx.scene.*;
import javafx.scene.control.TextInputDialog;
import javafx.stage.*;
import java.net.URL;
import java.util.*;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.MyDataBase;
import tn.esprit.utils.SessionManager;

import javax.naming.spi.StateFactory;
import java.io.IOException;

public class ConnectionUserController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField email_login;
    @FXML
    private TextField password_login;
    @FXML
    private Label labelinfo;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();
    private Connection cnx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String generateOTP() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private void sendEmailWithOTP(String toEmail, String otp) {
        final String username = "waves.esprit@gmail.com";
        final String password = "tgao tbqg wudl aluo";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("2FA Authentication");
            message.setText("Dear user,\n\nYour 2FA code is: " + otp);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void connecter(ActionEvent actionEvent) throws IOException {
        String qry = "SELECT * FROM `user` WHERE `email`=? AND `password`=?";
        cnx = MyDataBase.getInstance().getCnx();
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password_login.getText().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String HashedPassword = hexString.toString();
            stm.setString(1, email_login.getText());
            stm.setString(2, HashedPassword);
            ResultSet rs = stm.executeQuery();
            Utilisateur CurUser;
            if (rs.next()) {
                CurUser = new Utilisateur(rs.getInt("id"), rs.getString("name"), rs.getString("prename"), rs.getString("email"), rs.getString("password"), rs.getInt("phone"), rs.getString("roles"), rs.getString("image"), rs.getBoolean("is_verified"), rs.getDate("created_at"), rs.getDate("birthday"),rs.getString("country"),rs.getInt("projets") );
                Utilisateur.setCurrent_User(CurUser);
                SessionManager.getInstace(rs.getInt("id"), rs.getString("name"), rs.getString("prename"), rs.getInt("phone"), rs.getString("email"), rs.getString("roles"), rs.getString("image"), rs.getDate("birthday"), rs.getBoolean("is_verified"),rs.getString("country"));
                String role = rs.getString("roles");
                if (role.equals("[\"ROLE_ADMIN\"]")) {
                    try {
                        FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/loadingscene.fxml"));
                        Parent loadingRoot = loadingLoader.load();
                        Stage loadingStage = new Stage();
                        loadingStage.setScene(new Scene(loadingRoot));
                        loadingStage.setTitle("Loading...");
                        loadingStage.show();
                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                        delay.setOnFinished(event -> {
                            Task<Parent> task = new Task<>() {
                                @Override
                                protected Parent call() throws Exception {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
                                    return loader.load();
                                }
                            };
                            task.setOnSucceeded(event2 -> {
                                loadingStage.close();
                                Parent root = task.getValue();
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                                stage.setScene(scene);
                                stage.setTitle("Waves - Admin Dashboard");
                                stage.show();
                            });
                            new Thread(task).start();
                        });
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (role.equals("[\"ROLE_USER\"]")) {
                    try {
                        FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/loadingscene.fxml"));
                        Parent loadingRoot = loadingLoader.load();
                        Stage loadingStage = new Stage();
                        // loadingStage.initModality(Modality.APPLICATION_MODAL);
                        loadingStage.setScene(new Scene(loadingRoot));
                        loadingStage.setTitle("Loading...");
                        loadingStage.show();

                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                        delay.setOnFinished(event -> {
                            Task<Parent> task = new Task<>() {
                                @Override
                                protected Parent call() throws Exception {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
                                    return loader.load();
                                }
                            };
                            task.setOnSucceeded(event2 -> {
                                loadingStage.close();
                                Parent root = task.getValue();
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                                stage.setScene(scene);
                                stage.setTitle("Waves - Admin Dashboard");
                                stage.show();
                            });
                            new Thread(task).start();
                        });
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                labelinfo.setText("Les informations d'identification invalides.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void changePassword(ActionEvent event) {
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("réinitialiser le mot de passe");
        emailDialog.setHeaderText("vérifier votre email");
        emailDialog.setContentText("Veuillez entrer votre Email:");
        Optional<String> emailResult = emailDialog.showAndWait();
        emailResult.ifPresent(email -> {
            ServiceUtilisateur UserS = new ServiceUtilisateur();
            if (UserS.checkUserExists(email)) {
                String otp = generateOTP();
                sendEmailWithOTP(email, otp);
                TextInputDialog otpDialog = new TextInputDialog();
                otpDialog.setTitle("OTP Verification");
                otpDialog.setHeaderText("OTP for Password Reset");
                otpDialog.setContentText("Veuillez entrer le mot de passe à usage unique envoyé à votre Email:");
                Optional<String> otpResult = otpDialog.showAndWait();
                if (otpResult.isPresent() && otpResult.get().equals(otp)) {
                    TextInputDialog newPasswordDialog = new TextInputDialog();
                    newPasswordDialog.setTitle("Change Password");
                    newPasswordDialog.setHeaderText("Enter New Password");
                    newPasswordDialog.setContentText("New Password:");
                    Optional<String> newPasswordResult = newPasswordDialog.showAndWait();
                    newPasswordResult.ifPresent(newPassword -> {
                        boolean updateSuccess = UserS.updatePassword(email, newPassword);
                        if (updateSuccess) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Votre mot de passe a été modifié avec succès");
                            alert.showAndWait();
                        } else {
                            showAlert("Erreur", "Un problème est survenu lors de la modification de votre mot de passe. Veuillez réessayer.");
                        }
                    });
                } else {
                    showAlert("Incorrect OTP", "Le mot de passe que vous avez entré est incorrect. Veuillez réessayer.");
                }
            } else {
                showAlert("Email Invalide", "L’email que vous avez saisi n’existe pas.");
            }
        });
    }

    @FXML
    public void inscription(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Inscription");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
