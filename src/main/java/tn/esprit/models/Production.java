package tn.esprit.models;

public class Production {

    private int id, idUser ;
    private String nom , genre, desc, tags, cover;
    public Production() {}

    public Production(int id, int idUser, String nom, String genre, String desc, String tags, String cover) {
        this.id = id;
        this.idUser = idUser;
        this.nom = nom;
        this.genre = genre;
        this.desc = desc;
        this.tags = tags;
        this.cover = cover;
    }
    public Production(int id, String nom, String genre, String desc, String tags, String cover) {
        this.id = id;
        this.nom = nom;
        this.genre = genre;
        this.desc = desc;
        this.tags = tags;
        this.cover = cover;
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

    public int getIdUser() {return idUser;}
    public void setIdUser(int idUser) {this.idUser = idUser;}

    public String getCover() {return cover;}
    public void setCover(String cover) {this.cover = cover;}

    @Override
    public String toString() {
        return "Production{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", genre='" + genre + '\'' +
                ", desc='" + desc + '\'' +
                ", tags='" + tags + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
