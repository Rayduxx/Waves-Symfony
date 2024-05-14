package tn.esprit.models;

public class Item {

    private int itemID;
    private String titre;
    private String description;
    private String auteur;
    private float prix;

    public Item() {
    }

    public Item(int itemID, String titre, String description, String auteur, float prix) {
        this.itemID = itemID;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.prix = prix;
    }

    public Item(String titre, String description, String auteur, float prix) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.prix = prix;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", auteur='" + auteur + '\'' +
                ", prix=" + prix +
                '}';
    }
}
