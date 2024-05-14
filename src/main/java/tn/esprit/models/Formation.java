package tn.esprit.models;

import javafx.collections.ObservableList;

public class Formation {
    private int id;
    private String titre;
    private String description;
    private String affiche;

    private String video;

    public Formation() {
    }

    public Formation(int id, String titre, String description, String affiche, String video) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.affiche = affiche;
        this.video = video;

    }


    public Formation(String titre, String description, String affiche, String video) {
        this.titre = titre;
        this.description = description;
        this.affiche = affiche;
        this.video = video;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "formation{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", affiche='" + affiche + '\'' +
                ", video='" + video + '\'' +
                '}';
    }


}
