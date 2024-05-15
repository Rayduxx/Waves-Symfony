package tn.esprit.utils;

import tn.esprit.models.Poste;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailController {
    private Poste poste;

    public void SendMail(String st) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("waves.esprit@gmail.com", "tgao tbqg wudl aluo");//szwv ifts mrbf fzoa
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(st));
        message.setSubject("Confirmation");
        message.setText("Madame, Monsieur,\n" +
                "\n" +
                "Nous avons le plaisir de vous informer que votre demande d'ajout d'une nouvelle poste a été prise en compte avec succès.\n" +
                "\n" +
                "Voici un récapitulatif des informations de la poste ajoutée :\n" +
                "\n" +
                "Votre demande a été traitée et la poste a été ajoutée à notre base de données. Nous vous remercions pour votre contribution à notre plateforme.\n" +
                "\n" +
                "N'hésitez pas à nous contacter si vous avez des questions ou des préoccupations supplémentaires.\n" +
                "\n" +
                "Cordialement,\n" +
                "[L'Équipe de Waves]");


        Transport.send(message);
    }

    public void SendMailEvent(String st) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("waves.esprit@gmail.com", "tgao tbqg wudl aluo");//szwv ifts mrbf fzoa
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(st));
        message.setSubject("Réservation");
        message.setText("Reservation");
        message.setSubject("Confirmation Réservation");
        message.setText("On vous informe que votre reservation est accèpté. " +
                "Bienvenue dans notre évenement." +
                "On vous souhaite une bonne soirée. ");



        Transport.send(message);
    }
}
