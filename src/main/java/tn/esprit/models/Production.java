package tn.esprit.models;

public class Production {

    private int id ;
    private String nom , genre, desc, tags;

    public Production() {}

    public Production(int id, String nom, String genre, String desc, String tags) {
        this.id = id;
        this.nom = nom;
        this.genre = genre;
        this.desc = desc;
        this.tags = tags;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}

    public String getTags() {return tags;}
    public void setTags(String tags) {this.tags = tags;}

    @Override
    public String toString() {
        return "Production{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", genre='" + genre + '\'' +
                ", desc='" + desc + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
