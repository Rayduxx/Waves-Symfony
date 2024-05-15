package tn.esprit.utils;

import java.sql.Date;

public class SessionManager {
    private static SessionManager instance;

    private static int id_user;
    private static String nom;
    private static String prenom;
    private static int numtel;
    private static String email;
    private static String role;
    private static String image;
    private static Date ddn;
    private static boolean verified;
    private static String pays;

    private SessionManager(int id_user , String nom , String prenom , int numtel , String email, String role, String image, Date ddn, boolean verified, String pays) {
        SessionManager.id_user=id_user;
        SessionManager.nom=nom;
        SessionManager.prenom=prenom;
        SessionManager.numtel=numtel;
        SessionManager.email=email;
        SessionManager.role=role;
        SessionManager.image=image;
    }
    public static SessionManager getInstace(int id_user , String nom , String prenom , int numtel , String email , String role, String image, Date ddn, boolean verified, String pays) {
        if(instance == null) {
            instance = new SessionManager(id_user, nom , prenom, numtel, email, role, image, ddn, verified, pays);
        }
        return instance;
    }

    public static SessionManager getInstance() {return instance;}

    public static int getId_user() {return id_user;}

    public static String getNom() {return nom;}

    public static String getPrenom() {return prenom;}

    public static String getEmail() {return email;}

    public static int getNumtel() {return numtel;}

    public static String getRole() {return role;}

    public static String getImage() {return image;}

    public static void setInstance(SessionManager instance) {SessionManager.instance = instance;}

    public static void setId_user(int id_user) {SessionManager.id_user = id_user;}

    public static void setNom(String nom) {SessionManager.nom = nom;}

    public static void setPrenom(String prenom) {SessionManager.prenom = prenom;}

    public static void setNumtel(int numtel) {SessionManager.numtel = numtel;}

    public static void setEmail(String email) {SessionManager.email = email;}

    public static void setRole(String role) {SessionManager.role = role;}

    public static void setImage(String image) {SessionManager.image = image;}

    public static void cleanUserSession() {
        id_user=0;
        nom="";
        prenom="";
        numtel=0;
        email="";
        role="";
        image="";
    }

}