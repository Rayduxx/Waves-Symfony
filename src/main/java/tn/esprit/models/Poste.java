package tn.esprit.models;

public class Poste {

    private int id  ;
    private String genre , titre, artiste, description, image, morceau;

    public Poste() {}

    public Poste(int id, String titre, String artiste, String genre, String image, String morceau, String description) {
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
        this.genre = genre;
        this.image = image;
        this.morceau = morceau;
        this.description = description;

    }


    public Poste( String titre, String artiste, String genre, String image, String morceau, String description) {
        this.titre = titre;
        this.artiste = artiste;
        this.genre = genre;
        this.image = image;
        this.morceau = morceau;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMorceau() {
        return morceau;
    }

    public void setMorceau(String morceau) {
        this.morceau = morceau;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Poste{" +
                "id=" + id +
                ", morceau=" + morceau +
                ", genre='" + genre + '\'' +
                ", titre='" + titre + '\'' +
                ", artiste='" + artiste + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +

                '}';
    }
}
